package selenium.merci.prod.tam;


import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import selenium.merci.prod.constants.TAM_Constants;

import com.amadeus.selenium.runner.SeleniumSEPTestAdvanced;

/**
 * Prod Sanity for TAM
 * @author rshivaswamy
 *
 */

public class TAMProdJSP_RT_1PAX extends SeleniumSEPTestAdvanced{
	

	


	@Override
	public void localSetUp(){
		setDriverClass(FirefoxDriver.class);
		setBaseUrl("http://m.tam.com.br");
	}


	@Test
	public void scenario() throws Exception{

		openPage("/b2c/vgn/img/mobile/html/BR_pt_BR/index.htm");

		searchPage();

		calPage();

		depAvailPage();

		retAvailPage();

		fareReviewPage();

		alipPage();

		purcPage();

		confPage();

	}


	private void confPage() {
		// TODO Auto-generated method stub

	}


	private void purcPage() {
		// TODO Auto-generated method stub
		
		
		//verify cyberSource
		String viewSource = driver.getPageSource();
		
		if(viewSource.contains("clear.png") && viewSource.contains("check.js") && viewSource.contains("fp.swf")){
			System.out.println("cyberSource Validation Success");
		}
		
		//CC Details
		
		driver.findElement(By.id(TAM_Constants.NAME_ON_CARD)).sendKeys("ETVTST");
		new Select(driver.findElement(By.id(TAM_Constants.CARDTYPE_DROPDOWN))).selectByVisibleText("Visa");
		driver.findElement(By.id(TAM_Constants.CARD_NUMBER)).sendKeys("4012999999999999");
		driver.findElement(By.id(TAM_Constants.CVV_CODE)).sendKeys("123");
		new Select(driver.findElement(By.id(TAM_Constants.EXP_YEAR))).selectByVisibleText("2015");
		
		
		
		//Billing address
		
		driver.findElement(By.id(TAM_Constants.BILLING_FIRSTLINE_ADDRESS)).sendKeys("Address Line one");
		driver.findElement(By.id(TAM_Constants.BILLING_SECONDLINE_ADDRESS)).sendKeys("Address Line Two");
		driver.findElement(By.id(TAM_Constants.BILLING_CITY_ADDRESS)).sendKeys("City");
		driver.findElement(By.id(TAM_Constants.BILLING_STATE_ADDRESS)).sendKeys("KA");
		driver.findElement(By.id(TAM_Constants.BILLING_ZIPCODE_ADDRESS)).sendKeys("5600001");
		driver.findElement(By.id(TAM_Constants.BILLING_COUNTRY)).sendKeys("USA");
		
		
		//Click confirm
		driver.findElement(By.id(TAM_Constants.CONDITIONS_CHECKBOX)).click();
		driver.findElement(By.id(TAM_Constants.PURCPAGE_CONTINUR)).click();

	}


	private void alipPage() {
		// TODO Auto-generated method stub
		
		enterContactDetails();
		enterPAXDetails();
		
		
		driver.findElement(By.id(TAM_Constants.ALPI_CONTINUE)).click();

	}


	private void enterPAXDetails() {
		
		
		Select title = new Select(driver.findElement(By.id(TAM_Constants.PAX_TITLE)));
		int sizeOfDropDown = title.getOptions().size();
		
		title.selectByIndex(sizeOfDropDown - 1);
		
		// enter first name
		//TODO: remove hard coding
		driver.findElement(By.id(TAM_Constants.PAX_FIRSTNAME)).sendKeys("FirstName");
		
		// enter last name
		//TODO: remove hard coding
		driver.findElement(By.id(TAM_Constants.PAX_LASTNAME)).sendKeys("LastName");
		
		// enter DOB
		//TODO: remove hard coding
		Select day = new Select(driver.findElement(By.id(TAM_Constants.PAX_DAY_DOB)));
		int sizeOfDayDropDown = day.getOptions().size();
		day.selectByIndex(sizeOfDayDropDown-1);

		//TODO: remove hard coding		
		Select month = new Select(driver.findElement(By.id(TAM_Constants.PAX_MONTH_DOB)));
		int sizeOfMonthDropDown = month.getOptions().size();
		month.selectByIndex(sizeOfMonthDropDown-1);
		
		
		Select year = new Select(driver.findElement(By.id(TAM_Constants.PAX_YER_DOB)));
		year.selectByVisibleText("1985");
		
	}


	private void enterContactDetails() {
		
		
		driver.findElement(By.name(TAM_Constants.CONTACT_MOBILE)).sendKeys("1111111111");
		driver.findElement(By.name(TAM_Constants.CONTACT_SMS_NOTIFICATION)).sendKeys("2222222222");
		driver.findElement(By.name(TAM_Constants.CONTACT_EMAIL)).sendKeys("test@test.com");
	}


