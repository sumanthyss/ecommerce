import models.InventoryItem;
import org.junit.Test;

import static play.test.Helpers.running;
import static play.test.Helpers.fakeApplication;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: epanahi
 * Date: 11/27/12
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelTest extends BaseTest<InventoryItem>
{

    @Test
    public void newItem()
    {
        InventoryItem item = new InventoryItem("apple", 0.5, 10, "png");
        item.save();
        assertThat(item.price).isEqualTo(0.5);
    }
}
