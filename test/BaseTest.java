import com.avaje.ebean.Ebean;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.db.ebean.Model;
import play.test.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: epanahi
 * Date: 10/30/12
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest<T extends Model>
{
    public static FakeApplication app;

    @BeforeClass
    public static void startApp()
    {
        app = Helpers.fakeApplication(/*Helpers.inMemoryDatabase()*/);
        Helpers.start(app);
    }

    @Before
    public void beforeEachTest() {
        Ebean.save(fixturesToLoad());
    }

    @After
    public void afterEachTest() {
        Ebean.delete(fixturesToUnload());
    }

    // template methods to load/unload fixtures
    public List<T> fixturesToLoad()   { return new ArrayList<T>(); }
    public List<T> fixturesToUnload() { return new ArrayList<T>();}

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }
}
