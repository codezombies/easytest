package automation.example.advanced.pages;


import com.codingzombies.easytest.EasyTest;

import automation.example.advanced.Address;
import automation.example.advanced.actiontemplates.PopulateBillingAddress;

public class CheckoutPage extends BasePage {

    public CheckoutPage(final EasyTest easy) {
        super(easy);
    }

    public CheckoutPage setBillingAddress(final Address address) {
        page.executeIn("#billingForm", form -> {
            form.executeDataTemplate(PopulateBillingAddress.class, address);
        });
        return this;
    }
}
