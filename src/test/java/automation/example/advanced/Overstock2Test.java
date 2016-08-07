package automation.example.advanced;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.EasyTestOptions;
import com.codingzombies.easytest.config.DriverType;

import automation.example.advanced.actiontemplates.CloseModal;
import automation.example.advanced.actiontemplates.PopulateBillingAddress;
import automation.example.advanced.pages.CartPage;
import automation.example.advanced.pages.CheckoutLoginPage;
import automation.example.advanced.pages.CheckoutPage;
import automation.example.advanced.pages.Homepage;
import automation.example.advanced.pages.ProductDetailPage;
import automation.example.advanced.pages.ProductListingPage;

public class Overstock2Test {

    @Test
    public void testOverstock() throws Exception {

        final EasyTestOptions options = new  EasyTestOptions();
        options.addActionTemplate(new CloseModal());
        options.addActionDataTemplate(new PopulateBillingAddress());
        
        try (EasyTest easy = new EasyTest(DriverType.CHROME, options)) {


            easy.start("http://www.overstock.com");
            final Homepage homepage = new Homepage(easy);
            
            ProductListingPage listingPage = homepage.selectMenu(".homemenu", "#submenu-bathroom", ":Showers");
            listingPage = listingPage.filter("#finishes-mod", "Bronze Finish");
            listingPage = listingPage.filter("#finishes-mod", "Nickel Finish");
            listingPage = listingPage.filter("#types-mod", "Frameless");
            
            final ProductDetailPage detailPage = listingPage.selectProduct(3);
            final CartPage cartPage = detailPage.changeOption(1).changeQuantity("Quantity: 2").addToCart();
            final CheckoutLoginPage checkoutLoginPage = cartPage.continueToCheckout();
            final CheckoutPage checkoutPage = checkoutLoginPage.continueAsGuest();
            
            final Address address = new Address();
            address.setEmailAddress("test@example.com");
            address.setFirstName("Dohdoh");
            address.setLastName("Skater");
            
            checkoutPage.setBillingAddress(address);
        }
    }
    
}