	private void fareReviewPage() {
		// TODO Auto-generated method stub
		driver.findElement(By.xpath(TAM_Constants.FARE_REVIEW_CONTINUE_RT)).click();
	}


	private void retAvailPage() {
		// TODO Auto-generated method stub
		
		//Click on Price

		driver.findElement(By.id(TAM_Constants.SORT_BY_PRICE)).click();
		sortingValidation(By.className(TAM_Constants.FARE_PRICES));
		
		//Click on Time
		driver.findElement(By.id(TAM_Constants.SORT_BY_TIME)).click();

		//Click on Duration
		driver.findElement(By.id(TAM_Constants.SORT_BY_DURATION)).click();
		
		// Click on the first avail list
		driver.findElement(By.xpath(TAM_Constants.FLIGHT_ITIN_RETURN)).click();
		
		//Click continue on the upsell page
		driver.findElement(By.xpath(TAM_Constants.CONTINUE_BUTTON_UPSELL_PAGE_RETURN)).click();

	}


	private void depAvailPage() {
		// TODO Auto-generated method stub
		
		//Click on Price
		
		driver.findElement(By.id(TAM_Constants.SORT_BY_PRICE)).click();
		sortingValidation(By.className(TAM_Constants.FARE_PRICES));
		

		//Click on Time
		driver.findElement(By.id(TAM_Constants.SORT_BY_TIME)).click();
		
		//Click on Duration
		driver.findElement(By.id(TAM_Constants.SORT_BY_DURATION)).click();
		
		
		// Click on the first avail list
		driver.findElement(By.xpath(TAM_Constants.FLIGHT_ITIN_DEP)).click();
		
		//Click continue on the upsell page
		driver.findElement(By.xpath(TAM_Constants.CONTINUE_BUTTON_UPSELL_PAGE_DEP)).click();

	}


	private void sortingValidation(By sortingID) {
		// TODO Auto-generated method stub
		List<WebElement> sortedList = driver.findElements(sortingID);
		double previousFareValue = 0;
		
		for (WebElement webElement : sortedList) {
			
			double currentFareValue;
			
			String fareAmount = webElement.getText();
			String[] onlyFareValue = fareAmount.split(" ");
			System.out.println(onlyFareValue[0]);
			
			
			currentFareValue = Double.parseDouble(onlyFareValue[0]);
			
			if(currentFareValue >= previousFareValue){
//				System.out.println(currentFareValue+  " >= " + previousFareValue);
				previousFareValue = currentFareValue;
				
			}else{
				//Fail the test case
				System.out.println("this is not sorted");
			}
		}
	}


	private void calPage() {
		//TODO: move hard Coding
		
		driver.findElement(By.cssSelector(TAM_Constants.CAL_CONTINUE)).click();

	}


	private void searchPage() {
		//Click on flight bookin
		//TODO: move hard Coding
		
		driver.findElement(By.id(TAM_Constants.BOOK_FLIGHT)).click();

		//Enter From location
		//TODO: move hard Coding
		
		driver.findElement(By.id(TAM_Constants.FROM_LOCATION)).sendKeys("RIO");

		//Enter To location
		//TODO: move hard Coding
		
		driver.findElement(By.id(TAM_Constants.TO_LOCATION)).sendKeys("SSA");

		//Select type of Trip || Select round Trip
		//TODO: move hard Coding
		driver.findElement(By.id(TAM_Constants.TRIP_TYPE_RT)).click();

		//Update the dates
		updateDates();

		//FlexiDates operation
		handleFlexiDates();

		//Select class from the Drop Down
		handleCabinClassSelection();

		//Select PAX Counts
		/*
		 * No changes here
		 */

		//Click on Continue 
		
		
		driver.findElement(By.xpath(TAM_Constants.SEARCH_BUTTON)).click();
	}


	/*
	 * If the flexi Dates are not selected by default
	 * 	  Select it 
	 * Else
	 *    No operation required
	 */

	private void handleCabinClassSelection() {

		//TODO: move hard Coding
		
		Select classDropDown = new Select(driver.findElement(By.id(TAM_Constants.CABIN_CLASS)));
		int sizeOfDropDown = classDropDown.getOptions().size();

		/*
		 * selecting the last index as the domestic is displayed at the last index
		 */
		classDropDown.selectByIndex(sizeOfDropDown - 1);
	}


	private void handleFlexiDates() {

		//TODO: move hard Coding
		
		WebElement flexiDate = driver.findElement(By.id(TAM_Constants.FLEXIDATES_CHECKBOX));

		if(! (flexiDate.isSelected()) ){
			flexiDate.click();
		}
	}


	private void updateDates() {

		//TODO: move hard Coding
		
		Select depMonth = new Select(driver.findElement(By.id(TAM_Constants.CAL_MONTH)));

		//TODO: move hard Coding
		depMonth.selectByIndex(2);

	}
}
