package byog.Core;

import byog.Core.Object.Dungeon;
import byog.Core.Object.NumberGenerator;
import byog.Core.Object.RectangularObject;
import byog.Core.Object.RectangularSize;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WorldGeneration {
    public static TETile[][] worldGenerator(long seed, final int LENGTH, final int WIDTH)
    {

        TETile[][] world = new TETile[LENGTH][WIDTH];
        fillWorld(world, seed, LENGTH, WIDTH);
        return world;
    }

    public static void fillWorld(TETile[][] world, long seed, final int LENGTH, final int WIDTH)
    {
        for (int x = 0; x < LENGTH; x += 1) {
            for (int y = 0; y < WIDTH; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        RectangularSize dungeonSize = new RectangularSize(LENGTH, WIDTH);
        Index dungeonIndex = new Index(0, 0);
        NumberGenerator random = new NumberGenerator(seed);
        Dungeon dungeon = new Dungeon(dungeonSize, world, dungeonIndex, random);
        dungeon.addRoomsAndHallways();
    }
}
