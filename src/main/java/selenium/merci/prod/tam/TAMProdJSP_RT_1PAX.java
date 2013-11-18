package selenium.merci.prod.tam;


import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.amadeus.selenium.extension.WebElementSikuli;
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

	}


	private void alipPage() {
		// TODO Auto-generated method stub

	}


	private void fareReviewPage() {
		// TODO Auto-generated method stub
		driver.findElement(By.xpath(".//*[@id='page_wrapper']/form/div/div[15]/div/div/input")).click();

	}


	private void retAvailPage() {
		// TODO Auto-generated method stub
		
		//Click on Price
		driver.findElement(By.id("price")).click();
		sortingValidation(By.className("farePrice"));
		
		//Click on Time
		driver.findElement(By.id("time")).click();
		
		//Click on Duration
		driver.findElement(By.id("dur")).click();
		
		
		// Click on the first avail list
		driver.findElement(By.xpath(".//*[@id='inBoundTable']/div[1]/a/ul")).click();
		
		//Click continue on the upsell page
		driver.findElement(By.xpath(".//*[@id='page_wrapper']/div[2]/div[3]/div/a")).click();

	}


	private void depAvailPage() {
		// TODO Auto-generated method stub
		
		//Click on Price
		driver.findElement(By.id("price")).click();
		sortingValidation(By.className("farePrice"));
		
		//Click on Time
		driver.findElement(By.id("time")).click();
		
		//Click on Duration
		driver.findElement(By.id("dur")).click();
		
		
		// Click on the first avail list
		driver.findElement(By.xpath(".//*[@id='outBoundTable']/div[1]/a/ul")).click();
		
		//Click continue on the upsell page
		driver.findElement(By.xpath(".//*[@id='page_wrapper']/div[2]/div[2]/div/a")).click();

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
		driver.findElement(By.cssSelector("a>span")).click();

	}


	private void searchPage() {
		//Click on flight bookin
		//TODO: move hard Coding
		driver.findElement(By.id("block_text")).click();

		//Enter From location
		//TODO: move hard Coding
		driver.findElement(By.id("B_LOCATION_1")).sendKeys("RIO");

		//Enter To location
		//TODO: move hard Coding
		driver.findElement(By.id("E_LOCATION_1")).sendKeys("SSA");

		//Select type of Trip || Select round Trip
		//TODO: move hard Coding
		driver.findElement(By.id("roundTrip")).click();

		//Update the dates
		updateDates();

		//FlexiDates operation
		handleFlexiDates();

		//Select class from the Drop Down
		handleCabinClassSelectio();

		//Select PAX Counts
		/*
		 * No changes here
		 */

		//Click on Continue 
		
		driver.findElement(By.xpath(".//*[@id='searchForm']/div[1]/div[5]/div[2]/div/input")).click();
	}


	/*
	 * If the flexi Dates are not selected by default
	 * 	  Select it 
	 * Else
	 *    No operation required
	 */

	private void handleCabinClassSelectio() {

		//TODO: move hard Coding
		Select classDropDown = new Select(driver.findElement(By.id("COMMERCIAL_FARE_FAMILY_1")));
		int sizeOfDropDown = classDropDown.getOptions().size();

		/*
		 * selecting the last index as the domestic is displayed at the last index
		 */
		classDropDown.selectByIndex(sizeOfDropDown - 1);
	}


	private void handleFlexiDates() {

		//TODO: move hard Coding
		WebElement flexiDate = driver.findElement(By.id("DATE_RANGE_VALUE_1"));

		if(! (flexiDate.isSelected()) ){
			flexiDate.click();
		}
	}


	private void updateDates() {

		//TODO: move hard Coding
		Select depMonth = new Select(driver.findElement(By.id("Month1")));

		//TODO: move hard Coding
		depMonth.selectByIndex(2);

	}
}
