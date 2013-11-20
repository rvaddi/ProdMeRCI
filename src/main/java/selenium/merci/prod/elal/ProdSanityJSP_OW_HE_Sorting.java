package selenium.merci.prod.elal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.amadeus.selenium.runner.SeleniumSEPTestAdvanced;

public class ProdSanityJSP_OW_HE_Sorting extends SeleniumSEPTestAdvanced{


	protected final static String SORTBY_DURATION = "dur";
	protected final static String SORTBY_PRICE = "price";
	protected final static String SORTBY_TIME = "time";
	
	protected final static String FIRST_ITIN_DISPLAYED = "styleLink";
	public static final String FARE_REVIEW_CONTINUE_OW = ".//*[@id='page_wrapper']/form/div/div[12]/div/div/input";
	
	public static final String ALPI_CONTINUE = "continueBtn";
	
	public static final String PAX_TITLE = "TITLE_1";
	public static final String PAX_FIRSTNAME = "FIRST_NAME_1";
	public static final String PAX_LASTNAME = "LAST_NAME_1";
	
	
	protected static final String PAX_MIDDLENAME = "IDENTITY_DOCUMENT_MIDDLE_NAME_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_GENDER = "psptgender1";
	protected static final String PAX_ALPI_DAY_DOB = "Day_IDENTITY_DOCUMENT_DATE_OF_BIRTH_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_MONTH_DOB = "Month_IDENTITY_DOCUMENT_DATE_OF_BIRTH_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_YEAR_DOB = "Year_IDENTITY_DOCUMENT_DATE_OF_BIRTH_1_PSPT_BK_GLOBAL_DEFAULT_1";
	
	protected static final String PAX_ALPI_COUNTRY = "IDENTITY_DOCUMENT_NATIONALITY_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_TYPE = "IDENTITY_DOCUMENT_TYPE_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_NUNBER = "IDENTITY_DOCUMENT_ISSUING_COUNTRY_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_ISSUE_COUNTRY = "IDENTITY_DOCUMENT_ISSUING_COUNTRY_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_EXP_DAY = "Day_IDENTITY_DOCUMENT_EXPIRY_DATE_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_EXP_MONTH = "Month_IDENTITY_DOCUMENT_EXPIRY_DATE_1_PSPT_BK_GLOBAL_DEFAULT_1";
	protected static final String PAX_ALPI_DOC_EXP_YEAR = "Month_IDENTITY_DOCUMENT_EXPIRY_DATE_1_PSPT_BK_GLOBAL_DEFAULT_1";
	
	protected static final String ALPI_CONTINUR = "continueBtn";

	@Override
	public void localSetUp(){
		setDriverClass(FirefoxDriver.class);
	}



