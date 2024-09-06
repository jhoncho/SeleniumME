package Ventanas;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Confirmacion {


        public static void main(String[] args) {
            ChromeOptions option = new ChromeOptions();
            option.addArguments("--remote-allow-origins=*");

            //instanciamos el ChromeDriver
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            WebDriver driver = new ChromeDriver();

            // Navegar a la página con las casillas de verificación
            driver.get("https://demoqa.com/alerts");

            driver.manage().window().maximize();

            // HACEMOS CLI EN EL BOTON PARA MOSTRAR LA CONFIRMACION
            WebElement confirmarBoton = driver.findElement(By.id("confirmButton"));

            //INTERACCION CON SCROLL
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("argument[0].scrollIntoView({block: 'nearest', inline: 'center'});", confirmarBoton);
            confirmarBoton.click();

            //CAMBIAMOS EL ENFOQUE
            Alert confirmacionAlert = driver.switchTo().alert();

            //OBTENER EL TEXTO DE CONFIRMACION
            String textoalerta = confirmacionAlert.getText();
            System.out.print("Texto de la confirmacion; " + textoalerta);

        }
    }
