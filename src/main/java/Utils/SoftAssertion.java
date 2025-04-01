package Utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertion {

    // Thread-local variable to hold a separate SoftAssert instance for each thread.
    private static final ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

    //Retrieves the SoftAssert instance for the current thread.
    public static SoftAssert getSoftAssert() {
        return softAssert.get();
    }

    public static void assertAll() {
        SoftAssert currentSoftAssert = getSoftAssert();
        try {
            // Finalize and check all soft assertions. If any assertion fails, an AssertionError is thrown.
            currentSoftAssert.assertAll("Soft Assertion"); // Assert all recorded assertions (if any)
        } catch (Exception e) {
            LogsUtil.error("Soft Assertion Failed: {}", e.getMessage());
        } finally {
            softAssert.remove(); // Always clean up the ThreadLocal instance
        }
    }
}