	@Test
	public void scenario() throws Exception{

		
		readPropFile();
		
		handleCustomerSearchPage();

		calPage();

		availPage();
		
		fareReviewPage();
		
		ALPIPage();



	}
	
	
	private void readPropFile() throws IOException {
		
		try {
			File file = new File("HE_COUNTRY_DROPDOWN.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			String countryList = properties.getProperty("COUNTRY_LIST");
			String[] splitedCountryList = countryList.split(",");
			
			List<String> storedCountryList = new ArrayList<String>();
			
			for (String string : splitedCountryList) {
				storedCountryList.add(string);
			}
			
			fileInput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}



	private void ALPIPage() {

		enterContactDetails();
		enterPAXDetails();
		
		
		driver.findElement(By.id(ALPI_CONTINUE)).click();	

	}
	
	private void enterPAXDetails() {
		
		
		Select title = new Select(driver.findElement(By.id(PAX_TITLE)));
		int sizeOfDropDown = title.getOptions().size();
		title.selectByIndex(sizeOfDropDown - 1);
		
		
		
		// enter first name
		//TODO: remove hard coding
		driver.findElement(By.id(PAX_FIRSTNAME)).sendKeys("FirstName");
		
		// enter last name
		//TODO: remove hard coding
		driver.findElement(By.id(PAX_LASTNAME)).sendKeys("LastName");

		
		driver.findElement(By.name(PAX_MIDDLENAME)).sendKeys("mname");
		
		//DOB day id
		Select dayOfDOB = new Select(driver.findElement(By.id(PAX_ALPI_DAY_DOB)));
		dayOfDOB.selectByVisibleText("15");
		
		//DOB month id
		Select monthOfDOB = new Select(driver.findElement(By.id(PAX_ALPI_MONTH_DOB)));
		monthOfDOB.selectByIndex(monthOfDOB.getOptions().size()-1);
		
		
		//DOB Year id
		Select yearOfDOB = new Select(driver.findElement(By.id(PAX_ALPI_YEAR_DOB)));
		yearOfDOB.selectByVisibleText("1980");
		
		// country id
		Select countryDropDown = new Select(driver.findElement(By.id(PAX_ALPI_COUNTRY)));
		countryDropDown.selectByIndex(countryDropDown.getOptions().size()-1);
		
		readValuesFromCountryDropDown(countryDropDown);
		
		// doc type id
		Select docType = new Select(driver.findElement(By.id(PAX_ALPI_DOC_TYPE)));
		docType.selectByIndex(docType.getOptions().size()-1);
		
		
		// doc number name
		driver.findElement(By.id(PAX_ALPI_DOC_NUNBER)).sendKeys("ABCD1234");
		
		// place of issue id
		Select issurCountry = new Select(driver.findElement(By.id(PAX_ALPI_DOC_ISSUE_COUNTRY)));
		issurCountry.selectByIndex(issurCountry.getOptions().size()-1);
		
		// exp day id
		Select dayExpDate = new Select(driver.findElement(By.id(PAX_ALPI_DOC_EXP_DAY)));
		dayExpDate.selectByIndex(dayExpDate.getOptions().size()-1);
		
		// exp month id
		Select monthExpDate = new Select(driver.findElement(By.id(PAX_ALPI_DOC_EXP_MONTH)));
		monthExpDate.selectByIndex(monthExpDate.getOptions().size()-1);
		
		// exp year id
		Select yearExpDate = new Select(driver.findElement(By.id(PAX_ALPI_DOC_EXP_YEAR)));
		yearExpDate.selectByIndex(yearExpDate.getOptions().size()-1);
		
	}
	
	private void readValuesFromCountryDropDown(Select countryDropDown) {

		ListIterator<WebElement> iteratot = countryDropDown.getOptions().listIterator();
		
		for (Iterator iterator = countryDropDown.getOptions().listIterator(); iterator.hasNext();) {
			
		}
		
		
	}



	private void enterContactDetails() {
	 	
	 	driver.findElement(By.name("CONTACT_POINT_HOME_PHONE")).sendKeys("11111111");
	 	driver.findElement(By.name("CONTACT_POINT_MOBILE_1")).sendKeys("22222222");
	 	driver.findElement(By.name("CONTACT_POINT_BUSINESS_PHONE")).sendKeys("33333333");
	 	driver.findElement(By.name("CONTACT_POINT_EMAIL_1")).sendKeys("test@test.com");
		
	}
	
	private void fareReviewPage() {
		
	driver.findElement(By.xpath(FARE_REVIEW_CONTINUE_OW)).click();

	}

	private void handleSorting() {
		// click on price sorting
		driver.findElement(By.id(SORTBY_PRICE));
		// .priceAlign.avail.marginRight0px
		sortingValidation(By.cssSelector((".priceAlign.avail.marginRight0px")));


		// click on time sorting
		driver.findElement(By.id(SORTBY_TIME)).click();

		// click on duration sorting
		driver.findElement(By.id(SORTBY_DURATION)).click();
	}

	private void availPage() {
		handleSorting();
		
		driver.findElement(By.className(FIRST_ITIN_DISPLAYED)).click();

	}



	private void calPage() {

		driver.findElement(By.id("continueBtn")).click();
	}



	private void handleCustomerSearchPage() throws Exception,
	InterruptedException {
		openPage("http://m.elal.co.il/booking?lang=he&country=Argentina");

		// click on OneWay
		driver.findElement(By.id("oneway")).click();

		Thread.sleep(2000);
		// click on From field
		driver.findElement(By.id("origin")).click();
		driver.findElement(By.id("origin")).sendKeys("TLV");
		driver.switchTo().activeElement();
		Thread.sleep(1000);
		driver.findElement(By.linkText("ישראל, תל אביב (TLV)")).click();


		driver.findElement(By.id("destination")).clear();
		driver.findElement(By.id("destination")).sendKeys("NYC");
		driver.switchTo().activeElement();
		Thread.sleep(1000);
		driver.findElement(By.linkText("ארהב, ניו יורק (NYC)")).click();


		Thread.sleep(1000);

		driver.findElement(By.id("departure")).click();
		driver.switchTo().activeElement();
		String windowHandle = driver.getWindowHandle();
		driver.switchTo().window(windowHandle);
		driver.findElement(By.id("departure")).click();


		Thread.sleep(6000);

		handleDates();

		driver.findElement(By.className("searchButtonDiv")).click();
		
		
		handlePopUpWindow();
		
		
		Thread.sleep(5000);

	}



	private void handlePopUpWindow() {

		driver.switchTo().alert().accept();
		
		/*Set<String> driverWindows = driver.getWindowHandles();
		for (String string : driverWindows) {
			if(driver.getWindowHandle().contains("Security")){
				driver.switchTo().alert().accept();
			}
			
		}
		
		driver.switchTo().window(driver.getWindowHandle());*/
		
	}



	private void handleDates() {
		// wait for few seconds
		List<WebElement> datePicker = driver.findElements(By.className("dw-ul"));

		// 0th item is date, 1st item is month, 2nd item is year
		WebElement dayOfCal = datePicker.get(0);
		WebElement monthOfCal = datePicker.get(1);
		WebElement yearOfCal = datePicker.get(2);

		//dw-ul.dw-bf.dw-i : yearOfCal
		commonDateHandling(yearOfCal,"dw-i","2014");

		//dw-ul.dw-bf.dw-li.dw-i.dw-mon : monthOfCal
		commonDateHandling(monthOfCal,"dw-mon","יול");

		//dw-ul.dw-bf.dw-li.dw-i : dayOfCal
		commonDateHandling(dayOfCal,"dwb","20");

		List<WebElement> setList = driver.findElements(By.className("dwb"));
		for (WebElement webElement : setList) {
			if(webElement.getText().equalsIgnoreCase("בחר")){
				webElement.click();
				break;
			}
		}
	}

	private void commonDateHandling(WebElement refWebElement,String className,String selectItem){


		//dw-ul.dw-bf.dw-li.dw-i.dw-mon : monthOfCal
		List<WebElement> webElementList = refWebElement.findElements(By.className(className));
		for (WebElement webElement : webElementList) {
			System.out.println(webElement.getText());
			if(webElement.getText().equalsIgnoreCase(selectItem)){
				webElement.click();
			}
		}

	}


	private void sortingValidation(By cssSelector) {
		// TODO Auto-generated method stub
		List<WebElement> sortedList = driver.findElements(cssSelector);
		double previousFareValue = 0;

		List<WebElement> validSortedList = getValidSortedList(sortedList); 


		for (WebElement webElement : validSortedList) {

			double currentFareValue;

			String fareAmount = webElement.getText();
			System.out.println(fareAmount);
		}
	}


	private List<WebElement> getValidSortedList(List<WebElement> sortedList) {

		List<WebElement> validSortedList = new ArrayList<WebElement>() ;

		for (WebElement webElement : sortedList) {
			String fareAmount = webElement.getText();
			if(! fareAmount.isEmpty()){
				validSortedList.add(webElement);
			}
		}

		return validSortedList;
	}
}

