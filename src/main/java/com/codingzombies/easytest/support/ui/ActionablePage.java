package com.codingzombies.easytest.support.ui;

import static com.codingzombies.easytest.support.ui.Selectors.$;
import static com.codingzombies.easytest.support.ui.Selectors.$by;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.codingzombies.easytest.EasyTest;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class ActionablePage extends ActionableElement {

    public ActionablePage(final EasyTest easy) {
        super(easy, easy.getDriver(), 0);
        logger.logHeading("on page: " + easy.getDriver().getTitle());
    }
    
    public void switchToFrame(final String selector) {
        driver.switchTo().frame($(driver, selector));
    }

    public void switchToMain() {
        driver.switchTo().defaultContent();
    }
    
    public <T extends ActionableDataTemplate<U>, U> void executeDataTemplate(final String containerSelector, final Class<T> clazz, final U data) {
        executeDataTemplate(containerSelector, clazz.getName(), data);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends ActionableDataTemplate<U>, U> void executeDataTemplate(final String containerSelector, final String key, final U data) {
        final ActionableDataTemplate<U> populator = (ActionableDataTemplate<U>) options.getActionDataTemplates().get(key);
        if(populator == null) {
            throw new IllegalArgumentException("no ActionableDataTemplate registered for key: " + key);
        }
        
        populator.execute(new ActionableContainer(easy, get(containerSelector), 1), data);
    }

    public WebElement waitForVisibleElement(final String selector) {
        driverWait.until(ExpectedConditions.presenceOfElementLocated($by(selector)));
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated($by(selector)));
    }

    public void waitForInvisibleElement(final String selector) {
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated($by(selector)));
    }

}