import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */

    private final Map<Long, GraphBuildingHandler.Node> vertices = new LinkedHashMap<>();
    public TrieSet prefixSearchSet;

    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        prefixSearchSet = buildTrieSet();
        clean();

    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        LinkedList<Long> singularIDs = new LinkedList<>();
        for (GraphBuildingHandler.Node node: vertices.values()){
            if (node.neighbours.isEmpty()) singularIDs.add(node.id);
        }
        for (long id: singularIDs){
            vertices.remove(id);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return vertices.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return vertices.get(v).neighbours.keySet();
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double closestDistance = Double.MAX_VALUE;
        double distance;
        long closestID = 0;
        for (GraphBuildingHandler.Node node: vertices.values()){
            distance = distance(lon, lat, node.lon, node.lat);
            if (closestDistance > distance){
                closestID  = node.id;
                closestDistance = distance;
            }
        }
        return closestID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return vertices.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return vertices.get(v).lat;
    }

    void addVertex(GraphBuildingHandler.Node node) {
        this.vertices.put(node.id, node);
    }

    GraphBuildingHandler.Node getVertex(long vertexID)
    {
        return this.vertices.get(vertexID);
    }


    static class TrieSet
    {
        private Node root;
        private long size;

        public void put(HashMap<String, Object> location)
        {
            String original = (String)location.get("name");
            String cleaned = cleanString(original);
            Node node = root;
            for (int i = 0; i < cleaned.length(); i += 1){
                char c = cleaned.charAt(i);
                if (!node.links.containsKey(c)) node.links.put(c, new Node(c));
                node = node.links.get(c);
            }
            node.isExists = true;
            node.location = location;
            this.size += 1;

        }

        private Node findNode(String cleanedPrefix)
        {
            Node node = root;
            for (int i = 0; i < cleanedPrefix.length(); i += 1){
                if (node == null) break;
                char c = cleanedPrefix.charAt(i);
                node = node.links.get(c);
            }
            return node;
        }

        List<String> getAllOriginalNames(String cleanedPrefix)
        {

            Node node = findNode(cleanedPrefix);
            List<String> result = new LinkedList<>();
            getAllOriginalNames(node, result);
            return result;
        }

        private void getAllOriginalNames(Node node, List<String> result)
        {
            if (node == null) return;
            if (node.isExists) result.add((String) node.location.get("name"));
            for (Node link: node.links.values()) getAllOriginalNames(link, result);
        }



        List<Map<String, Object>> getAllLocations(String cleanedPrefix)
        {
            Node node = findNode(cleanedPrefix);
            List<Map<String, Object>> result = new LinkedList<>();
            getAllLocations(node, result);
            return result;
        }

        private void getAllLocations(Node node, List<Map<String, Object>> result)
        {
            if (node == null) return;
            if (node.isExists) result.add(node.location);
            for (Node link: node.links.values()) getAllLocations(link, result);
        }

        TrieSet(Iterable<GraphBuildingHandler.Node> vertices)
        {
            this.root = new Node(' ');
            this.size = 0;

            HashMap<String, Object> location;
            for (GraphBuildingHandler.Node node: vertices){
                if(node.name != null) {
                    location = new HashMap<>();
                    location.put("lat", node.lat);
                    location.put("lon", node.lon);
                    location.put("name", node.name);
                    location.put("id", node.id);
                    this.put(location);
                }

            }
        }

        public TrieSet()
        {
            this.root = new Node(' ');
        }

        private static class Node {
            boolean isExists;
            char c;
            Map<Character, Node> links;
            Map<String, Object> location;

            private Node(char c) {
                links = new TreeMap<>();
                isExists = false;
                this.c = c;
            }
        }
    }

    private TrieSet buildTrieSet()
    {
        return new TrieSet(vertices.values());
    }
}
