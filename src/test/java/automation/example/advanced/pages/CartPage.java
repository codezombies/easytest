package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;

public class CartPage extends BasePage {

    public CartPage(final EasyTest easy) {
        super(easy);
    }
    
    public CheckoutLoginPage continueToCheckout() {
        page.clickButton(".cart-chkout-btn-right");
        return new CheckoutLoginPage(easy);
    }
}
