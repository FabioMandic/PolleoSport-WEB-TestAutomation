package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasResults() {
        By anyProduct = By.cssSelector("div.product-thumb, .product-layout, .product-grid");
        return waits.present(anyProduct);
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
