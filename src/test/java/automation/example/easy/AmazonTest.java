package automation.example.easy;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.config.DriverType;

public class AmazonTest {

    @Test
    public void testAmazon() throws Exception {
        
        try(EasyTest easy = new EasyTest(DriverType.CHROME)) {
            
            easy.start("http://www.amazon.com");
            
            // homepage
            easy.newPage(page -> {
                page.executeIn("=site-search", (element) -> {
                    element.typeText("#twotabsearchtextbox", "java programming");
                    element.clickButton("input[type=submit]");
                });
            });
            
            // product listing page
            easy.newPage(page -> {
                // get item with index 4, item #5
                page.executeIn(".s-result-item", 4, container -> {
                    // click image to go to detail page
                    container.click(".a-link-normal");
                });
            });
            
            //product detail page
            easy.newPage(page -> {
                // add item to cart
               page.clickButton("#add-to-cart-button"); 
            });
            
            //cart interstitial
            easy.newPage(page -> {
                // click go to cart button
               page.clickButton("#hlb-view-cart-announce"); 
            });
            
            //cart page
            easy.newPage(page -> {
                // click proceed to checkout
               page.clickButton("=proceedToCheckout"); 
            });
            
            // login page
            easy.newPage(page -> {
                // click create account
               page.clickButton("#createAccountSubmit"); 
            });
            
            easy.newPage(page -> {
               page.typeText("#ap_customer_name", "Dohdoh Skater"); 
               page.typeText("#ap_email", "test@example.com"); 
               page.typeText("#ap_password", "123456789"); 
               page.typeText("#ap_password_check", "123456789"); 
            });
        }
        
    }
    
}
