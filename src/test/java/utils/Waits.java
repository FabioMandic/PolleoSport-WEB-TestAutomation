package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Waits {

    private final WebDriver driver;
    private final long defaultTimeout;
    private final WebDriverWait wait;

    public Waits(WebDriver driver, long timeoutSeconds) {
        this.driver = driver;
        this.defaultTimeout = timeoutSeconds;
        this.wait = new WebDriverWait(driver, timeoutSeconds);
    }

    public WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }


    public void click(By by) {
        clickable(by).click();
    }


    public void click(By by, int timeoutSeconds) {
        new WebDriverWait(driver, timeoutSeconds)
                .until(ExpectedConditions.elementToBeClickable(by))
                .click();
    }

    public boolean present(By by) {
        return !driver.findElements(by).isEmpty();
    }

    public void safeJsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void sleep(long millis) {
        try { Thread.sleep(millis); } catch (InterruptedException ignored) {}
    }


    public WebElement firstVisible(By by, int timeoutSeconds) {
        WebDriverWait w = new WebDriverWait(driver, timeoutSeconds);
        return w.until(d -> {
            List<WebElement> els = d.findElements(by);
            for (WebElement e : els) {
                try {
                    if (e.isDisplayed()) return e;
                } catch (Exception ignored) {}
            }
            return null;
        });
    }


    public WebElement firstVisible(By by) {
        return firstVisible(by, (int) defaultTimeout);
    }
}