import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSelf {
    @Test
    public void test()
    {
        GraphDB g = new GraphDB("../library-sp18/data/test.osm.xml");
        List<Long> route = new LinkedList<>();
        for (long i = 0; i < 5; i += 1){
            route.add(i);
        }
        List<Router.NavigationDirection> result = Router.routeDirections(g, route);
        assertEquals("The directions lengths are not equal", 3, result.size());
        assertEquals(Router.NavigationDirection.START, result.get(0).direction);
        assertEquals(Router.NavigationDirection.LEFT, result.get(1).direction);
        assertEquals(Router.NavigationDirection.RIGHT, result.get(2).direction);
    }
}
