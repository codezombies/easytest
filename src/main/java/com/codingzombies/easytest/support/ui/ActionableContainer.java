package com.codingzombies.easytest.support.ui;

import org.openqa.selenium.WebElement;

import com.codingzombies.easytest.EasyTest;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public class ActionableContainer extends ActionableElement {

    public ActionableContainer(final EasyTest easy, final WebElement webElement, final int space) {
       super(easy, webElement, space); 
    }

    public <T extends ActionableDataTemplate<U>, U> void executeDataTemplate(final Class<T> clazz, final U data) {
        executeDataTemplate(clazz.getName(), data);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends ActionableDataTemplate<U>, U> void executeDataTemplate(final String key, final U data) {
        final ActionableDataTemplate<U> populator = (ActionableDataTemplate<U>) options.getActionDataTemplates().get(key);
        if(populator == null) {
            throw new IllegalArgumentException("no ActionableDataTemplate registered for key: " + key);
        }
        
        populator.execute(this, data);
    }
}