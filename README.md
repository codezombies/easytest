# easytest
framework to help on setting up your next web automation testing using selenium

## requirements
* jdk 1.8
* maven
 
## features
* creating automation tests easily (see examples)
* supports chrome browser out of the box without any setup
* supports phantomjs/ghost driver out of the box
* automatic screenshots during errors
* pretty logging 
* support for action templates (see examples)
* support for responsive website with predefined browser sizes
* natural element selectors

## element selectors
* selector prefixed with '#' -> By.id
* selector prefixed with '=' -> By.name
* selector prefixed with '/' or './' -> By.xpath
* selector prefixed with ':' -> By.linkText
* default selector will use By.cssSelector

## example 1 (basic)
```java
    @Test
    public void testWorkingInUS() throws Exception {

        try (EasyTest easy = new EasyTest(DriverType.PHANTOMJS)) {

            easy.start("https://beta.workinginus.com");

            //home page
            easy.newPage(page -> {

                // type text 
                page.typeText("#homepageSearch", "Bellevue");
                // from auto complete, select bellevue, washington
                page.executeIn(".ui-autocomplete", container -> {
                    container.click(":BELLEVUE, WASHINGTON");
                });
                
            });

            // city page
            easy.newPage(page -> {
                // from salary tab, select see all salaries
                page.executeIn("#city-tabs-salaries", container -> {
                   container.click("h3 a"); 
                });
            });
            
            // salaries page
            easy.newPage(page -> {
                // filter with company = microsoft
                page.click("a[data-filter='company'][data-value='MICROSOFT CORPORATION']");
            });

            // salaries page, filter reload the page
            easy.newPage(page -> {
                // filter with job title
                page.click("a[data-filter='jobtitle'][data-value='SOFTWARE DEVELOPMENT ENGINEER IN TEST']");
            });

            // salaries page, filter reload the page
            easy.newPage(page -> {
                // filter with year
                page.click("a[data-filter='year'][data-value='2014']");
            });
        }

    }
```

## example 2 (using action templates)

``` java
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
        options.addActionDataTemplate("gmc", new FitmentPopulator());
        
        try(EasyTest easy = new EasyTest(DriverType.CHROME, options)) {
            easy.start("http://www.tirebuyer.com");
            
            // homepage
            easy.newPage((page) -> {
                page.executeIn("#formFitment", (form) -> {
                    form.executeDataTemplate("gmc", fitment); //use template to populate form
                    form.clickButton(".searchTire");
                });
            });
            
        }
        
    }
    
}
```

## logs
the framework prints readable logs when used correctly. it also includes nested spaces when selecting elements inside a page section or another element. see example below
```
- on page: Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more
  - executing in: =site-search
    - typing value: java programming
    - clicking: input[type=submit]
- on page: Amazon.com: java programming
  - executing in: .s-result-item, index: 4
    - clicking: .a-link-normal
- on page: Head First Java, 2nd Edition: Sierra, Bates: 9780596009205: Amazon.com: Books
  - clicking: #add-to-cart-button
- on page: Amazon.com Shopping Cart
  - clicking: #hlb-view-cart-announce
- on page: Amazon.com Shopping Cart
  - clicking: =proceedToCheckout
- on page: Amazon Sign In
  - clicking: #createAccountSubmit
- on page: Amazon Registration
  - typing value: Dohdoh Skater
  - typing value: test@example.com
  - typing value: 123456789
  - typing value: 123456789
```

Let me know what you guys think. Thanks!
