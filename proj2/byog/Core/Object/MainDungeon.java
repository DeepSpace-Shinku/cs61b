package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;

import java.io.Serializable;

public class MainDungeon extends Dungeon implements Serializable {

    public Player player;
    public MainDungeon(RectangularSize s, TETile[][] w, Index i, NumberGenerator r)
    {
        super(s, w, i, r);
        addRoomsAndHallways();
        addPlayer();
    }

    private void addPlayer()
    {
        Room randomRoom = getRandomRoom();
        player = new Player(world, randomRoom.objectIndex.indexOn(1, 1));
    }

    public void ShowAll()
    {
        for(Dungeon subDungeon: subDungeons);
    }

}
