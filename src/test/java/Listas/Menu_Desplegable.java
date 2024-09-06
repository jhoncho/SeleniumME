package Listas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Menu_Desplegable {
    public static void main (String [] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //INSTANCIAMOS CHROME

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.wikipedia.org");

        // PANTALLA COMPLETA
        driver.manage().window().maximize();

        //LOCALIZAR EL MENU DESPLEGABLE
        WebElement MenuDesplegable = driver.findElement(By.id("searchLanguage"));

        //CREAR UN OBJETO SELECT PARA INTERACTUAR
        Select seleccionarLenguaje = new Select(MenuDesplegable);

        //SELECCIONAR UN IDIOMA
        seleccionarLenguaje.selectByValue("tr");

        //OPCIONAL
        // VERIFICAMOS QUE EL LENGUAJE SELECCIONADO ES CORRECTO
        String SeleccionOp = seleccionarLenguaje.getFirstSelectedOption().getText();
        System.out.println("Idioma seleccionado" + SeleccionOp );

    }
}

