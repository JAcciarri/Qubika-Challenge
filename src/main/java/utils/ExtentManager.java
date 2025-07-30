package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {
    private static ExtentReports extent;

    private ExtentManager() { }

    public static synchronized ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
            spark.config().setDocumentTitle("Qubika E2E Report");
            spark.config().setReportName("Qubika Automation Suite");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}