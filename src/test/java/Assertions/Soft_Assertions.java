package Assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

public class Soft_Assertions {

    public static void main (String [] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //INSTANCIAMOS CHROME

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.wikipedia.org");

        // PANTALLA COMPLETA
        driver.manage().window().maximize();

        //CREAR UNA INSTANCIA DE SOFT ASSERT
        SoftAssert softAssert = new SoftAssert();

        //BUSCAMOS UN TERMINO, SELENIUM
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("Selenium");
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"search-form\"]/fieldset/button"));
        searchButton.click();

        //Validar que EL TITULO DE LA PAGINA CONTENGA SELENIUM
        String pageTitle = driver.getTitle();
        softAssert.assertTrue(pageTitle.contains("Selenium"), "El titulo de la pagina no contiene selenium");

        //validar que el encabezado contenga selenium
        WebElement header = driver.findElement(By.id("firstHeading"));
        String headertext = header.getText();
        softAssert.assertTrue(headertext.contains("Selenium"), "El encabezado no contiene selenium");


    }

}
