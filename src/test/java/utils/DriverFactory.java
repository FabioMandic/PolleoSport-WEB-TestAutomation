package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "firefox":
                return createFirefox();
            case "chrome":
            default:
                return createChrome();
        }
    }

    private static WebDriver createChrome() {
        ChromeOptions options = new ChromeOptions();


        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Gašenje NOTIFICATION prompt
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2); // 1 allow, 2 block
        prefs.put("profile.default_content_setting_values.geolocation", 2);
        options.setExperimentalOption("prefs", prefs);

        if ("true".equalsIgnoreCase(System.getProperty("headless"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox() {
        FirefoxProfile profile = new FirefoxProfile();

        // Gašenje NOTIFICATION prompt
        profile.setPreference("dom.webnotifications.enabled", false);
        profile.setPreference("permissions.default.desktop-notification", 2);
        profile.setPreference("dom.push.enabled", false);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        if ("true".equalsIgnoreCase(System.getProperty("headless"))) {
            options.setHeadless(true);
        }

        return new FirefoxDriver(options);
    }
}
