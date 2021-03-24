package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /**
     *
     * @param s side length of the hexagon
     * @param x the x index of the first position of the hexagon
     * @param y the y index of the first position of the hexagon
     */
    public static void addHexagon(int s, int x, int y, TETile[][] world)
    {
        drawUpper(s, x, y, world);
        drawLower(s, x, y, world);
    }

    private static void drawLine(int firstXIndex, int yIndex, int length, TETile[][] world)
    {
        for(int i = 0; i < length; i += 1)
        {
            world[firstXIndex + i][yIndex] = Tileset.GRASS;
        }
    }

    private static void drawUpper(int s, int x, int y, TETile[][] world)
    {
        for(int i = 0; i < s;i ++)
        {
            int firstXIndex = x - i;
            int yIndex = y + i;
            int length = s + 2 * i;
            drawLine(firstXIndex, yIndex, length, world);
        }
    }

    private static void drawLower(int s, int x, int y, TETile[][] world)
    {
        /** Calculate the first x, y indices of lower part.*/
        int firstX = x - s + 1;
        int firstY = y + s;

        for(int i = 0; i < s;i ++)
        {
            int firstXIndex = firstX + i;
            int yIndex = firstY + i;
            int length = s + 2 * (s - 1) - 2 * i;
            drawLine(firstXIndex, yIndex, length, world);
        }
    }

    public static void addTesselation(int s, int x, int y, TETile[][] world)
    {
        drawHexagonRow(s, x, y, 1, world);
        x = leftBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 2, world);
        x = leftBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 3, world);
        x = rightBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 2, world);
        x = leftBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 3, world);
        x = rightBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 2, world);
        x = leftBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 3, world);
        x = rightBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 2, world);
        x = rightBelowXIndex(s, x);
        y = belowYIndex(s, y);
        drawHexagonRow(s, x, y, 1, world);
    }

    /** Return the x index of hexagon on the right of the current x index.*/
    private static int rightXIndex(int s, int x)
    {
        return x + s + s + 2 * (s - 1);
    }

    /** Return the x index of hexagon on the left-below of the current x index.*/
    private static int leftBelowXIndex(int s, int x)
    {
        return x - 2 * s + 1;
    }

    /** Return the x index of hexagon on the right-below of the current x index.*/
    private static int rightBelowXIndex(int s, int x)
    {
        return x + 2 * s - 1;
    }

    /** Return the y index of hexagon below of current x index.*/
    private static int belowYIndex(int s, int y)
    {
        return y + s;
    }
    /** Draw n consecutive hexagons in a row with side length s
     *  and the initial coordinate x and y in the given world.*/
    private static void drawHexagonRow(int s, int x, int y, int n, TETile[][] world)
    {
        for(; n > 0; n -= 1)
        {
            addHexagon(s, x, y, world);
            x = rightXIndex(s, x);
        }
    }

    public static void main(String[] args)
    {
        final int WIDTH = 50;
        final int HEIGHT = 50;
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // fills in a block 14 tiles wide by 4 tiles tall

        addTesselation(3, 24, 0, world);
        ter.renderFrame(world);
    }

}
