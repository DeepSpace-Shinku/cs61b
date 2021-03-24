package byog.Core.Object;

import byog.Core.Index;

import static org.junit.Assert.*;
import static byog.Core.Object.RectangularObject.*;
import byog.TileEngine.*;
import org.junit.Test;

public class TestGameObject {

    @Test
    public void TestIsOnEdge()
    {
        RectangularSize size = new RectangularSize(3, 4);
        assertTrue(isOnEdge(0, 1, size));
        assertFalse(isOnEdge(1, 2, size));
        assertTrue(isOnEdge(2, 1, size));
    }

    public static void TestRoomAddTo()
    {
        /**
         * Make a test of Room.addTo()
         */
        int WIDTH = 20, HEIGHT = 20;
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
        Room room = new Room(new RectangularSize(4, 4), world, new Index(1, 1));


        // draws the world to the screen
        ter.renderFrame(world);
    }

    public static void TestHallwayAddTo()
    {
        /**
         * Make a test of Hallway.addTo()
         */
        int WIDTH = 80, HEIGHT = 40;
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
        Room room = new Room(new RectangularSize(9, 9), world, new Index(1, 1));
        Hallway hallway = new Hallway(false, world, new Index(2, 9));
        // draws the world to the screen
        ter.renderFrame(world);
    }

    public static void main(String[] args)
    {
        TestRoomAddTo();
        TestHallwayAddTo();
    }
}
