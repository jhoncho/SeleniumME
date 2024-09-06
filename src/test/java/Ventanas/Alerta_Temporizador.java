package Ventanas;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Alerta_Temporizador {
    public static void main (String [] args)
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");

        //instanciamos el ChromeDriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Navegar a la página con las casillas de verificación
        driver.get("https://demoqa.com/alerts");

        driver.manage().window().maximize();

        //HACEMOS CLIC EN EL BOTON PARA MOSTRAR MENSAJE CON TIEMPO
        WebElement timerAlertButton = driver.findElement(By.id("timerAlertButton"));
        timerAlertButton.click();

        //ESPERAMOS HASTA QUE LA ALERTA SEA VISIBLE DESPUES DE 5 SEGUNDOS
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());

        //CAMBIAMO EL ENFOQUE
        Alert alert = driver.switchTo().alert();

        //OBTENER EL TEXTO
        String alertaTexto = alert.getText();
        System.out.println(" El texto de la alerta con temporizador es;  " + alertaTexto);

        //ACEPTAMOS LA ALERTA
        alert.accept();

    }
}
