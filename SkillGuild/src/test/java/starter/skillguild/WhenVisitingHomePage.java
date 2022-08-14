package starter.skillguild;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenVisitingHomePage {

	    @Managed(driver = "chrome", options = "headless")
	    WebDriver driver;

	    NavigateActions navigate;

	    @Test
	    void theHomePageShouldExist() {
	        navigate.toTheHomePage();
	    }
	    
	    @Test
	    void theBrandCenterShouldExist() {
	        navigate.toTheBrandCenter();
	    }
}