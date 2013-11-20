package selenium.merci.prod.elal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TempProperties {

	public static void main(String[] args) throws IOException {
		
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
			
			
			for (String string : storedCountryList) {
				System.out.println(string);
			}
			
			fileInput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
	}
}
