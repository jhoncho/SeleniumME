package PageObject;

import org.checkerframework.checker.index.qual.PolyUpperBound;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Base {

    private WebDriver driver;
    public Base(WebDriver driver)
    {
        this.driver = driver;
    }

    //LLAMAMOS AL CHROME DRIVER
    public WebDriver chromeDriverConnection()
    {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        return  driver;
    }

    public WebElement findElement(By locator)
    {
        return driver.findElement(locator);
    }

    public List <WebElement> findElements(By locator)
    {
        return driver.findElements(locator);
    }

    public String getText(WebElement element)
    {
        return element.getText();
    }
    public String getText(By locator)
    {
        return driver.findElement(locator).getText();
    }
    //METODO PARA ENVIAR TEXTO
    public void type(String inputText, By locator)
    {
        driver.findElement(locator).sendKeys(inputText);
    }
    public void click (By locator)
    {
        driver.findElement(locator).click();
    }
    public Boolean isDisplayed (By locator)
    {
        try
        {
            return driver.findElement(locator).isDisplayed();
        }catch (org.openqa.selenium.NoSuchElementException e)
        {
            return false;
        }
    }

    public void visit (String url)
    {
        driver.get(url);
    }

}
