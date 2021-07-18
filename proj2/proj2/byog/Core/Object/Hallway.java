package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Hallway extends RectangularObject {
    private String objectName = "hallway";
    private boolean is_horizontal;
    private Index middleIndex;
    private Index objectIndex;
    private RectangularSize size;

    public Hallway(int len, boolean is_horizontal)
    {
        if(is_horizontal) {
            size = new RectangularSize(len, 3);
        }else {
            size = new RectangularSize(3, len);
        }
    }

    public Hallway(boolean is_horizontal, TETile[][] w, Index middleIndex)
    {

        this.middleIndex = middleIndex;
        world = w;
        this.is_horizontal = is_horizontal;
        setSizeAndIndex();
        addTo(world, objectIndex);
    }


    private void setSizeAndIndex()
    {
        if(is_horizontal) {
            int leftSize = 0, rightSize = 0;
            for(int i = 1; middleIndex.horizontal() + i < 80; i++){
                if (world[middleIndex.horizontal() + i][middleIndex.vertical()] == Tileset.NOTHING
                || world[middleIndex.horizontal() + i][middleIndex.vertical() + 1] == Tileset.NOTHING
                || world[middleIndex.horizontal() + i][middleIndex.vertical() - 1] == Tileset.NOTHING){
                    rightSize += 1;
                }else{
                    break;
                }
            }
            for(int i = 1; middleIndex.horizontal() - i >= 0; i++){
                if (world[middleIndex.horizontal() - i][middleIndex.vertical()] == Tileset.NOTHING
                || world[middleIndex.horizontal() - i][middleIndex.vertical() + 1] == Tileset.NOTHING
                || world[middleIndex.horizontal() - i][middleIndex.vertical() - 1] == Tileset.NOTHING){
                    leftSize += 1;
                }else{
                    break;
                }
            }
            size = new RectangularSize(leftSize + rightSize + 3, 3);
            objectIndex = new Index(middleIndex.horizontal() - leftSize - 1, middleIndex.vertical() - 1);
        }else{
            int bottomSize = 0, topSize = 0;
            for(int i = 1; middleIndex.vertical() + i < 40; i++){
                if (world[middleIndex.horizontal()][middleIndex.vertical() + i] == Tileset.NOTHING
                || world[middleIndex.horizontal() + 1][middleIndex.vertical() + i] == Tileset.NOTHING
                || world[middleIndex.horizontal() - 1][middleIndex.vertical() + i] == Tileset.NOTHING){
                    topSize += 1;
                }else{
                    break;
                }
            }
            for(int i = 1; middleIndex.vertical() - i >= 0; i++){
                if (world[middleIndex.horizontal()][middleIndex.vertical() - i] == Tileset.NOTHING
                || world[middleIndex.horizontal() + 1][middleIndex.vertical() - i] == Tileset.NOTHING
                || world[middleIndex.horizontal() - 1][middleIndex.vertical() - i] == Tileset.NOTHING){
                    bottomSize += 1;
                }else{
                    break;
                }
            }
            size = new RectangularSize(3, topSize + bottomSize + 3);
            objectIndex = new Index(middleIndex.horizontal() - 1, middleIndex.vertical() - bottomSize - 1);
        }
    }

    /**
     * Return the how long can a hallway stretch
     * in the given world and given direction.
     * @param direction: 0, 1, 2, 3 means left, right,
     *                 up and down respectively.
     */
    private int discover(int direction)
    {
        int size = 0;
        Index currentIndex = new Index(middleIndex.horizontal(), middleIndex.vertical());
        while(currentIndex.horizontal() >= 0 && currentIndex.vertical() >= 0 && currentIndex.horizontal() < 80 && currentIndex.vertical() < 40)
            if (world[middleIndex.horizontal()][middleIndex.vertical()] == Tileset.NOTHING) {
                size += 1;
            } else {
                break;
            }
        return 0;
        }

    @Override
    public void addTo(TETile[][] world, Index beginIndex)
    {
        Index currentIndex;
        TETile currentTile;
        for(int i : new Range(size.length())){
            for(int j : new Range(size.width())){
                currentIndex = new Index(beginIndex.horizontal() + i,beginIndex.vertical() + j);
                if (isOnEdge(i, j, size) && !isOnPassage(i, j)){
                    currentTile = Tileset.WALL;
                }else{
                    currentTile = Tileset.FLOOR;
                }
                world[currentIndex.horizontal()][currentIndex.vertical()] = currentTile;
            }
        }
    }


    private boolean isOnPassage(int horizontalIndex, int verticalIndex)
    {
        return (size.length() == 3 && horizontalIndex == 1) || (size.width() == 3 && verticalIndex == 1);
    }
}
