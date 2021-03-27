package byog.Core.Object;

import byog.TileEngine.TETile;
import byog.Core.Index;

public interface GameObject {
    public void addTo(TETile[][] world, Index index);
    public void remove();
}
