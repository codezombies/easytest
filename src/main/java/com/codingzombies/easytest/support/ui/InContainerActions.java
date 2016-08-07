package com.codingzombies.easytest.support.ui;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
@FunctionalInterface
public interface InContainerActions<T> {
    T execute(ActionableContainer element);
}