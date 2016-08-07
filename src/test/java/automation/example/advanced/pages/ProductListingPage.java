package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;

public class ProductListingPage extends BasePage {

    public ProductListingPage(final EasyTest easy) {
        super(easy);
    }

    public ProductListingPage filter(final String mainFilterSelector, final String value) {
        return page.executeIn(mainFilterSelector, container -> {
            container.click("li[data-value='" + value + "']");
            return new ProductListingPage(easy);
        });
    }

    public ProductDetailPage selectProduct(final int index) {
        return page.executeIn("li.product", index, container -> {
            container.click("a");
            return new ProductDetailPage(easy);
        });
    }
}
