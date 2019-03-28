package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import pom.Elements;
import utilities.DBManager;

public class Getdata {

	WebDriver driver;
	WebDriverWait wait;
	String baseurl1 = "https://yallacompare.com/uae/en/credit-cards/";
	String baseurl2 = "https://yallacompare.com/uae/en/credit-cards/fab-etihad-infinite-cards/";

	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	DBManager dbm = new DBManager();
	String tableName = "Automation.YallaCards";

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

			Elements.changefeature(driver).click();
			Thread.sleep(1000);
			List<WebElement> fsel = driver.findElements(By.xpath("//*[@id='select2-featuresFilter-results']/li"));
			int listcount = fsel.size();
			String ftext = fsel.get(1).getText();
			System.out.println("ftext is " + ftext);
			fsel.get(1).click();

			Thread.sleep(1000);
			Elements.filter(driver).click();
			Thread.sleep(1000);

			Boolean looper = true;

			while (looper) {
				System.out.println("inside while loop");
				List<WebElement> cc = driver.findElements(By.className("srpTable___row__primary "));
				int cccount = cc.size();
				System.out.println("number of cards " + cccount);
				for (int i = 1; i <= cccount; i++) {

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

					Elements.viewdetails(driver, i).click();
					try {
						String features = Elements.features(driver).getText();

						System.out.println("Features :" + features);

						CCData.put("Features :", features);
					} catch (Exception e) {

					}
					try {
						String offers = Elements.offers(driver).getText();

						System.out.println("offers :" + offers);
						CCData.put("Offers :", offers);

					} catch (Exception e) {

					}
					Elements.viewdetails(driver, i).click();

					String jsondata = CCData.toString();
					System.out.println("Json data : " + jsondata);

					try {

						System.out.println("Going to insert data in table");

						String Feature = "Air Miles";

						String Bank = "Bank";

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
					Elements.nextpage(driver).click();
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
