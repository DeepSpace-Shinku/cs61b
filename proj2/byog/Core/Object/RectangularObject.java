package byog.Core.Object;

import byog.Core.Index;
import byog.TileEngine.TETile;

import java.io.Serializable;

public abstract class RectangularObject implements GameObject, Serializable {
    RectangularSize size = null;
    String objectName = null;
    public Index objectIndex = null;
    public TETile[][] world = null;

    public abstract void addTo(TETile[][] world, Index index);

    public void remove()
    {
        assert objectIndex != null: "Game object " + objectName + " with length " + size.length() +" has no index";
    }

    /**
     * Return if a given position is on the edge of the given object size.
     * @param i : Horizontal position index.
     * @param j : Vertical position index.
     * @param size : Size of the object.
     */
    public static boolean isOnEdge(int i, int j, RectangularSize size)
    {
        return i == 0 || i == size.length() - 1 || j == 0 || j == size.width() - 1;
    }

    protected int bottomIndex()
    {
        return objectIndex.vertical();
    }

    protected int topIndex()
    {
        return objectIndex.vertical() + size.width() - 1;
    }

    protected int leftIndex()
    {
        return objectIndex.horizontal();
    }

    protected int rightIndex()
    {
        return objectIndex.horizontal() + size.length() - 1;
    }
}
