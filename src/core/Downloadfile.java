package core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Downloadfile {
	
	File folder;
	WebDriver driver;
	
	@BeforeMethod
	
	public void setup(){
		
		folder = new File(UUID.randomUUID().toString());
		
		folder.mkdir();
		
		System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", folder.getAbsolutePath());
		options.setExperimentalOption("prefs", prefs);
		
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY,options);
		driver = new ChromeDriver(cap);
		
		
	}
	
	@Test 
	public void download() throws InterruptedException{
		
		driver.get("http://the-internet.herokuapp.com/download");
		driver.findElement(By.linkText("comments.txt")).click();
		
		Thread.sleep(2000);
		
		File listoffiles[] = folder.listFiles();
		
		Assert.assertTrue(listoffiles.length>0);
		
		for (File file :listoffiles){
			Assert.assertTrue(file.length()>0);
		}
		
	}
	
	@AfterMethod
	public void teardown(){
		
		driver.quit();
		for(File file : folder.listFiles()){
			file.delete();
		}
		folder.delete();
	}

}
