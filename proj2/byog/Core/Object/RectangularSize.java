package byog.Core.Object;

public class RectangularSize {
    private int length;
    private int width;

    public RectangularSize()
    {
        return;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public RectangularSize(int len, int wid)
    {
        length = len;
        width = wid;
    }

    public int length()
    {
        return length;
    }

    public int width(){
        return width;
    }
}
