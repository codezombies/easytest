package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;

public class CheckoutLoginPage extends BasePage {

    public CheckoutLoginPage(final EasyTest easy) {
        super(easy);
    }

    public CheckoutPage continueAsGuest() {
        page.clickButton("#guestCheckoutForm button");
        return new CheckoutPage(easy);
    }
    
}
