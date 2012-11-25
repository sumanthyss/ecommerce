import org.junit.Test;
import play.mvc.Result;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class RouteTest
{
    @Test
    public void testRootRoute()
    {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }
}
