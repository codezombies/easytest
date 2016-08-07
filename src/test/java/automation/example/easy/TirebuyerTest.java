package automation.example.easy;

import org.junit.Test;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.EasyTestOptions;
import com.codingzombies.easytest.config.DriverType;
import com.codingzombies.easytest.support.ui.ActionableDataTemplate;
import com.codingzombies.easytest.support.ui.ActionableElement;
import com.codingzombies.easytest.support.ui.ActionableTemplate;

public class TirebuyerTest {

    private static class Fitment {
        String year;
        String make;
        String model;
        String trim;
        String zip;
        public Fitment(final String year, final String make, final String model, final String trim, final String zip) {
            this.year = year;
            this.make = make;
            this.model = model;
            this.trim = trim;
            this.zip = zip;
        }
    }
    
    private static class FitmentTemplate implements ActionableTemplate {
        
        Fitment fitment;
        public FitmentTemplate(final Fitment fitment) {
            this.fitment = fitment;
        }

        @Override
        public void execute(final ActionableElement element) {
            element.selectText("=year", fitment.year);
            element.selectText("=make", fitment.make);
            element.selectText("=model", fitment.model);
            element.selectText("=trim", fitment.trim);
            element.typeText("=zipCode", fitment.zip);
        }
    }
    
    private static class FitmentPopulator implements ActionableDataTemplate<Fitment> {
        @Override
        public void execute(final ActionableElement element, final Fitment fitment) {
            element.selectText("=year", fitment.year);
            element.selectText("=make", fitment.make);
            element.selectText("=model", fitment.model);
            element.selectText("=trim", fitment.trim);
            element.typeText("=zipCode", fitment.zip);
        }
    }
    
    @Test
    public void testTireBuyer() throws Exception {
        
        final Fitment fitment = new Fitment("2010", "GMC", "Acadia", "SL", "98004");
        
        final EasyTestOptions options = new EasyTestOptions();
        options.addActionTemplate("gmc", new FitmentTemplate(fitment));
        options.addActionDataTemplate("gmc", new FitmentPopulator());
        
        try(EasyTest easy = new EasyTest(DriverType.CHROME, options)) {
            
            // register templates
            
            easy.start("http://www.tirebuyer.com");
            
            // homepage
            easy.newPage((page) -> {
                page.executeIn("#formFitment", (form) -> {
                    // we can populate the form in 3 different ways
                    // 1.) populate manually
//                    form.selectText("=year", "2010");
//                    form.selectText("=make", "GMC");
//                    form.selectText("=model", "Acadia");
//                    form.selectText("=trim", "SL");
//                    form.typeText("=zipCode", "98004");
                    // 2.) using action template 
//                    form.executeTemplate("gmc");
                    // 3.) using action data template
                    form.executeDataTemplate("gmc", fitment);
                    form.clickButton(".searchTire");
                });
            });
            
            // product listing page
            easy.newPage(page -> {
                page.executeIn(".list-view-item", 2, (container) -> {
                    container.selectText("=qty", "2");
                    container.clickButton(".addToCart a");
                });
            });
            
            // cart page
            easy.newPage(page -> {
                page.executeIn(".cart-item", (container) -> {
                    // click radio button
                    container.click(".at_cart_installer_radioButton");
                });

                // wait for modal
                page.waitForVisibleElement("#fbContent");
                
                // select second element
                page.executeIn(".installerContainer", 1, (container) -> {
                    container.click(".at_shipToInstaller_radioButton");
                });
                
                page.click(".at_shipToInstaller_save_and_continue");
                // wait for modal to close
                page.waitForInvisibleElement("#fbOverlay");
                // click button to continue to checkout
                page.clickButton("#gotoCheckoutBtn");
            });
            
            
            //click save and continue
            
            // checkout page
            easy.newPage(page -> {
                page.clickButton("a[href='/paypal/checkout']");
            });
            
            // paypal page
            easy.newPage(page -> {
                page.waitForVisibleElement("=injectedUl");
                // switch to iframe
                page.switchToFrame("=injectedUl");
                page.typeText("#email", "test@example.com");
                page.typeText("#password", "123456789");
                page.clickButton("#btnLogin");
                // assert element visible
                page.switchToMain();
                // cancel and return
                page.click("#cancelLink");
            });
            
            // checkout page again
            easy.newPage(page -> {
                page.typeText("=card_number", "4111111111111111");
                page.selectText("#CardMonth", "01");
                page.selectText("#CardYear", "2020");
                page.typeText("#SecurityCode", "123");
                page.typeText("#Email", "test@example.com");
                page.typeText("#FirstName", "Dohdoh");
                page.typeText("#LastName", "Skater");
                page.typeText("#Address", "123 Main St");
                page.typeText("#City", "Bellevue");
                page.typeText("#ZipCodeInput", "98004");
                page.typeText("#PhoneNumber", "5554445656");
                page.clickButton(".btn-checkout a");
            });
        }
        
    }
    
}
