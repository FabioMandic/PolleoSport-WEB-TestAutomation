package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage {

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean hasProducts() {
        By anyProduct = By.cssSelector("div.product-thumb, .product-layout, .product-grid");
        return waits.present(anyProduct);
    }
}
