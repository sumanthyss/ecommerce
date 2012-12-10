import org.junit.Test;
import play.api.templates.Html;
import play.mvc.Content;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

public class ViewTest extends BaseTest
{
    @Test
    public void indexTemplate()
    {
        Content html = views.html.main.render("Shopping Cart", Html.empty());
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Shopping");
    }
}
