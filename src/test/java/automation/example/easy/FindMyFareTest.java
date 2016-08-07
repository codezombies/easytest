package automation.example.easy;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.config.DriverType;

public class FindMyFareTest {

    @Test
    public void testFindMyFare() throws Exception {
        try(EasyTest easy = new EasyTest(DriverType.CHROME)) {
            
            // start
            easy.start("https://www.findmyfare.com/");
            
            // homepage
            easy.newPage(page -> {
               
                page.typeText("#from_1", "Auckland, New Zealand (AKL)");
                page.typeText("#to_1", "Colombo, Sri Lanka (CMB)");
                
                // click from date slector and select 20th
                page.click("#date_1");
                page.executeIn("#ui-datepicker-div", container -> {
                   container.click(":20"); 
                });
                
                // click to date slector and select 25th
                page.click("#date_2");
                page.executeIn("#ui-datepicker-div", container -> {
                    container.click(":25"); 
                });
               
                // click travellers
                page.click("#PopS");
                
                // add people
                page.executeIn(".popover", container -> {
                    container.click("button[data-id='adults'].add_people");
                    container.click("button[data-id='childrens'].add_people");
                    container.click("button[data-id='infants'].add_people");
                });
                
                // click find flight button
                page.clickButton("#search_flight_submit");
                
            });
            
        }
    }
    
}
