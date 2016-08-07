package automation.example.advanced;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.EasyTestOptions;
import com.codingzombies.easytest.config.DriverType;

import automation.example.advanced.actiontemplates.CloseModal;
import automation.example.advanced.actiontemplates.PopulateBillingAddress;

public class OverstockTest {

    @Test
    public void testOverstock() throws Exception {

        final Address address = new Address();
        address.setEmailAddress("test@example.com");
        address.setFirstName("Dohdoh");
        address.setLastName("Skater");
        
        final EasyTestOptions options = new  EasyTestOptions();
        options.addActionTemplate("modal", new CloseModal());
        options.addActionDataTemplate("billingAddress", new PopulateBillingAddress());
        
        try (EasyTest easy = new EasyTest(DriverType.CHROME, options)) {


            easy.start("http://www.overstock.com");

            // homepage
            easy.newPage(page -> {
                page.executeTemplate("modal");
                page.hover(".homemenu");
                page.executeIn(".homemenu", container -> {
                    container.waitForVisibleElement(".sub");
                    
                    container.hover("li[data-submenu-id=submenu-bathroom]");
                    
                    container.waitForVisibleElement("#submenu-bathroom");
                    container.hover("#submenu-bathroom");
                    container.click(":Showers");
                });
            });

            // listing page filter reloads the page for each filter
            easy.newPage(page -> {
                page.executeIn("#finishes-mod", container -> {
                   container.click("li[data-value='Bronze Finish'] input[type=checkbox]"); 
                });
            });
            easy.newPage(page -> {
                page.executeIn("#types-mod", container -> {
                    container.click("li[data-value='Frameless'] input[type=checkbox]"); 
                });
            });
            easy.newPage(page -> {
                page.executeIn("#finishes-mod", container -> {
                    container.click("li[data-value='Nickel Finish'] input[type=checkbox]"); 
                });
            });
            
            // listing page after filter
            easy.newPage(page -> {
               // get 4th item (index 3)
                page.executeIn("li.product", 3, container -> {
                  container.click("a"); 
               });
            });
            
            // detail page
            easy.newPage(page -> {
                page.executeIn("#addToCartForm", container -> {
                   container.selectIndex(".options-dropdown", 1);
                   container.selectText(".add-quantity", "Quantity: 2");
                   container.click("#addCartMain_addCartButton");
                });
            });
            
            easy.newPage(page -> {
                page.clickButton(".cart-chkout-btn-right");
            }); 

            easy.newPage(page -> {
                page.clickButton("#guestCheckoutForm button");
            }); 
            
            easy.newPage(page -> {
               page.executeIn("#billingForm", form -> {
                   form.executeDataTemplate("billingAddress", address);
               });
            });
        }
    }

}
