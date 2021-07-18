import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private final double LIMIT = 0.0000000001;
    //private final double LIMIT = 0;
    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        String[][] renderGrid;
        int depth;
        double rasterULLon, rasterULLat, rasterLRLon, rasterLRLat;
        Map<String, Object> results = new HashMap<>();

        depth = getDepth(params);
        int leftX, rightX, upperY, lowerY;
        leftX = getXIndex(params.get("ullat"), depth);
        rightX = getXIndex(params.get("lrlat"), depth);
        upperY = getYIndex(params.get("ullon"), depth);
        lowerY = getYIndex(params.get("lrlon"), depth);
        rasterULLat = leftX * latPerGrid(depth) + MapServer.ROOT_ULLAT;
        rasterULLon = upperY * lonPerGrid(depth) + MapServer.ROOT_ULLON;
        rasterLRLat = rightX * latPerGrid(depth) + MapServer.ROOT_ULLAT;
        rasterLRLon = lowerY * lonPerGrid(depth) + MapServer.ROOT_ULLON;
        renderGrid = setRenderGrid(leftX, rightX, upperY, lowerY, depth);
        setAll(results, renderGrid, rasterULLon, rasterULLat, rasterLRLon, rasterLRLat, depth);
        return results;
    }

    private int getDepth(Map<String, Double> params)
    {
        double inputLonDPP = getInputLonDPP(params);
        double lonDPP = initialDPP();
        int depth = 0;
        while (lonDPP / 2 + LIMIT > inputLonDPP && depth < 7){
            depth += 1;
            lonDPP /= 2;
        }
        // System.out.println("The input lonDPP is " + inputLonDPP + " and the return lonDPP is " + lonDPP + " with level " + level);
        return depth;
    }

    private double initialDPP()
    {
        return Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
    }

    private double getInputLonDPP(Map<String, Double> params)
    {
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double width = params.get("w");
        return (lrlon - ullon) / width;
    }

    private double latPerGrid(int depth)
    {
        return (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2, depth);
    }

    private double lonPerGrid(int depth)
    {
        return (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, depth);
    }

    /**
     * Return the X index of the grid which is
     * corresponding to the given latitude and depth.
     */
    private int getXIndex(double lat, int depth)
    {
        return (int)((lat - MapServer.ROOT_ULLAT) / latPerGrid(depth) - LIMIT);
    }

    /**
     * Return the Y index of the grid which is
     * corresponding to the given longitude and depth.
     */
    private int getYIndex(double lon, int depth)
    {
        return (int)Math.abs((lon - MapServer.ROOT_ULLON) / lonPerGrid(depth) - LIMIT);
    }

    private String[][] setRenderGrid(int leftX, int rightX, int upperY, int lowerY, int depth)
    {
        String[][] RenderGrid = new String[rightX - leftX + 1][lowerY - upperY + 1];
        for (int x = leftX; x <= rightX; x++){
            for (int y = upperY; y <= lowerY; y++){
                RenderGrid[x - leftX][y - upperY] = "d" + depth +"_x" + x + "_y" + y;
            }
        }
        return RenderGrid;
    }

    private void setAll(Map<String, Object> results, String[][] renderGrid, double rasterULLon, double rasterULLat, double rasterLRLon, double rasterLRLat, int depth)
    {
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterULLon);
        results.put("raster_ul_lat", rasterULLat);
        results.put("raster_lr_lon", rasterLRLon);
        results.put("raster_lr_lat", rasterLRLat);
        results.put("depth", depth);
        results.put("query_success", true);
    }
}
