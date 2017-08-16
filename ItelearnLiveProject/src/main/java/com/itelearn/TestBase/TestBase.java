package com.itelearn.TestBase;


import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.itelearn.Properties.PropertyFileReader;
import com.itelearn.Utility.ExcelUtility;
import com.itelearn.Utility.SeleniumHelper;

public class TestBase {

	//String keyword4, vEID4, vTDataName4;
	public String type;

	WebDriver driver;
	String TestCaseName;
	String RunMode;
	String xkeyword, xEID, vTDataNames;


	SeleniumHelper sh= new SeleniumHelper();
	ExcelUtility eu= new ExcelUtility();


	@BeforeTest
	//Setting the Chrome Browser property
	public static void settingBrowserProperties(){
		String prop1=	PropertyFileReader.getProperty("setUpName");
		String prop2= PropertyFileReader.getProperty("setUpPath");

		System.setProperty(prop1, prop2);
	}

	@Test
	public void basetest() throws IOException, InterruptedException{
		System.out.println("--------------Reading test cases----------------");

		String[][] testCaseSheet= eu.readExcel("TestCases");
		int sheetRowNum= eu.sheetRows;

		String[][] testStepSheet	= eu.readExcel("TestSteps");

		for(int k=1;k<sheetRowNum;k++){
			TestCaseName=	testCaseSheet[k][1];
			RunMode= testCaseSheet[k][3];
			System.out.println("The test case name is:"+ TestCaseName);


			if(RunMode.equalsIgnoreCase("Y")){


				System.out.println("--------  Reading the TestSteps Sheet ----------");


				int	TestStepRows= testStepSheet.length;

				for (int y=1;y<TestStepRows;y++){
					String	vTCID=testStepSheet[y][1];


					if(TestCaseName.equals(vTCID)){
						xkeyword= testStepSheet[y][5];
						xEID= testStepSheet[y][8];
						vTDataNames=testStepSheet[y][11];

						type= testStepSheet[y][12];

//						System.out.println("------------------Reading the EID sheet-------------");
//						String[][] EIDdataSheet= eu.readExcel("EID");
//
//						for(int m=1; m<eu.sheetRows;m++){
//							 type =EIDdataSheet[m][2];
//							 
//							System.out.println("The EID sheet locator data is :"+ type);
						//}
						try{
						sh.execute(xkeyword,vTDataNames,xEID,type);
						} catch(Exception e){
							System.out.println("An error has occured!!!" + e);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
@AfterTest
public void teardown(){
	sh.getDriver().quit();
	
}
}
