import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ HW4Tests.class, MazeCellJUnit.class, MazeJUnit.class,
		MazeRouteJUnit.class })
public class AllTests {

}
