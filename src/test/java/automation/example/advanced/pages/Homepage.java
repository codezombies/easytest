package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;

import automation.example.advanced.actiontemplates.CloseModal;

public class Homepage extends BasePage {

    public Homepage(final EasyTest easy) {
        super(easy);
        // close modal if applicable
        page.executeTemplate(CloseModal.class);
    }

    public ProductListingPage selectMenu(final String mainSelector, final String subSelector, final String link) {
        page.hover(mainSelector);
        return page.executeIn(mainSelector, container -> {
            container.waitForVisibleElement(".sub");
            
            container.hover("li[data-submenu-id="+ subSelector.substring(1) +"]");
            
            container.waitForVisibleElement(subSelector);
            container.hover(subSelector);
            container.click(link);
            return new ProductListingPage(easy);
        });
    }
}
