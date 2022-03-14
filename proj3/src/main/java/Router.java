import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {


        long start = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);
        return aStarShortestPath(g, start, dest);
    }

    private static List<Long> aStarShortestPath(GraphDB g, long start, long dest)
    {
        // Initialize best, the known best distance from a node to start
        HashMap<Long, Double> best = new HashMap<>();
        best.put(start, .0);

        // A set contains checked nodes, to prevent repeating
        Set<Long> marked = new HashSet<>();

        // Contains node that waiting to check
        PriorityQueue<Bead> fringe = new PriorityQueue<>();
        fringe.add(new Bead(start, 0, null));

        // Start search

        Bead last;
        while(true){
             last = fringe.remove();
             long v = last.ID;
             if (marked.contains(v)) continue;
             if (v == dest) break;

             marked.add(v);
             double distV, distW;
             for(long w: g.getVertex(v).neighbours.keySet()){
                 distV = getBest(best, v);
                 distW = getBest(best, w);
                 if (g.distance(v, w) < distW - distV){
                     best.put(w, distV + g.distance(v, w));
                     fringe.add(new Bead(w, distV + g.distance(v, w) + g.distance(w, dest), last));
                 }
             }
        }

        // Store the result to a list
        List<Long> result  = new LinkedList<>();
        Bead b = last;
        while(b != null){
            result.add(0, b.ID);
            b = b.prev;
        }

        return result;
    }


    private static double getBest(HashMap<Long, Double> best, long v)
    {
        return best.getOrDefault(v, Double.MAX_VALUE);
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
       List<NavigationDirection> result = new LinkedList<>();
       NavigationDirection current = null;
       String prevWay = null;

       String wayName;
       double dist;
       for (int i = 0; i < route.size() - 1; i += 1){
           long startID = route.get(i), endID = route.get(i + 1);
           wayName = g.getVertex(startID).neighbours.get(endID);
           dist = g.distance(startID, endID);

           if (wayName.equals(prevWay)) current.distance += dist;
           else {
               if (i != 0) result.add(current);
               current = new NavigationDirection();
               current.direction = (i == 0)?NavigationDirection.START:
                       chooseDirection(g.bearing(startID, endID) - g.bearing(route.get(i - 1), startID));
               if(!wayName.equals("")) current.way = wayName;
               current.distance = dist;
           }

           if (i == route.size() - 2) result.add(current);
           prevWay = wayName;
       }
       return result;
    }

    private static int chooseDirection(double bearing)
    {
        if (bearing > 180) bearing -= 360;
        if (bearing < -180) bearing += 360;
        int result;
        if (bearing < 0){
            if (bearing < -30){
                if (bearing < -100) result = NavigationDirection.SHARP_LEFT;
                else result = NavigationDirection.LEFT;
            }else{
                if (bearing < -15) result = NavigationDirection.SLIGHT_LEFT;
                else result = NavigationDirection.STRAIGHT;
            }
        }else{
            if (bearing > 30){
                if (bearing > 100) result = NavigationDirection.SHARP_RIGHT;
                else result = NavigationDirection.RIGHT;
            }else{
                if (bearing > 15) result = NavigationDirection.SLIGHT_RIGHT;
                else result = NavigationDirection.STRAIGHT;
            }
        }
        return result;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }

    }

    private static class Bead implements Comparable<Bead>
    {
        private long ID;
        private double priority;
        Bead prev;

        public Bead(long ID, double priority, Bead prev)
        {
            this.ID = ID;
            this.priority = priority;
            this.prev = prev;
        }

        @Override
        public int compareTo(Bead o) {
            return (this.priority > o.priority)?1:-1;
        }
    }

}
