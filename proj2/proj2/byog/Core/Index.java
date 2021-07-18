package byog.Core;

import java.io.Serializable;

public class Index implements Serializable {
    private int horizontal;
    private int vertical;

    public Index(int h, int v)
    {
        horizontal = h;
        vertical = v;
    }

    public Index(){
    }

    public void setHorizontal(int h)
    {
        horizontal = h;
    }

    public void setVertical(int v)
    {
        vertical = v;
    }

    /**
     * Return a new Index on the
     * moves in the horizontal and
     * vertical direction of the
     * original index.
     */
    public Index indexOn(int horizontalDistance, int verticalDistance)
    {
        return new Index(horizontal() + horizontalDistance,vertical() + verticalDistance);
    }

    public Index indexOnHorizontal(int horizontalDistance)
    {
        return indexOn(horizontalDistance, 0);
    }

    public Index indexOnVertical(int verticalDistance)
    {
        return indexOn(0, verticalDistance);
    }

    public void horizontalMove(int n)
    {
        horizontal += n;
    }

    public void verticalAdd(int n)
    {
        vertical += n;
    }

    public int horizontal(){
        return horizontal;
    }
    public int vertical(){
        return vertical;
    }
}
