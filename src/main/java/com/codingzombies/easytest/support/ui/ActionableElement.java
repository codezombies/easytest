package com.codingzombies.easytest.support.ui;

import static com.codingzombies.easytest.support.ui.Selectors.$;
import static com.codingzombies.easytest.support.ui.Selectors.$$;
import static com.codingzombies.easytest.support.ui.Selectors.$by;

import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.codingzombies.easytest.DeviceWebDriver;
import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.EasyTestOptions;
import com.codingzombies.easytest.loggers.Logger;
import com.codingzombies.easytest.loggers.SysoutLogger;
import com.codingzombies.easytest.util.EasyTestUtil;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class ActionableElement {

    protected static final Logger logger = new SysoutLogger();

    protected final EasyTest easy;
    protected final SearchContext context;
    protected final DeviceWebDriver driver;
    protected final WebDriverWait2 driverWait;
    protected EasyTestOptions options;
    private final int space;

    
    public ActionableElement(final EasyTest easy, final SearchContext context, final int space) {
        this.easy = easy;
        this.driver = easy.getDriver();
        this.driverWait = easy.getDriverWait();
        this.options = easy.getOptions();
        this.context = context;
        this.space = space;
    }

    public Select selectText(final String selector, final CharSequence value) {
        return select(selector, element -> {
            element.selectByVisibleText(value.toString());
            logger.logItems(space, "selecting value: " + value);
        });
    }

    public Select selectValue(final String selector, final CharSequence value) {
        return select(selector, element -> {
            element.selectByValue(value.toString());
            logger.logItems(space, "selecting value: " + value);
        });
    }

    public Select selectIndex(final String selector, final int index) {
        return select(selector, (element) -> {
            element.selectByIndex(index);
            logger.logItems(space, "selecting index: " + index);
        });
    }

    private Select select(final String selector, final Consumer<Select> consumer) {
        final Select element = new Select(get(selector));
        driverWait.until((java.util.function.Predicate<WebDriver>) input -> element.getOptions().size() > 1);
        consumer.accept(element);
        return element;
    }

    public WebElement typeText(final String selector, final CharSequence value) {
        final WebElement element = get(selector);
        element.clear();
        element.sendKeys(value);
        logger.logItems(space, "typing value: " + value);
        return element;
    }

    public WebElement clickButton(final String selector) {
        return click(selector);
    }

    public WebElement click(final String selector) {
        final WebElement element = get(selector);
        driverWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        logger.logItems(space, "clicking: " + selector);
        return element;
    }

    public <T> T executeIn(final String selector, final InContainerActions<T> action) {
        logger.logItems(space, "executing in: " + selector);
        final WebElement container = waitForVisibleElement(selector);
        return action.execute(new ActionableContainer(easy, container, space + 1));
    }

    public <T> T executeIn(final String selector, final int index, final InContainerActions<T> action) {
        logger.logItems(space, "executing in: " + selector + ", index: " + index);
        waitForVisibleElement(selector);
        return action.execute(new ActionableContainer(easy, get(selector, index), space + 1));
    }

    public void executeIn(final String selector, final VoidContainerActions action) {
        logger.logItems(space, "executing in: " + selector);
        final WebElement container = waitForVisibleElement(selector);
        action.execute(new ActionableContainer(easy, container, space + 1));
    }

    public void executeIn(final String selector, final int index, final VoidContainerActions action) {
        logger.logItems(space, "executing in: " + selector + ", index: " + index);
        waitForVisibleElement(selector);
        action.execute(new ActionableContainer(easy, get(selector, index), space + 1));
    }

    public WebElement waitForVisibleElement(final String selector) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated($by(selector)));
    }

    public void waitForInvisibleElement(final String selector) {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated($by(selector)));
    }
    
    public void hover(final String selector) {
        logger.logItems(space, "hover on: " + selector);
        driver.moveTo($(context, selector));
    }
    
    public <T extends ActionableTemplate> void executeTemplate(final Class<T> clazz) {
        executeTemplate(clazz.getName());
    }
    
    public void executeTemplate(final String key) {
        final ActionableTemplate template = options.getActionTemplates().get(key);
        if(template == null) {
            throw new IllegalArgumentException("no ActionableTemplate registered for key: " + key);
        }
        
        template.execute(this);
    }

    public WebElement getRaw(final String selector) {
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy($by(selector)));
        return $(context, selector);
    }
    
    public WebElement get(final String selector) {
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy($by(selector)));
        return $(context, selector, (e) -> e.isDisplayed() && e.isEnabled());
    }

    // use for multiple elements on a selector
    public WebElement get(final String selector, final int index) {
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy($by(selector)));
        final List<WebElement> list = getList(selector);
        if(index >= list.size()) {
            System.err.println("List content: ");
            System.err.println(EasyTestUtil.toString(list));
            throw new IllegalArgumentException("list has smaller size than the given index: " + index);
        }
        return list.get(index);
    }
    
    // use for multiple elements on a selector
    public List<WebElement> getList(final String selector) {
        driverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy($by(selector)));
        return $$(context, selector);
    }
}