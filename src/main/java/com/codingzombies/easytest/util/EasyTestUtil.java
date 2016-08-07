package com.codingzombies.easytest.util;

import java.util.List;

import org.openqa.selenium.WebElement;

public class EasyTestUtil {

    public static final String toString(final List<WebElement> elements) {
        final StringBuilder sb = new StringBuilder();
        elements.forEach(element -> {
            sb.append(element.getTagName() + "=" + element.getText());
            sb.append(", displayed: " + element.isDisplayed());
            sb.append(", enabled: " + element.isEnabled());
            sb.append(", selected: " + element.isSelected());
            sb.append("\r\n"); //new line
        });
        return sb.toString();
    }
    
}
