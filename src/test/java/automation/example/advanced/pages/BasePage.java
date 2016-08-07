package automation.example.advanced.pages;

import com.codingzombies.easytest.EasyTest;
import com.codingzombies.easytest.support.ui.ActionablePage;

public class BasePage {

    protected final EasyTest easy;
    protected final ActionablePage page;
    public BasePage(final EasyTest easy) {
        this.easy = easy;
        this.page = easy.newPage(thisPage -> thisPage);
    }
    
}
