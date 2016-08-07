package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;

public class ProductDetailPage extends BasePage {

    public ProductDetailPage(final EasyTest easy) {
        super(easy);
    }

    public ProductDetailPage changeOption(final int index) {
        return page.executeIn("#addToCartForm", container -> {
            container.selectIndex(".options-dropdown", index);
            return this;
         });
    }

    public ProductDetailPage changeQuantity(final String value) {
        return page.executeIn("#addToCartForm", container -> {
            container.selectText(".add-quantity", value);
            return this;
        });
    }
    
    public CartPage addToCart() {
        return page.executeIn("#addToCartForm", container -> {
            container.click("#addCartMain_addCartButton");
            return new CartPage(easy);
         });
    }
}
