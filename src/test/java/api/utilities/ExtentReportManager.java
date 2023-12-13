package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{
	public ExtentSparkReporter sparkReporter;   //responsible for Look and feel of the report - UI
	public ExtentReports extent;   //it is responsible for common details 
	public ExtentTest test;    // creating entries of the test
	
	String repName;
	

    public void onStart(ITestContext arg0) {
    	String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "TestReport"+timestamp+".html"; 
    	
        sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
    	sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
    	sparkReporter.config().setReportName("Pet Store - Execution Report");
    	sparkReporter.config().setTheme(Theme.STANDARD);
    	
    	extent = new ExtentReports();
    	extent.attachReporter(sparkReporter);
    	extent.setSystemInfo("Application", "Pet store Users API");
    	extent.setSystemInfo("Operating System", System.getProperty("os.name"));
    	extent.setSystemInfo("User Name", System.getProperty("user.name"));
    	extent.setSystemInfo("Environment", "QA");
    	extent.setSystemInfo("User", "Dhana");    	
    	
    	System.out.println("Start Of Execution(TEST)->"+arg0.getName());

    }

    /*public void onTestStart(ITestResult arg0) {

    	  test= extent.createTest(arg0.getName());
          //	test.createNode(arg0.getName());
          test.assignCategory(arg0.getMethod().getGroups());
          test.createNode(arg0.getName());
          test.log(Status.INFO, "Info....");

    }*/

    public void onTestSuccess(ITestResult arg0) {
    	test= extent.createTest(arg0.getName());
       	test.createNode(arg0.getName());
       	test.assignCategory(arg0.getMethod().getGroups());
       	test.log(Status.PASS, "Test Passed");
    }

   
    public void onTestFailure(ITestResult arg0) {

    	 test= extent.createTest(arg0.getName());
         test.createNode(arg0.getName());
         test.assignCategory(arg0.getMethod().getGroups());
         test.log(Status.FAIL, "Test Failed");
         test.log(Status.FAIL, arg0.getThrowable().getMessage());
    }

   
    public void onTestSkipped(ITestResult arg0) {

    	 test= extent.createTest(arg0.getName());
         test.createNode(arg0.getName());
         test.assignCategory(arg0.getMethod().getGroups());
         test.log(Status.SKIP, "Test Skipped");
         test.log(Status.SKIP, arg0.getThrowable().getMessage());

    }

    
    public void onFinish(ITestContext arg0) {

    	extent.flush();

    }  


}
