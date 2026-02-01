package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Waits;

public abstract class BasePage {

    protected WebDriver driver;
    protected Waits waits;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver, 15);
    }

    protected void jsScrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
}
