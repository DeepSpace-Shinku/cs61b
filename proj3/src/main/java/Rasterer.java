

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
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
        String[][] renderGrid;
        int depth = getDepth(params);
        double rasterULLon, rasterULLat, rasterLRLon, rasterLRLat;
        double requiredULLon = params.get("ullon"), requiredULLat = params.get("ullat"),
                requiredLRLon = params.get("lrlon"), requiredLRLat = params.get("lrlat");
        Map<String, Object> results = new HashMap<>();
        if (!isValidParameters(true, requiredULLon, requiredULLat, requiredLRLon,requiredLRLat)){
            results.put("query_success", false);
            return results;
        }
        int leftX, rightX, upperY, lowerY;
        leftX = getXIndex(requiredULLon, depth);
        rightX = getXIndex(requiredLRLon, depth);
        upperY = getYIndex(requiredULLat, depth);
        lowerY = getYIndex(requiredLRLat, depth);

        rasterULLat = upperY * latPerGrid(depth) + MapServer.ROOT_ULLAT;
        rasterULLon = leftX * lonPerGrid(depth) + MapServer.ROOT_ULLON;
        rasterLRLat = (lowerY + 1) * latPerGrid(depth) + MapServer.ROOT_ULLAT;
        rasterLRLon = (rightX + 1) * lonPerGrid(depth) + MapServer.ROOT_ULLON;



        renderGrid = setRenderGrid(leftX, rightX, upperY, lowerY, depth);
        setAll(results, renderGrid, rasterULLon, rasterULLat, rasterLRLon, rasterLRLat, depth);
        return results;
    }

    private int getDepth(Map<String, Double> params)
    {
        double inputLonDPP = getInputLonDPP(params);
        double lonDPP = depth0DPP();
        int depth = 0;
        while (lonDPP > inputLonDPP && depth < 7){
            depth += 1;
            lonDPP = nextLevelLonDPP(lonDPP);
        }
        // System.out.println("The input lonDPP is " + inputLonDPP + " and the return lonDPP is " + lonDPP + " with level " + level);
        return depth;
    }

    private double depth0DPP()
    {
        return Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
    }

    private double nextLevelLonDPP(double DPP)
    {
        return DPP / 2;
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
     * if it is out of bound of the LIMIT, return LIMIT
     */
    private int getXIndex(double lon, int depth)
    {
        return (int)((lon - MapServer.ROOT_ULLON) / lonPerGrid(depth));
    }

    /**
     * Return the Y index of the grid which is
     * corresponding to the given longitude and depth.
     * if it is out of bound of the LIMIT, return LIMIT
     */
    private int getYIndex(double lat, int depth)
    {
        return (int)Math.abs((lat - MapServer.ROOT_ULLAT) / latPerGrid(depth));
    }

    private String[][] setRenderGrid(int leftX, int rightX, int upperY, int lowerY, int depth)
    {
        String[][] RenderGrid = new String[lowerY - upperY + 1][rightX - leftX + 1];
        for (int x = leftX; x <= rightX; x++){
            for (int y = upperY; y <= lowerY; y++){
                RenderGrid[y - upperY][x - leftX] = "d" + depth +"_x" + x + "_y" + y + ".png";
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

    private boolean isValidParameters(boolean isNorth, double requiredULLon, double requiredULLat, double requiredLRLon, double requiredLRLat)
    {
        if (!isNorth) return false;
        if (requiredLRLat > requiredULLat || requiredLRLon < requiredULLon) return false;
        if (requiredULLat < MapServer.ROOT_LRLAT || requiredLRLat > MapServer.ROOT_ULLAT ||
                requiredULLon > MapServer.ROOT_LRLON || requiredLRLon < MapServer.ROOT_ULLON) return false;
        return true;
    }
}
