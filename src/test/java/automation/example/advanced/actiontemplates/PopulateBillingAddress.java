package automation.example.advanced.actiontemplates;

import com.codingzombies.easytest.support.ui.ActionableDataTemplate;
import com.codingzombies.easytest.support.ui.ActionableElement;

import automation.example.advanced.Address;

public class PopulateBillingAddress implements ActionableDataTemplate<Address> {

    @Override
    public void execute(final ActionableElement element, final Address data) {
        element.typeText("#emailAddress", data.getEmailAddress());
        element.typeText("#BillingFirstName", data.getFirstName());
        element.typeText("#BillingLastName", data.getLastName());
    }

}
