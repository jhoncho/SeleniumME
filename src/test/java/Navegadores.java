import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Navegadores {
    public static void main (String [] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //INSTANCIAMOS CHROME

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://winstoncastillo.com/robot-selenium/");

        // PANTALLA COMPLETA
        driver.manage().window().maximize();

        //Thread.sleep(15000);

        //Espera implicita
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10000));

        driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/ul/li[2]/a")).click();

        // Espera Explicita
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        driver.findElement(By.name("email")).click();
        Thread.sleep(5000);
        driver.findElement(By.name("email")).sendKeys("Correo@Correo.com");


        //CAPTURAMOS ELEMENTOS
        //driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[7]/a")).click();




    }
}
