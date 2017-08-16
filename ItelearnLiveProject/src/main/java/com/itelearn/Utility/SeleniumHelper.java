	package com.itelearn.Utility;

	import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.itelearn.Properties.PropertyFileReader;


	public class SeleniumHelper {
		public static WebDriver driver;
		String url;
		ExcelUtility et= new ExcelUtility();
		
		//public Logger log=LogManager.getLogger(SeleniumHelper.class.getName());

		
		public void execute(String keyword, String fdata, String fElement, String fElementType) throws IOException, InterruptedException{
			switch(keyword) {

			case "openBrowser":
				openBwsr();
				break;

			case "navigateBrowser":
				navigateBwsr();
				break;

			case "clickLink":
				clicklnk(fElement,fElementType);
				break;
			
			case "clickButton":
				clickbtn(fElement,fElementType);
				break;

			case "typeText":
				typTxt(fElement, fdata, fElementType);
				break;

			case "verifytext":
				verifytxt(fElement, fdata, fElementType );
				break;



			case "verifyTitle":
				verifyPageTitle(fdata);
				break;


			case "maximizeWindow":
				maximizeWindow();
				break;


			case "mouseHover":
				mouseHover(fElement,fdata,fElementType);
				break;



			case "wait":
				wtTime(40);
				break;
			}

		}	


		@Test
		// To check the element type//
		public WebElement elementTyp(String fElement, String fElementType){
			if(fElementType.equalsIgnoreCase("linkText")){
				return driver.findElement(By.linkText(fElement));
			}
			else if(fElementType.equalsIgnoreCase("cssSelector")){ 
				return driver.findElement(By.cssSelector(fElement)); 
			}
			else {
				return driver.findElement(By.xpath(fElement)); 
			}
			
		}


		
		
		public void openBwsr() throws IOException{
			System.out.println("-----------Opening the Browser---------");
			String setName= PropertyFileReader.getProperty("setUpName");
			System.out.println(setName);
			String setPath= PropertyFileReader.getProperty("setUpPath");
			System.out.println(setPath);
			System.setProperty(setName, setPath);
			driver= new ChromeDriver();
			System.out.println("Opening Chrome browser");
		}


		
		public void navigateBwsr() throws IOException{
			System.out.println("-----------Navigate to the URL---------");
			String url = PropertyFileReader.getProperty("URL");
			System.out.println("Navigating to url ");
			driver.navigate().to(url);

		}

		@Test
		public void clickbtn(String vElement,String vType){
			System.out.println("-----------Clicking the button--------- for element " + vElement);
		
			if(vElement != null && !vElement.trim().equals("-")){
				driver.findElement(By.xpath(vElement)).click();
			}
		}




		//Getting the webdriver for FreelProjectsNoLogin Class Screenshot method//
		public WebDriver getDriver() {
			return driver;
		}


		
		public void maximizeWindow() throws InterruptedException{
			System.out.println("-----------Maximizing the Window---------");
			driver.manage().window().maximize();
		}

		
		public void mouseHover(String vElement, String vdata, String vType){
			System.out.println("----------MouseHover-------------");
			Actions act= new Actions(driver);
			WebElement we= elementTyp(vElement,vType);
			act.moveToElement(we).click().build().perform();
		}

		public void typTxt(String vElement, String vdata, String fElementType){
			System.out.println("-----------typing the text---------");
			if(vElement != null && !vElement.trim().equals("-")){
			WebElement we= elementTyp(vElement, fElementType);
			we.clear();
			we.sendKeys(vdata);
			}
		}

		
		public void clicklnk(String vElement, String fElementType){
			System.out.println("-----------Clicking the Link---------"+vElement);
			System.out.println("The element type is:"+ fElementType);
			if(vElement != null && !vElement.trim().equals("-")){
				WebElement element= elementTyp(vElement, fElementType);
				element.click();
			}

		}	


	
		
		public void wtTime(long fdata){
			System.out.println("-----------Introducing wait---------");{
				driver.manage().timeouts().implicitlyWait(fdata, TimeUnit.SECONDS);
			}
		}


		public void verifytxt(String vElement, String fdata, String vType){  
			System.out.println("-----------Verifying--------------------");
			WebElement we=  elementTyp(vElement, vType);
			String actual=		we.getText();
			System.out.println("Actual text is:"+ actual);
			System.out.println("Expected text is:"+ fdata);

			System.out.println(actual);
			Assert.assertEquals(actual, fdata);
		}

		
		public void verifyPageTitle(String fdata){
			System.out.println("-----------Verifying Page title--------------------");
			String actual=driver.getTitle();
			System.out.println();
			Assert.assertEquals(actual, fdata);
		}
	}	



	
