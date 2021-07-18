package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Player extends RectangularObject{

    TETile playerTile = Tileset.PLAYER;

    public Player(TETile[][] w, Index i)
    {
        this.world = w;
        this.objectIndex = i;
        this.size = new RectangularSize(1, 1);
        this.addTo(world, objectIndex);
    }


    @Override
    public void addTo(TETile[][] world, Index index){
        world[index.horizontal()][index.vertical()] = playerTile;
    }

    public void remove()
    {
        world[objectIndex.horizontal()][objectIndex.vertical()] = Tileset.FLOOR;
    }

    public void move(String direction)
    {
        Index movingIndex;
        switch (direction){
            case "left":
                movingIndex = objectIndex.indexOnHorizontal(-1);
                break;
            case "right":
                movingIndex = objectIndex.indexOnHorizontal(1);
                break;
            case "up":
                movingIndex = objectIndex.indexOnVertical(1);
                break;
            case "down":
                movingIndex = objectIndex.indexOnVertical(-1);
                break;
            default:
                throw new RuntimeException("Invalid moving direction.");
        }
        if (world[movingIndex.horizontal()][movingIndex.vertical()].equals(Tileset.FLOOR)){
            this.remove();
            this.addTo(world, movingIndex);
            objectIndex = movingIndex;
        }
    }
}
