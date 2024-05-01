package driversFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.Excelfileutil;



public class DriverScript {
	WebDriver driver;
	String inputpath = "./Fileinput/DataEngin.xlsx";
	String outputpath = "./Fileoutput/Result.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String Testcases = "masterTestCases";

	public void Starttest() throws Throwable {
		String module_status;
		// Create object for ExcellFileutil class
		Excelfileutil xl = new Excelfileutil(inputpath);
		// iterate all test cases in test cases
		for(int i=1;i<=xl.rowcount(Testcases);i++) {
			if(xl.getcellData(Testcases, i, 2).equalsIgnoreCase("Y")) {
				// read corresponding sheet or test cases
				String TCModule = xl.getcellData(Testcases, i, 1);
				// Define path of html
				report= new ExtentReports("./target/extentReport/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger= report.startTest(TCModule);
				logger.assignAuthor("Vinodkumar");
				//iterate all rows in TCModule sheet
				for(int j=1;j<=xl.rowcount(TCModule);j++) {
					// read all cell data from tcmodule sheet
					String Description = xl.getcellData(TCModule, j, 0);
					String Object_type = xl.getcellData(TCModule, j, 1);
					String Lname= xl.getcellData(TCModule, j, 2);
					String Lvalue = xl.getcellData(TCModule, j, 3);
					String Test_data= xl.getcellData(TCModule, j, 4);
					//System.out.println("dsfg");
					try {
						if(Object_type.equalsIgnoreCase("LunchBrowser")){
							driver = FunctionLibrary.LunchBrowser();
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("openurl")){
							FunctionLibrary.openurl();
							logger.log(LogStatus.INFO,Description);}
						if(Object_type.equalsIgnoreCase("waitforElement")) {
							FunctionLibrary.waitforElement(Lname, Lvalue, Test_data);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equals("typeActions")) {
							FunctionLibrary.typeActions(Lname, Lvalue, Test_data);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equals("typeActions")) {
							FunctionLibrary.typeActions(Lname, Lvalue, Test_data);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("waitforElement")) {
							FunctionLibrary.waitforElement(Lname, Lvalue, Test_data);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("validateTittle")) {
							FunctionLibrary.validateTittle(Test_data);
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("mouseclick")) {
							FunctionLibrary.mouseclick();
							logger.log(LogStatus.INFO, Description);}
						if(Object_type.equalsIgnoreCase("categroryTable")) {
							FunctionLibrary.categroryTable(Test_data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(Lname, Lvalue, Test_data);
						}
						if(Object_type.equalsIgnoreCase("stockTable")) {
							FunctionLibrary.stockTable(Test_data);
							}
						if(Object_type.equalsIgnoreCase("capturestock")) {
							FunctionLibrary.capturestock(Lname, Lvalue);
						}
						// write as pass into TCModule sheet in status cell
						xl.setcellData(TCModule, j, 5,"pass",outputpath);
						logger.log(LogStatus.PASS,Description);
						module_status= "True";}	
					catch (Exception e)
					{
						System.out.println(e.getMessage());
						// write as fail into TCMoudle sheet in status  cell
						xl.setcellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.PASS, Description);
						module_status="False";
					}
					if(module_status.equalsIgnoreCase("True")) {
						//write as pass into testcases sheet
						xl.setcellData(Testcases, i, 3, "Pass", outputpath);
					}
					else {
						xl.setcellData(Testcases, i, 3, "Fail", outputpath);
					}

					report.endTest(logger);
					report.flush();
				}
			}
			else {
				//write as Blocked for test to N in testcases sheet
				xl.setcellData(Testcases, i, 3, "Blocked",outputpath);
			}
		}
	}
}


