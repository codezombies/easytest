package automation.example.easy;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.config.DriverType;

public class WorkingInUSTest {

    @Test
    public void testWorkingInUS() throws Exception {

        try (EasyTest easy = new EasyTest(DriverType.PHANTOMJS)) {

            easy.start("https://beta.workinginus.com");

            //home page
            easy.newPage(page -> {

                // type text 
                page.typeText("#homepageSearch", "Bellevue");
                // from auto complete, select bellevue, washington
                page.executeIn(".ui-autocomplete", container -> {
                    container.click(":BELLEVUE, WASHINGTON");
                });
                
            });

            // city page
            easy.newPage(page -> {
                // from salary tab, select see all salaries
                page.executeIn("#city-tabs-salaries", container -> {
                   container.click("h3 a"); 
                });
            });
            
            // salaries page
            easy.newPage(page -> {
                // filter with company = microsoft
                page.click("a[data-filter='company'][data-value='MICROSOFT CORPORATION']");
            });

            // salaries page, filter reload the page
            easy.newPage(page -> {
                // filter with job title
                page.click("a[data-filter='jobtitle'][data-value='SOFTWARE DEVELOPMENT ENGINEER IN TEST']");
            });

            // salaries page, filter reload the page
            easy.newPage(page -> {
                // filter with year
                page.click("a[data-filter='year'][data-value='2014']");
            });
        }

    }

}
