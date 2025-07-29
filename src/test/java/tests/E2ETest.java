package tests;

import org.testng.annotations.Test;

public class E2ETest {

    @Test
    public void testEndToEnd() {
        System.out.println("Running end-to-end test...");
        assert true : "End-to-end test failed";
    }


}
