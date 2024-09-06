package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Login_Test {

    private WebDriver driver;
    Login Login;

    @BeforeClass
    public void setUp ()
        {
            Login = new Login(driver);
            driver = Login.chromeDriverConnection();
            Login.visit("https://www.saucedemo.com/");
        }
        @AfterClass
    public void tearDown ()
        {
            driver.quit();
        }
        @Test
    public void test()
        {
            Login.loginUser();

        }
}
