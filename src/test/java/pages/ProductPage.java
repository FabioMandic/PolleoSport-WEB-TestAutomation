package pages;

import org.openqa.selenium.*;
import org.testng.SkipException;
import utils.Waits;

import java.util.List;

public class ProductPage extends BasePage {

    private final Waits waits;

    private final By addToCartCandidates = By.cssSelector(
            "button#button-cart, " +
                    "button[onclick*='cart.add'], " +
                    "button.btn-primary, " +
                    ".btn-cart, .add-to-cart, .button-cart"
    );

    private final By outOfStockHints = By.xpath(
            "//*[contains(translate(.,'NEMA ZALIHE','nema zalihe'),'nema zalihe') " +
                    "or contains(translate(.,'RASPRODANO','rasprodano'),'rasprodano') " +
                    "or contains(translate(.,'OUT OF STOCK','out of stock'),'out of stock')]"
    );

    private final By cartCount = By.cssSelector(".cart-count, #cart-total, a[title*='cart'] span");

    public ProductPage(WebDriver driver) {
        super(driver);
        this.waits = new Waits(driver, 15);
    }

    public boolean is404() {
        String u = driver.getCurrentUrl().toLowerCase();
        String t = driver.getTitle().toLowerCase();
        return u.contains("404") ||
                t.contains("404") ||
                t.contains("stranica nije pronađena") ||
                t.contains("page not found");
    }

    public boolean isOutOfStock() {
        return !driver.findElements(outOfStockHints).isEmpty();
    }


    public boolean isOpened() {
        return !is404();
    }


    public ProductPage addToCart() {
        return addToCartOrSkipIfNotPossible();
    }

    public ProductPage addToCartOrSkipIfNotPossible() {

        if (is404()) {
            throw new SkipException("404 product page: " + driver.getCurrentUrl());
        }

        if (isOutOfStock()) {
            throw new SkipException("Out of stock product: " + driver.getCurrentUrl());
        }

        List<WebElement> buttons = driver.findElements(addToCartCandidates);

        WebElement first = null;
        for (WebElement b : buttons) {
            try {
                if (b.isDisplayed() && b.isEnabled()) {
                    first = b;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (first == null) {
            throw new SkipException("No add-to-cart button on product page");
        }

        waits.safeJsClick(first);
        waits.sleep(800);
        return this;
    }

    public int cartBadgeCount() {
        try {
            String txt = driver.findElement(cartCount).getText().replaceAll("[^0-9]", "");
            return txt.isEmpty() ? 0 : Integer.parseInt(txt);
        } catch (Exception e) {
            return 0;
        }
    }
}
