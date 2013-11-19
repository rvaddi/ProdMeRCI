package selenium.merci.prod.saudi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;


import com.amadeus.selenium.runner.SeleniumSEPTestAdvanced;


/**
 * Prod sanity for Saudi, basic booking flow
 * @author rshivaswamy
 *
 */

public class SaudiProdJSP_OW_1PAX extends SeleniumSEPTestAdvanced{


	protected final static String CUSTOMER_LANG_SELECT = "submit";
	protected final static String CUSTOMER_INDEXPAGE = ".linkHref";
	protected final static String CUSTOMER_SEARCHPAGE_DEPMONTH = "sMonth";
	protected final static String CUSTOMER_SEARCHPAGE_DEPYEAR = "sYear";
	protected final static String CUSTOMER_SEARCHPAGE_RETMONTH = "rMonth";
	protected final static String CUSTOMER_SEARCHPAGE_RETYEAR = "rYear";
	protected final static String CUSTOMER_SEARCHPAGE_SUBMIT = "btnStyleBooking";
	protected final static String CALPAGE_SUBMIT = "continueBtn";

	protected final static String SORTBY_DURATION = "dur";
	protected final static String SORTBY_PRICE = "price";
	protected final static String SORTBY_TIME = "time";
	protected final static String FIRST_ITIN_DISPLAYED = "styleLink";
	
	public static final String FARE_REVIEW_CONTINUE_OW = ".//*[@id='page_wrapper']/form/div/div[12]/div/div/input";
	public static final String ALPI_CONTINUE = "continueBtn";
	
	public static final String PAX_TITLE = "TITLE_1";
	public static final String PAX_FIRSTNAME = "FIRST_NAME_1";
	public static final String PAX_LASTNAME = "LAST_NAME_1";


	@Override
	public FirefoxProfile localGetFirefoxProfile() throws Exception{
		File SQProfileDir = new File("D:\\Work\\profile\\SQMobile");
		FirefoxProfile profile = new FirefoxProfile(SQProfileDir);
		reporter.report("User Agent Switcher", "User Agent switched to iPhone");
		return profile;
	}

	@Override
	public void localSetUp(){
		setDriverClass(FirefoxDriver.class);
	}


	@Test
	public void scenario() throws Exception{

		// Customer page
		openPage("http://m.saudiairlines.com");

		driver.findElement(By.name(CUSTOMER_LANG_SELECT)).click();
		driver.findElement(By.cssSelector(CUSTOMER_INDEXPAGE)).click();


		customerSearchPage();

		calPage();

		depAvailPage();

//		retAvailPage();

		fareReviewPage();

		ALPIPage();

		PURCPage();

		confPage();



	}

	private void confPage() {
		// TODO Auto-generated method stub

	}

	private void PURCPage() {
		// TODO Auto-generated method stub

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
		
		
		Select countryDropdown = new Select(driver.findElement(By.id("NATIONALITY_1")));

		try {
			
			countryDropdown.selectByVisibleText("Israel");
			
		} catch (NoSuchElementException e) {

				System.out.println("Israel country is not displayed in the country drop dow.");

		}
		countryDropdown.selectByVisibleText("India");
		
		
			
	}

	private void enterContactDetails() {
		// TODO Auto-generated method stub
	 	driver.findElement(By.id("COUNTRY")).sendKeys("+91");
	 	driver.findElement(By.id("PHONE_NUMBER")).sendKeys("11111111");
	 	driver.findElement(By.name("NOTIF_VALUE_1_1")).sendKeys("22222222");
	 	driver.findElement(By.name("CONTACT_POINT_EMAIL_1")).sendKeys("test@test.com");
		
	}

	private void fareReviewPage() {
	
	driver.findElement(By.xpath(FARE_REVIEW_CONTINUE_OW)).click();

	}

	private void retAvailPage() {
	
		handleSorting();
		
		driver.findElement(By.className(FIRST_ITIN_DISPLAYED)).click();		
	}

	private void depAvailPage() {

		handleSorting();

		// select the first flight from the list
		driver.findElement(By.className(FIRST_ITIN_DISPLAYED)).click();

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

	private void sortingValidation(By cssSelector) {
		// TODO Auto-generated method stub
		List<WebElement> sortedList = driver.findElements(cssSelector);
		double previousFareValue = 0;

		List<WebElement> validSortedList = getValidSortedList(sortedList); 


		for (WebElement webElement : validSortedList) {

			double currentFareValue;

			String fareAmount = webElement.getText();
			System.out.println(fareAmount);
			/*String[] onlyFareValue = fareAmount.split(" ");
			System.out.println(onlyFareValue[0]);


			currentFareValue = Double.parseDouble(onlyFareValue[0]);

			if(currentFareValue >= previousFareValue){
//				System.out.println(currentFareValue+  " >= " + previousFareValue);
				previousFareValue = currentFareValue;

			}else{
				//Fail the test case
				System.out.println("this is not sorted");
			}*/
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

	private void calPage() {

		driver.findElement(By.id(CALPAGE_SUBMIT)).click();

	}

	private void customerSearchPage() {
		
		// customer search page
		
		Select tripType = new Select(driver.findElement(By.name("tripType")));
		tripType.selectByIndex(0);
		
		Select depMonth = new Select(driver.findElement(By.id(CUSTOMER_SEARCHPAGE_DEPMONTH)));
		depMonth.selectByIndex(1);

		Select depYear = new Select(driver.findElement(By.id(CUSTOMER_SEARCHPAGE_DEPYEAR)));
		depYear.selectByVisibleText("2014");


//		Select retpMonth = new Select(driver.findElement(By.id(CUSTOMER_SEARCHPAGE_RETMONTH)));
//		retpMonth.selectByIndex(2);

//		Select retYear = new Select(driver.findElement(By.id(CUSTOMER_SEARCHPAGE_RETYEAR)));
//		retYear.selectByVisibleText("2014");

		driver.findElement(By.id(CUSTOMER_SEARCHPAGE_SUBMIT)).click();
	}
}


