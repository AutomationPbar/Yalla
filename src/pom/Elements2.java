package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Elements2 {
	
	WebDriver driver;
	
	public static WebElement creditcards(WebDriver driver,int row, int col){
		
		
		WebElement ccdata=driver.findElement(By.xpath(".//*[@id='personalLoanSrpTable']/tbody/tr[contains(@class,'srpTable___row__primary ') ]["+row+"]/td["+col+"]"));
		return ccdata;
		
		
	}
	
public static WebElement creditcardsimage(WebDriver driver,int row, int col){
		
		
		WebElement ccdata=driver.findElement(By.xpath("//*[@id='personalLoanSrpTable']/tbody/tr[contains(@class,'srpTable___row__primary ') ]["+row+"]/td["+col+"]/a/img"));
		return ccdata;
		
		
	}
	
	public static WebElement viewdetails(WebDriver driver, int row){
		
		
		
		WebElement vd=driver.findElement(By.xpath(".//*[@id='personalLoanSrpTable']/tbody/tr[contains(@class,'srpTable___row__viewMoreDetails ') ]["+row+"]"));
		return vd;
		
		
	}
	
	public static WebElement features(WebDriver driver){
		
		
		
		WebElement f=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[2]"));
		return f;
		
		
	}
	
	public static WebElement desc(WebDriver driver){
		
		
		
		WebElement d=driver.findElement(By.className("//*[@id='body']/main/div[5]/div/div[1]/div[1]"));
		return d;
		
		
	}
	
	public static WebElement offers(WebDriver driver){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[3]"));
		return o;
		
		
	}

	
	public static WebElement moreinfo(WebDriver driver, int row){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='personalLoanSrpTable']/tbody/tr[contains(@class,'srpTable___row__primary ') ]["+row+"]/td[8]/a"));
		return o;
		
		
	}
	
	public static WebElement islamicfin(WebDriver driver){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[3]/table/tbody/tr/td[4]"));
		return o;
		
		
	}
	public static WebElement cashback(WebDriver driver){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[3]/table/tbody/tr/td[5]"));
		return o;
		
		
	}
	
	public static WebElement addinfo(WebDriver driver){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[3]/ul[1]"));
		return o;
		
		
	}
public static WebElement eligibility(WebDriver driver){
		
		
		
		WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[3]/ul[3]"));
		return o;
		
		
	}
public static WebElement reqdoc(WebDriver driver){
	
	
	
	WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[3]/ul[2]"));
	return o;
	
	
}

public static WebElement example(WebDriver driver){
	
	
	
	WebElement o=driver.findElement(By.xpath("//*[@id='body']/main/div[5]/div/div[1]/div[4]"));
	return o;
	
	
}
	public static WebElement nextpage(WebDriver driver){
		
		
		
		WebElement n=driver.findElement(By.xpath("//*[@class='nextPage']"));
		return n;
		
		
	}
	
	public static WebElement changefeature(WebDriver driver){
		
		
		
		WebElement cf=driver.findElement(By.id("select2-featuresFilter-container"));
		return cf;
		
		
	}
	
	public static WebElement changebank(WebDriver driver){
		
		
		
		WebElement cf=driver.findElement(By.id("select2-bankFilter-container"));
		return cf;
		
		
	}
	
public static WebElement filter(WebDriver driver){
		
		
		
		WebElement cf=driver.findElement(By.xpath("//*[@class='button button filterButton']"));
		return cf;
		
		
	}
}
