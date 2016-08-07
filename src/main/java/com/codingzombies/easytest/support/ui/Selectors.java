package com.codingzombies.easytest.support.ui;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.codingzombies.easytest.DeviceWebDriver;
import com.codingzombies.easytest.exceptions.ElementNotFoundException;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class Selectors {

    private Selectors() {
    }

    public static By $by(final String selector) {
        if (byId(selector)) {
            return By.id(selector.substring(1));
        }
        else if (byName(selector)) {
            return By.name(selector.substring(1));
        }
        else if (byXpath(selector)) {
            return By.xpath(selector);
        }
        else if (byLinkText(selector)) {
            return By.linkText(selector);
        }
        return By.cssSelector(selector);
    }
    
    public static WebElement $(final SearchContext context, final String selector) {
        final WebElement element = $$(context, selector).get(0);
        moveToElementIfApplicable(context, element);
        return element;
    }

    public static WebElement $(final SearchContext context, final String selector, final Predicate<WebElement> predicate) {
        final WebElement element = $$(context, selector, predicate).get(0);
        moveToElementIfApplicable(context, element);
        return element;
    }

    public static List<WebElement> $$(final SearchContext context, final String selector) {
        return $$(context, selector, null);
    }

    public static List<WebElement> $$(final SearchContext context, final String selector, final Predicate<WebElement> predicate) {
        List<WebElement> elements;
        if (byId(selector)) {
            elements = context.findElements(By.id(selector.substring(1)));
        }
        else if (byName(selector)) {
            elements = context.findElements(By.name(selector.substring(1)));
        }
        else if (byXpath(selector)) {
            elements = context.findElements(By.xpath(selector));
        }
        else if (byLinkText(selector)) {
            elements = context.findElements(By.linkText(selector.substring(1)));
        }
        else {
            elements = context.findElements(By.cssSelector(selector));
        }
        
        if(elements == null || elements.isEmpty()) {
            throw new ElementNotFoundException(selector);
        }
        
        final List<WebElement> filteredElements = elements.stream()
                    .filter(Optional.ofNullable(predicate).orElse(Objects::nonNull))
                    .collect(Collectors.toList());
        
        if(filteredElements.isEmpty()) {
            throw new ElementNotFoundException(selector, elements);
        }
        
        return filteredElements;
    }

    private static boolean byId(final String selector) {
        return selector.startsWith("#") && !StringUtils.containsAny(selector.substring(1), " .>");
    }

    private static boolean byName(final String selector) {
        return selector.startsWith("=");
    }

    private static boolean byXpath(final String selector) {
        return selector.startsWith("/") || selector.startsWith("./");
    }

    private static boolean byLinkText(final String selector) {
        return selector.startsWith(":");
    }

    private static void moveToElementIfApplicable(final SearchContext context, final WebElement element) {
        if (element != null && element.isDisplayed() && context instanceof DeviceWebDriver) {
            try {
                new Actions((WebDriver) context).moveToElement(element).perform();
            }
            catch (final Exception e) {/* some drivers don't support actions */}
        }
    }

}
