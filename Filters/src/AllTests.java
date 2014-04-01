import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ AverageTest.class, CascadeTest.class, IdentityTest.class,
		MaxFilterTest.class, MinFilterTest.class, ScalarLinearTest.class,
		BinomialFilterTest.class, FIRFilterTest.class, PureGainTest.class, SignalTest.class})
public class AllTests {

}
