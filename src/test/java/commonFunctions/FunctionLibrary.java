 package commonFunctions;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;
	// Methods for lunching browser
	public static WebDriver LunchBrowser() throws Throwable {
		conpro = new Properties();
		// load the property file
		conpro.load(new FileInputStream("./propertyFile/Enveronment.properties"));
		if (conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver=new ChromeDriver();
			driver.manage().window().maximize();}	
		else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver= new FirefoxDriver();
		}
		else {
			Reporter.log("Browser key is not matching",true);
		}

		return driver;
	}

	public static void openurl() 
	{
		driver.get(conpro.getProperty("url"));
	}
	// methods for waitforElement to wait
	public static void waitforElement(String LocaterName,String LocaterValue,String TestData) {
		WebDriverWait mywait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
		if(LocaterName.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocaterValue)));
		}
		if(LocaterName.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocaterValue)));
		}
		if(LocaterName.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocaterValue)));
		}
	}
	// method for typeActions key
	public static void typeActions(String LocaterName,String LocaterValue, String TestData) {
		if(LocaterName.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocaterValue)).clear();
			driver.findElement(By.xpath(LocaterValue)).sendKeys(TestData);
		}
		if(LocaterName.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocaterValue)).clear();
			driver.findElement(By.id(LocaterValue)).sendKeys(TestData);
		}
		if(LocaterName.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocaterValue)).clear();
			driver.findElement(By.name(LocaterValue)).sendKeys(TestData);
		}
	}
	// methods for clickAction
	public static void clickAction(String LocaterName, String LocaterValue) {
		if(LocaterName.equalsIgnoreCase("Xpath")) {
			driver.findElement(By.xpath(LocaterValue)).click();
		}
		if(LocaterName.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocaterValue)).sendKeys(Keys.ENTER);

		}
		if(LocaterName.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocaterValue)).click();
		}
	}
	// methods for  page validation Title
	public static void validateTittle(String Expected_Title) {
		String Actual_Title = driver.getTitle();
		try { Assert.assertEquals(Actual_Title, Expected_Title,"Title is not matching");

		} catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
	// methods for closing browser
	public static void closeBrowser() {
		driver.quit();
	}
	// method for data generation
	public static String generateDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_DD HH_MM_SS");
		return df.format(date);
	}
	// methods for mouse click
	public static void mouseclick()throws Throwable {
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Items')])[2]"))).perform();
		Thread.sleep(3000);  
		ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]"))).click().perform();
		Thread.sleep(3000);
	}

	// method for category table
	public static void categroryTable(String Exp_data) throws Throwable {
		String Exp_Data= driver.findElement(By.xpath(Exp_data)).getText();

		// if search box is not display click search button 
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			// click search panel button 
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-text"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-text"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000); 
		String Act_Data= driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		Reporter.log(Exp_Data+"   "+Act_Data,true);
		try{
			Assert.assertEquals(Act_Data,Exp_Data,"categrory name is not matching");
		}
		catch(AssertionError a)
		{Reporter.log(a.getMessage());
		}
	}

	// methods for list box
	public static void dropDownAction(String locaterName, String locaterValue , String testData) {
		if(locaterName.equalsIgnoreCase("xpath")) {
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.xpath(locaterValue)));
			element.selectByIndex(value);
		}
		if(locaterName.equalsIgnoreCase("name")) {
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.name(locaterValue)));
			element.selectByIndex(value);
		}
		if(locaterName.equalsIgnoreCase("id")) {
			int value = Integer.parseInt(testData);
			Select element = new Select(driver.findElement(By.id(locaterValue)));
			element.selectByIndex(value);
		}
	}

	// method for capture stock number into notepad
	public static void capturestock( String LocaterName, String LocaterValue) throws Throwable{ 
		String stockNumber= ""; 
		if(LocaterName.equalsIgnoreCase("xpath")) {
			stockNumber= driver.findElement(By.xpath(LocaterValue)).getAttribute("value");
		}
		if(LocaterName.equalsIgnoreCase("id")) {
			stockNumber= driver.findElement(By.id(LocaterValue)).getAttribute("value");
		}

		if(LocaterName.equalsIgnoreCase("name")) {
			stockNumber= driver.findElement(By.name(LocaterValue)).getAttribute("value");
		}

		// Create notepad and write stocknumber
		FileWriter fw = new FileWriter("./capterData/Stocknumber.txt");
		BufferedWriter bw =new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
	}
	// method for stock table
	public static void stockTable( String TestData) throws Throwable {
		// read stock number from notepad
		FileReader fr= new FileReader("./capterData/Stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		br.close();
		// if search box is not display click search button 
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			// click search panel button 
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-text"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-text"))).sendKeys(TestData);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000); 
		String Act_Data= driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"   "+Act_Data,true);
		try{
			Assert.assertEquals(Act_Data,Exp_Data,"stock  is not matching");
		}
		catch(AssertionError a)
		{Reporter.log(a.getMessage());
		}

	}
	// method for suppliersTable
	public static void suppliersTable(String TestData) throws Throwable{
		// read suppler number from note pad
		FileReader fr = new FileReader("./capterData/supplerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		br.close();
		// if search box is not display click search button 
				if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
					// click search panel button 
					driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
				driver.findElement(By.xpath(conpro.getProperty("search-text"))).clear();
				driver.findElement(By.xpath(conpro.getProperty("search-text"))).sendKeys(TestData);
				driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
				Thread.sleep(3000); 
				String Act_Data= driver.findElement(By.xpath("//table[@class= 'table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
				Reporter.log(Exp_Data+"   "+Act_Data,true);
				try{
					Assert.assertEquals(Act_Data,Exp_Data,"stock  is not matching");
				}
				catch(AssertionError a)
				{Reporter.log(a.getMessage());
				}
		
	}
	// method for suppliersTable
public static void coustomersTable(String TestData) throws Throwable{
	// read suppler number from note pad
			FileReader fr = new FileReader("./capterData/CoustomerNumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String Exp_Data = br.readLine();
			br.close();
			// if search box is not display click search button 
					if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
						// click search panel button 
						driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
					driver.findElement(By.xpath(conpro.getProperty("search-text"))).clear();
					driver.findElement(By.xpath(conpro.getProperty("search-text"))).sendKeys(TestData);
					driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
					Thread.sleep(3000); 
					String Act_Data= driver.findElement(By.xpath("//table[@class= 'table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
					Reporter.log(Exp_Data+"   "+Act_Data,true);
					try{
						Assert.assertEquals(Act_Data,Exp_Data,"stock  is not matching");
					}
					catch(AssertionError a)
					{Reporter.log(a.getMessage());
					}
}


}
