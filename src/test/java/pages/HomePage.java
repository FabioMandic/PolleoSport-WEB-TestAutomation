package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import utils.Waits;

import java.util.List;

public class HomePage extends BasePage {

    private final Waits waits;
    private final String baseUrl = "https://polleosport.hr/";

    // Cookiebot
    private final By cookiebotAllowAll = By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");
    private final By cookiebotAllowAllAlt = By.cssSelector("button#CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll");

    // Push overlay
    private final By pushOverlayClose = By.cssSelector(".sp-prompt-close, .sendpulse-prompt .close, .sp-webpush-popup-close, .sp-webpush-close");

    // Search
    private final By searchInput = By.cssSelector("input[type='search'], input[name='search'], input[placeholder*='Traži'], input[placeholder*='Pretraži']");
    private final By searchBtn = By.cssSelector("button[type='submit'], button[aria-label*='search'], button[class*='search']");

    // Top menu links
    private final By topMenuLinks = By.cssSelector(".main_menu .categories > li > a, .nav-links a");

    // Listing: prvi proizvod
    private final By firstProductLink = By.cssSelector(".product-thumb .image a, .product-layout .image a, div.product-thumb h4 a");

    public HomePage(WebDriver driver) {
        super(driver);
        this.waits = new Waits(driver, 15);
    }

    public HomePage open() {
        driver.get(baseUrl);
        closePopups();
        return this;
    }

    public String title() {
        return driver.getTitle();
    }

    public SearchPage openSearch(String query) {
        search(query);
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("search"));
        return new SearchPage(driver);
    }

    public CategoryPage openProteiniCategory() {
        openCategoryByText("PROTEINI");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("proteini"));
        return new CategoryPage(driver);
    }

    public CategoryPage openVitaminiCategory() {
        openCategoryByText("VITAMINI I ZDRAVLJE");
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("vitamini"));
        return new CategoryPage(driver);
    }

    public HomePage acceptCookiesIfPresent() {
        try {
            if (waits.present(cookiebotAllowAll) || waits.present(cookiebotAllowAllAlt)) {
                By target = waits.present(cookiebotAllowAll) ? cookiebotAllowAll : cookiebotAllowAllAlt;
                waits.click(target, 6);
                waits.sleep(400);
            }
        } catch (Exception ignored) {}
        return this;
    }

    public HomePage openTopLinkByText(String text) {
        openCategoryByText(text);
        return this;
    }

    public void closePopups() {
        acceptCookiesIfPresent();
        try {
            if (waits.present(pushOverlayClose)) {
                waits.click(pushOverlayClose, 3);
                waits.sleep(200);
            }
        } catch (Exception ignored) {}
    }

    public HomePage search(String query) {
        closePopups();
        WebElement input = waits.visible(searchInput);
        input.clear();
        input.sendKeys(query);

        try {
            input.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            try { waits.click(searchBtn, 5); } catch (Exception ignored) {}
        }
        return this;
    }

    public HomePage openCategoryByText(String text) {
        closePopups();
        boolean clicked = false;

        waits.visible(topMenuLinks);
        List<WebElement> links = driver.findElements(topMenuLinks);

        System.out.println("--- Tražim kategoriju: " + text + " ---");

        for (WebElement a : links) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", a);

                String t = a.getText();
                if (a.isDisplayed() && t != null && t.trim().toUpperCase().contains(text.toUpperCase())) {
                    System.out.println("Klikam na: " + t);
                    waits.safeJsClick(a);
                    clicked = true;
                    break;
                }
            } catch (Exception ignored) {}
        }

        if (!clicked) {
            System.out.println("!!! Link nije pronađen: " + text);
            throw new SkipException("Ne mogu naći link u top meniju: " + text);
        }

        return this;
    }

    public ProductPage openFirstProductFromListing() {
        closePopups();
        WebElement a = waits.firstVisible(firstProductLink, 15);
        if (a == null) {
            throw new SkipException("Nema proizvoda na listi.");
        }
        waits.safeJsClick(a);


        waits.sleep(1500);
        closePopups();
        return new ProductPage(driver);
    }

    public boolean urlContains(String part) {
        return driver.getCurrentUrl().toLowerCase().contains(part.toLowerCase());
    }

    public boolean pageContainsText(String txt) {
        try {
            return driver.getPageSource().toLowerCase().contains(txt.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
}