package steps;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
//        tags = {"@JSON"},
        features = "ApiTests/test/java/features",
        glue = "steps"
)
public class TestRunner {
}
