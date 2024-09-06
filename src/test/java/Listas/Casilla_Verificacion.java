package Listas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class Casilla_Verificacion {
    public static void main (String [] args)
    {
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--remote-allow-origins=*");

        //instanciamos el ChromeDriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Navegar a la página con las casillas de verificación
        driver.get("https://www.w3schools.com/html/html_forms.asp");

        //LOCALIZAMOS EL ELEMENTO
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));

        //SELECCIONAMOS UNA CASILLA VERIFICAMOS SI TIENE "BIKE" O "BOAT"
        for (WebElement checkbox : checkboxes)
        {
            //OBTENEMOS EL VALOR DEL ATRIBUTO "VALUE"
            String value = checkbox.getAttribute("value");

            //VERIFICAMOS EL VALOR
            if (value.equals("Bike")|| value.equals("Car"))
            {
                //VERIFICAMOS QUE LA CASILLA NO ESTE SELECCIONADO
                if (!checkbox.isSelected())
                {
                    //HACEMOS CLIC EN LA CASILLA DE VERIFICACION
                    checkbox.click();
                }
            }
        }
    }
}
