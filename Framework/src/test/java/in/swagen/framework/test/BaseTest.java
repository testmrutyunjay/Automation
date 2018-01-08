package in.swagen.framework.test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import in.swagen.framework.util.CustomListeners;
import in.swagen.framework.util.InitProperties;
import in.swagen.framework.util.SetUpDrivers;
@Listeners(CustomListeners.class)
public abstract class BaseTest {

	public WebDriver driver;
	public static Properties properties;
	private String browserName;
	private String url;
	public  String userName;
	public  String passWord;
	public static int implicitWait;
	public  Logger log;
	public BaseTest(){
		log=Logger.getLogger(this.getClass());
		Logger.getRootLogger().setLevel(Level.INFO);
		
	}
	@BeforeSuite
	public void initFramework(){
		properties=InitProperties.initPropertis("config","login");
		log.info("framework is initialised");
	}
	@AfterSuite
	public void closeFramework(){
		
	}
	@BeforeTest
	public void initGlobalConstants(){
		browserName=properties.getProperty("browserName");
		url=properties.getProperty("url");
		userName=properties.getProperty("username");
		passWord=properties.getProperty("password");
		implicitWait=Integer.parseInt(properties.getProperty("implicitWait"));
		log.info(browserName);
		log.info(url);
		log.info(userName);
		log.info(passWord);
		log.info(implicitWait);
	}
	@BeforeMethod
	public void setUp(){
		SetUpDrivers setupDriver=new SetUpDrivers();
		if(browserName.equalsIgnoreCase("firefox")){
			driver=setupDriver.launchFirefoxDriver(url);
		}
		else if(browserName.equalsIgnoreCase("ie")){
			driver=setupDriver.launchInternetExplorerDriver(url);
		}
		else{
			driver=setupDriver.launchChromeDriver(url);
		}
		log.info(browserName+" is launched");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}
	@AfterMethod
	public void tearDown(){
		driver.close();
		log.info(browserName+" is closed");
	}
}
