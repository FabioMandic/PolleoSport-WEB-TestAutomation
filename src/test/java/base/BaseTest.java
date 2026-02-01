package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

public class BaseTest {

    protected static WebDriver driver;


    @BeforeClass
    public void setUpClass() {
        driver = DriverFactory.createDriver();
        driver.manage().window().maximize();
    }


    @BeforeMethod
    public void returnToHome() {
        driver.get("https://polleosport.hr/");
    }


    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
}