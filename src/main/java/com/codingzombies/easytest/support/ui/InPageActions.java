package com.codingzombies.easytest.support.ui;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
@FunctionalInterface
public interface InPageActions<T> {
    T execute(ActionablePage page);
}