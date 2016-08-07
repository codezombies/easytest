package com.codingzombies.easytest.support.ui;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
@FunctionalInterface
public interface ActionableTemplate {

    void execute(ActionableElement element);
    
}
