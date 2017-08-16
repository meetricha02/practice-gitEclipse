package com.itelearn.dataProviders;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.itelearn.Properties.PropertyFileReader;
import com.itelearn.Utility.ExcelUtility;
import com.itelearn.Utility.SeleniumHelper;


public class dataSets {
	//Reads the data from the dataprovider method in the class
	//Executes the test for both sets of data
	
	
	String key, eid, type, value;
	WebDriver driver;
	SeleniumHelper s;
	ExcelUtility e;

	@BeforeTest
	// Setting the Chrome Browser property
	public void settingBrowserProperties() {
		String prop1 = PropertyFileReader.getProperty("setUpName");
		String prop2 = PropertyFileReader.getProperty("setUpPath");
		System.setProperty(prop1, prop2);
	}
	
	
	@BeforeMethod
	public void initialization(){
		 e = new ExcelUtility();
		 s = new SeleniumHelper();
	}

	@Test(dataProvider = "logindata")
	public void loginDataSet( String Uname, String Pwd) throws IOException, InterruptedException {

		String Testcase = "TC_valid_dataset_001";

		String[][] testSteps = e.readExcel("TestSteps");
		int rows = e.sheetRows;

		for (int a = 1; a < rows; a++) {
			String TCID = testSteps[a][1];
		    value = testSteps[a][11];
		    type= testSteps[a][12];
			key = testSteps[a][5];
			System.out.println("Keyword is :-----------" + key);
			eid = testSteps[a][8];

			//If the value of the cell is "Username", then get the data from data providers//
			if (value.equals("Username")) {

				System.out.println("UserName is :" + value);
				value = Uname;
			}

			//If the value of the cell is "Password", then get the data from data providers//

			if (value.equals("Password")) {

				System.out.println("Password is :" + value);

				value = Pwd;
			}
			if (TCID != null && TCID.equalsIgnoreCase(Testcase)) {
				//Value from the data providers(Uname/Pwd) passed to the execute method in Selenium Helper Class//
				s.execute(key, value, eid, type);
			}

		}

	}

	// Using Data Providers to Login//
	@DataProvider(name = "logindata")
	@Test
	public Object[][] dataSet() throws IOException {
		Object[][] ob = new Object[2][2];

//		String[][] SheetDataSets = e.readExcel("Sheet1");
//		int rowNum= e.sheetRows;
//			for(int i=1;i<rowNum;i++ ){
//			String	Uvalue= SheetDataSets[i][1];
//			String	Pvalue= SheetDataSets[i][2];
						
		// [1] Username
		// [2] Password

			
		ob[1][0] ="annl@gmail.com" ;
		ob[1][1] = "12345";
		
		ob[0][0] ="tomt@gmail.com" ;
		ob[0][1] ="123456";
		
			
		return ob;

	}

}
