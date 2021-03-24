package byog.Core;

public class Index {
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

    public void horizontalAdd(int n)
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
