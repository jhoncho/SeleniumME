package Ventanas;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Alerta_Simple {
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

        //HACEMOS CLIC EN EL BOTON PARA MOSTRAR ALERTA
        WebElement btnAlerta = driver.findElement(By.id("alertButton"));
        btnAlerta.click();

        //CAMBIAMOS EL ENFOQUE
        Alert alert = driver.switchTo().alert();

        //OBTENEMOS EL TEXTO DE ALERTA
        String AlertaTexto = alert.getText();
        System.out.println(" El texto de Alerta es:  " + AlertaTexto);

        //ACEPTAMOS LA ALERTA
        alert.accept();

    }
}

