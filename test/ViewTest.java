import org.junit.Test;
import play.mvc.Content;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

public class ViewTest
{
    @Test
    public void indexTemplate()
    {
        Content html = views.html.index.render("test");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("test");
    }
}
