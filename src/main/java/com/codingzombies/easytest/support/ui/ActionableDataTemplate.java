package com.codingzombies.easytest.support.ui;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
@FunctionalInterface
public interface ActionableDataTemplate<T> {

    public void execute(ActionableElement element, T data);
    
}
