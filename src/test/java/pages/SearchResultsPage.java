package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchResultsPage {

    private final WebDriver driver;

    // “Add to cart” gumbi – više opcija
    private final By addToCartButtons = By.cssSelector(
            "button[onclick*='cart.add'], " +
                    "button[title*='Košar'], button[title*='Cart'], " +
                    "button[id*='button-cart'], " +
                    "button[class*='add-to-cart'], a[class*='add-to-cart']"
    );

    // linkovi proizvoda (da možemo otvoriti detalj ako treba)
    private final By productLinks = By.cssSelector(
            "a[href*='product'], a[href*='product/product'], a.product-thumb, .product-thumb a"
    );

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOpened() {
        String url = driver.getCurrentUrl().toLowerCase();
        return url.contains("search") || url.contains("route=product/search");
    }

    public SearchResultsPage addFirstAvailableToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 15);


        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("body")));

        // pokušaj direktno kliknuti add-to-cart na listingu
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (WebElement b : buttons) {
            try {
                if (b.isDisplayed() && b.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", b);
                    wait.until(ExpectedConditions.elementToBeClickable(b)).click();
                    return this;
                }
            } catch (Exception ignored) {}
        }

        // ako nema gumba na listingu – otvori prvi proizvod pa ćemo ga dodati u CartPage logici
        List<WebElement> links = driver.findElements(productLinks);
        for (WebElement a : links) {
            try {
                if (a.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", a);
                    a.click();
                    return this;
                }
            } catch (Exception ignored) {}
        }

        throw new NoSuchElementException("Nisam našao ni add-to-cart ni product link na search rezultatima.");
    }
}
