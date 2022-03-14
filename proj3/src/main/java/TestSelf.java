import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestSelf {
    @Test
    public void test()
    {
        GraphDB g = new GraphDB("../library-sp18/data/test.osm.xml");
        GraphDB.TrieSet s =  g.prefixSearchSet;
        List<String> result = s.getAllOriginalNames("a");
        assertEquals(5, result.size());
        List<Map<String, Object>> locResult = s.getAllLocations("a");
        assertEquals(5, locResult.size());
    }
}
