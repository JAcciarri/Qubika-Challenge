package dataprovider;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class E2ETestDataProvider {

    @DataProvider(name = "e2eTestDataProvider")
    public Object[][] e2eTestDataProvider() throws Exception {
        return JsonTestDataLoader.loadGenericTestData(
                "data/e2eTestCase17545.json",
                new TypeReference<List<Map<String, String>>>() {}
        );
    }
}
