import controllers.routes;
import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

public class ApplicationTest extends BaseTest
{
    @Test
    public void callIndex()
    {
        Result result = callAction(routes.ref.Application.index());
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
        assertThat(charset(result)).isEqualTo("utf-8");
        assertThat(contentAsString(result)).contains("Shopping Cart");
    }
}
