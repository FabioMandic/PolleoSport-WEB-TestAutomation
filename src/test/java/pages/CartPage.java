package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private final WebDriver driver;

    // cart ikonica i link na košaricu
    private final By cartLink = By.cssSelector("a[href*='checkout/cart'], a[href*='cart']");
    // badge / broj artikala (nekad je bubble, nekad tekst)
    private final By cartCount = By.cssSelector(".cart .count, .cart-count, .badge, #cart-total");

    // remove gumbovi u košarici
    private final By removeButtons = By.cssSelector("button[title*='Remove'], button[aria-label*='Remove'], .btn-danger");

    // empty cart poruka (razni tekstovi)
    private final By emptyCartHints = By.xpath(
            "//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'your shopping cart is empty') " +
                    "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'košarica je prazna') " +
                    "or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'prazna košarica')]"
    );

    // add to cart na product detail page-u
    private final By addToCartOnProduct = By.cssSelector("#button-cart, button[id*='button-cart'], button[onclick*='cart.add']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public CartPage openCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
        cart.click();
        return this;
    }

    public CartPage addToCartIfOnProductPage() {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addToCartOnProduct));
            btn.click();
        } catch (Exception ignored) {}
        return this;
    }

    public int readCartCountSafely() {
        try {
            String text = driver.findElement(cartCount).getText().replaceAll("[^0-9]", "");
            if (text.isEmpty()) return 0;
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage removeAllItemsIfPossible() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // pokušaj klikati remove dok ih ima
        for (int i = 0; i < 10; i++) {
            try {
                WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(removeButtons));
                btn.click();

                Thread.sleep(500);
            } catch (Exception e) {
                break;
            }
        }
        return this;
    }

    public boolean isEmpty() {
        try {
            return driver.findElements(emptyCartHints).size() > 0 || readCartCountSafely() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
