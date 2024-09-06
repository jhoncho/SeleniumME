package Practica;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PracitcaSelenium {
    public static void main (String [] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //INSTANCIAMOS CHROME

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Definir una espera explícita
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try{

            // Navegar a la página con las casillas de verificación
            driver.get("https://opencart.abstracta.us/");

            //MAXIMIZAR PANTALLA
            driver.manage().window().maximize();

            // Esperamos a que la página cargue y encuentra el elemento específico
            WebElement itemElement = driver.findElement(By.xpath("//button[contains(@data-loading-text,'Loading...')]"));

            // Obtenemos el valor o texto del ítem
            String itemValue = itemElement.getText();
            System.out.println("Valor del ítem: " + itemValue);

            //Hacemos clic en el icono de corazon
            WebElement iconocorazon = driver.findElement(By.xpath("(//button[@data-original-title='Add to Wish List'])[1]"));
            // Esperar a que el botón sea clickeable
            wait.until(ExpectedConditions.elementToBeClickable(iconocorazon));

            iconocorazon.click();
            Thread.sleep(3000);

            //CAPTURAMOS EL TEXTO
            WebElement textocorazon = driver.findElement(By.xpath("(//div[contains(.,'You must login or create an account to save MacBook to your wish list! ×')])[2]"));
            String Item = textocorazon.getText();
            System.out.println(" El texto es :  "+ Item);

            //HACEMOS CLIC EN EL ADD TO CART
            WebElement AddToCart = driver.findElement(By.xpath("(//button[contains(.,'Add to Cart')])[1]"));
            AddToCart.click();

           for (int i=0;i < 4; i++) {

               AddToCart.click();
               Thread.sleep(3000);
           }

            // Esperamos a que la página cargue y encuentra el elemento específico
            WebElement itemElement2 = driver.findElement(By.xpath("//button[contains(@data-loading-text,'Loading...')]"));

            // Obtenemos el valor o texto del ítem
            String itemValue2 = itemElement2.getText();
            System.out.println("Valor del ítem: " + itemValue2);

        }finally
        {
            // CERRAMOS EL NAVEGADOR
            driver.quit();
        }
    }
}