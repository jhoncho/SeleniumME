package Assertions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

public class Hard_Assertions {
    public static void main (String [] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //INSTANCIAMOS CHROME

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com.bo/");

        // PANTALLA COMPLETA
        driver.manage().window().maximize();

        //VERIFICAR QUE EL TITULO DE LA PAGINA SEA GOOGLE
        String expectedTitle = "Google";
        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle,expectedTitle,"El titulo de la pagina no es Google");

        //VERIFICAR QUE EL CAMPO DE BUSQUEDA ESTE PRESENTE

        WebElement  searchBox = driver.findElement(By.name("q"));
        Assert.assertTrue(searchBox.isDisplayed(),"El campo de busqueda no esta presente");

    }
}