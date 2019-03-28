package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import pom.Elements;
import utilities.DBManager;

public class Getdata4 {

	WebDriver driver;
	WebDriverWait wait;
	String baseurl1 = "https://yallacompare.com/uae/en/credit-cards/";
	String baseurl2 = "https://yallacompare.com/uae/en/credit-cards/fab-etihad-infinite-cards/";

	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	DBManager dbm = new DBManager();
	String tableName = "Automation.YallaCards";
	String ftext;
	String btext;

	@BeforeSuite
	public void setup() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 5);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

	}

	@Test
	public void ccdata() {
		try {
			driver.get(baseurl1);
			
			driver.manage().window().maximize();
			
			int blistcount = 0;
		
				
				Thread.sleep(2000);	
			Elements.changebank(driver).click();
			
			Thread.sleep(1000);
			List<WebElement> bsel = driver.findElements(By.xpath("//*[@id='select2-bankFilter-results']/li"));
			blistcount = bsel.size();
			System.out.println("bank count "+blistcount);
			
			
			Elements.changefeature(driver).click();
			Thread.sleep(1000);
			
			List<WebElement> fsel = driver.findElements(By.xpath("//*[@id='select2-featuresFilter-results']/li"));
			int listcount = fsel.size();
			System.out.println("feature count "+ listcount);
			Elements.changefeature(driver).click();
			
			
				for (int fea=15;fea<=15;fea++){
				System.out.println("inide feature loop getting next feature ");
				try{
					Elements.changefeature(driver).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//*[@id='select2-featuresFilter-results']/li["+fea+"]")).click();
					Thread.sleep(1000);
					ftext = Elements.changefeature(driver).getText();
				System.out.println("feature text is " + ftext);
				//bsel.get(ban).click();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			
				for(int ban=9;ban<=9;ban++){
				
					System.out.println("inide bank loop getting next bank ");
					try{
						Elements.changebank(driver).click();
						Thread.sleep(1000);
						
						driver.findElement(By.xpath("//*[@id='select2-bankFilter-results']/li["+ban+"]")).click();
						Thread.sleep(1000);
						btext =Elements.changebank(driver).getText();
						Thread.sleep(1000);
					
					System.out.println("bank text is " + btext);
					//bsel.get(ban).click();
					}catch(Exception e){
						e.printStackTrace();
					}
					Thread.sleep(1000);

			Thread.sleep(1000);
			Elements.filter(driver).click();
			Thread.sleep(1000);

			Boolean looper = true;

			while (looper) {
				System.out.println("inside while loop");
				List<WebElement> cc = driver.findElements(By.className("srpTable___row__primary "));
				int cccount = cc.size();
				System.out.println("number of cards " + cccount);
				if(cccount>0){
				for (int i = 1; i <=cccount; i++) {

					JSONObject CCData = new JSONObject();

					String ccName = Elements.creditcards(driver, i, 1).getText();

					System.out.println("credit card name :" + ccName);

					CCData.put("Card Name :", ccName);
					try {

						WebElement ccName1 = Elements.creditcardsimage(driver, i, 1);
						String logoSRC = ccName1.getAttribute("src");
						System.out.println("image url is " + logoSRC);

						URL imageURL = new URL(logoSRC);
						BufferedImage saveImage = ImageIO.read(imageURL);

						ImageIO.write(saveImage, "png", new File("C:\\ccimage\\" + ccName + ".png"));
						String imgpath = "C:\\ccimage\\" + ccName + ".png";

						String imgurl = utilities.Makeurl.geturl(imgpath);
						System.out.println("api url :" + imgurl);

						CCData.put("Image Url :", imgurl);

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// driver.close();
					}

					String Msal = Elements.creditcards(driver, i, 2).getText();

					System.out.println("Minimum Salary :" + Msal);
					CCData.put("Minimum Salary :", Msal);

					String Afee = Elements.creditcards(driver, i, 3).getText();

					System.out.println("Annual fee :" + Afee);

					CCData.put("Annual Fee:", Afee);

					String Rate = Elements.creditcards(driver, i, 4).getText();

					System.out.println("Rate  :" + Rate);

					CCData.put("Rate :", Rate);

					String Stransfer = Elements.creditcards(driver, i, 5).getText();

					System.out.println("salary transfer :" + Stransfer);
					CCData.put("Salary Transfer :", Stransfer);

					
					/*try {
						Elements.viewdetails(driver, i).click();
						String description = Elements.desc(driver).getText();
						String features = Elements.features(driver).getText();

						System.out.println("Features :" + features);
						System.out.println("Description :" + description);

						CCData.put("Features :", features);
						CCData.put("Description :", description);
						
					} catch (Exception e) {

					}
					try {
						String offers = Elements.offers(driver).getText();

						System.out.println("offers :" + offers);
						CCData.put("Offers :", offers);

					} catch (Exception e) {

					}
					Elements.viewdetails(driver, i).click();*/
					String NameURL="";
					try{
					NameURL = Elements.moreinfo(driver,i).getAttribute("href");
					Thread.sleep(1000);
					}catch(Exception e){
						e.printStackTrace();
					}
					System.out.println("more info URL : " + NameURL);

					((JavascriptExecutor) driver).executeScript("window.open()");

					ArrayList<String> handles = new ArrayList<String>(driver.getWindowHandles());

					System.out.println("Tabs :" + handles.size());

					if (handles.size() > 1) {

						driver.switchTo().window(handles.get(1));

						driver.get(NameURL);
						Thread.sleep(3000);
						
						try{
							String ifin = Elements.islamicfin(driver).getText();
							
							String cb = Elements.cashback(driver).getText();
							System.out.println("Islamic finance :" + ifin);
							CCData.put("Islamic finance :", ifin);
							System.out.println("cashback :" + cb);
							CCData.put("CashBack:", cb);
							
							List <WebElement> infoelements = driver.findElements(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div"));
							int iecount = infoelements.size();
							System.out.println("information element found are " + iecount);
							//System.out.println("list elements are " + infoelements.get(0).getText());
							//System.out.println("list elements are " + infoelements.get(1).getText());
							//System.out.println("list elements are " + infoelements.get(2).getText());
							 //*[@id="body"]/main/div[5]/div/div[1]/div[1]
							for(int info=1;info<=iecount;info++){
								String infotext = null;
								try{
							infotext = driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
							
							System.out.println("the value of info is " + infotext);
								}catch(Exception e){
									e.printStackTrace();
								}
								
							if(infotext.contains("Offers")){
								
								//String offers = Elements.offers(driver).getText();
								String offers =driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
								System.out.println("offers :" + offers);
								CCData.put("Offers :", offers);
								
							}else if (infotext.contains("Features")){
								String features =driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
								System.out.println("Features :" + features);
								CCData.put("Features :", features);
								
							}else if (infotext.contains("Additional Information")){
								String addinfo = driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
								
								System.out.println("Additional info" + addinfo);
								CCData.put("Additional Information:", addinfo);
								
							}else if (infotext.contains("Example")){
								String ex = driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
								
								System.out.println("Example " + ex);
								CCData.put("Example :", ex);
								
							}
							else {
								String description = driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div["+info+"]")).getText();
								
								
								System.out.println("Description :" + description);

								
								CCData.put("Description :", description);
								
							}
							
							
							/*String rdoc= Elements.reqdoc(driver).getText();
							System.out.println("Required Documents: " + rdoc);
							CCData.put("Required Documents:", rdoc);
							
							String ele= Elements.eligibility(driver).getText();
							System.out.println("Eligibility :" + ele);
							CCData.put("Eligibility :", ele);*/
							
							
										

							
							}
							
							
						}catch(Exception e){
							//e.printStackTrace();
						}
						
						driver.close();
						driver.switchTo().window(handles.get(0));
					}


					String jsondata = CCData.toString();
					System.out.println("Json data : " + jsondata);
					
					jsondata = Sanitize(jsondata);

					try {

						System.out.println("Going to insert data in table");

						String Feature = ftext;
						Feature = Sanitize(Feature);

						String Bank = btext;
						Bank = Sanitize(Bank);

						dbm.SetPractoLabData(Bank, ccName, jsondata, Feature, tableName);

					} catch (SQLServerException e) {

						dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

						// e.printStackTrace();

					} catch (Exception e) {

						dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

						e.printStackTrace();

					}

				}

				System.out.println("clicking next page");

				try {
					WebElement next = driver.findElement(By.xpath("//*[@class='nextPage']"));
					System.out.println("attribute value of next button  " + next.getAttribute("Class"));
				} catch (NoSuchElementException e) {
					looper = false;
				}

				try {
					Actions builder = new Actions(driver);
				     builder.moveToElement(Elements.nextpage(driver) ).click( Elements.nextpage(driver) );
				     builder.perform();
					//JavascriptExecutor executor = (JavascriptExecutor)driver;
					//executor.executeScript("arguments[0].click;", Elements.nextpage(driver));
					//Elements.nextpage(driver).click();
					Thread.sleep(15000);
				} catch (Exception e) {
					
					//e.printStackTrace();
					
				}

				}else{
					String Feature = ftext;
					Feature = Sanitize(Feature);

					String Bank = btext;
					Bank = Sanitize(Bank);
					dbm.SetPractoLabData(Bank, "No card found", "No Data", Feature, tableName);
					looper = false;
					continue;
				}
				}
			}
			
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public String Sanitize(String inp){
		
		String output = "";
		
		output = inp.replace("'", "");
		
		return output;
		
	}

}
