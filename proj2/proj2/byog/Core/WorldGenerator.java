package byog.Core;

import byog.Core.Object.*;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class WorldGenerator implements Serializable {
    TETile[][] world;
    MainDungeon mainDungeon;
    Player player;
    NumberGenerator random;

    /**
     * World generator for generating a new game.
     */
    public WorldGenerator(long seed, final int LENGTH, final int WIDTH)
    {
        this.random = new NumberGenerator(seed);
        this.world =  new TETile[LENGTH][WIDTH];
        fillWorldWithEmpty(world, LENGTH, WIDTH);
        this.mainDungeon = createMainDungeon(LENGTH, WIDTH);
        this.player = mainDungeon.player;
    }

    private MainDungeon createMainDungeon(int LENGTH, int WIDTH)
    {
        RectangularSize dungeonSize = new RectangularSize(LENGTH, WIDTH);
        Index dungeonIndex = new Index(0, 0);
        return new MainDungeon(dungeonSize, world, dungeonIndex, random);
    }


    public void fillWorldWithEmpty(TETile[][] world, final int LENGTH, final int WIDTH)
    {
        for (int x = 0; x < LENGTH; x += 1) {
            for (int y = 0; y < WIDTH; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
}
