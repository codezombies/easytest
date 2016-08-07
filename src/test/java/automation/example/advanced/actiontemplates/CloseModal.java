package automation.example.advanced.actiontemplates;

import com.codingzombies.easytest.support.ui.ActionableElement;
import com.codingzombies.easytest.support.ui.ActionableTemplate;

public class CloseModal implements ActionableTemplate {
    @Override
    public void execute(final ActionableElement element) {
        try {
            element.click("#cboxClose");
        }
        catch (final Exception e) {};
    };
}