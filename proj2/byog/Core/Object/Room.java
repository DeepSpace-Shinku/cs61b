package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Room extends RectangularObject {
    String objectName = "room";


    public Room(RectangularSize s)
    {
        size = s;
    }

    public Room(RectangularSize s, TETile[][] w, Index i)
    {
        size = s;
        objectIndex = i;
        world = w;
        addTo(world, objectIndex);
    }

    @Override
    public void addTo(TETile[][] world, Index begin_index)
    {
        Index currentIndex;
        TETile currentTile;
        for(int i : new Range(size.length())){
            for(int j : new Range(size.width())){
                currentIndex = new Index(begin_index.horizontal() + i,begin_index.vertical() + j);
                if (isOnEdge(i, j, size)){
                    currentTile = Tileset.WALL;
                }else{
                    currentTile = Tileset.FLOOR;
                }
                world[currentIndex.horizontal()][currentIndex.vertical()] = currentTile;
            }
        }
    }
}
