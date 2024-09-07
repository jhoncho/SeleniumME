package Reportes;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reportes {


    private static ExtentReports extent;
    private static ExtentTest test;

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String[] args) {


        //CONFIGURAR EL REPORTE
         ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extend-report.html");
         extent = new ExtentReports(); //CREAMOS una instancia de extentReports
         extent.attachReporter(sparkReporter);// adjuntar el reporte


        // Configurar el WebDriver para usar Chrome
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");// Establecer la propiedad del sistema con la ruta del driver de Chrome
        driver = new ChromeDriver(); // Crear una instancia de ChromeDriver
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Crear una instancia de WebDriverWait con un tiempo de espera de 10 segundos

        try {
            // Navegar a la página de Sauce Demo
            driver.get("https://www.saucedemo.com/"); // Abrir la página de Sauce Demo en el navegador

            //LEER DATOS DESDE UN ARCHIVO CSV
            String csvFilePath = "Files/Usuario - Hoja 1.csv"; // RUTA AL ARCHIVO CSV
            try(CSVReader csvReader = new CSVReader(new FileReader( new File(csvFilePath)))) //CREAR UN CSVREADER PARA LEER EL ARCHIVO
            {
                String[] headers = csvReader.readNext(); //leer los encabezados de las columnas
                String[] line;
                while ((line = csvReader.readNext()) != null) //leer cada linea del archivo csv
                {
                    String username = line [0]; //leer el valor de la primera columna, nombre de usuario
                    String password = line [1]; //leer el valor de la segunda columna, contrasena o password

                    test = extent.createTest("Login por usuario: " + username);
                    performLogin(username, password); // llamos al metodo perfoormLogin

                    //
                }
            }catch (IOException | CsvValidationException e ) //capturar exepcion
            {
                e.printStackTrace();//imprimimos el tracke por algun error
            }

        } finally {
            // Cerrar el WebDriver
            driver.quit(); // Cerrar el navegador y finalizar la sesión del WebDriver
            extent.flush();//guardar el reporte
        }
    }

    private static void performLogin( String username, String password) {
        // Navegar a la página de Sauce Demo
        driver.get("https://www.saucedemo.com/"); // Abrir la página de Sauce Demo en el navegador

        // Iniciar sesión con las credenciales proporcionadas
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))); // Esperar a que el campo de nombre de usuario sea visible
        WebElement passwordField = driver.findElement(By.id("password")); // Encontrar el campo de contraseña
        WebElement loginButton = driver.findElement(By.id("login-button")); // Encontrar el botón de inicio de sesión

        usernameField.clear(); // Limpiar el campo de nombre de usuario
        usernameField.sendKeys(username); // Ingresar el nombre de usuario
        passwordField.clear(); // Limpiar el campo de contraseña
        passwordField.sendKeys(password); // Ingresar la contraseña
        loginButton.click(); // Hacer clic en el botón de inicio de sesión

        // Validar el inicio de sesión
        try {
            WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(.,'Products')]"))); // Esperar a que el título del producto sea visible
            test.pass("Login  Exitoso");
            System.out.println("Login successful for user " + username + ": " + productTitle.isDisplayed()); // Imprimir el resultado del inicio de sesión
        } catch (Exception e) {
            // Capturar el mensaje de usuario bloqueado y tomar una captura de pantalla
            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")); // Encontrar el mensaje de error
            if (errorMessage.getText().contains("Epic sadface: Sorry, this user has been locked out.")) { // Verificar si el mensaje de error contiene el texto de bloqueo
                System.out.println("User is locked out: " + username); // Imprimir que el usuario está bloqueado
                test.fail("Login Fallido" + username);
                takeScreenshot(driver, username); // Llamar al método takeScreenshot para capturar una captura de pantalla
            } else {
                System.out.println("Login failed for user " + username + ": " + e.getMessage()); // Imprimir el mensaje de error si el inicio de sesión falla por otra razón
                test.fail("Login fallido" + e.getMessage());
            }
        }
    }

    private static void takeScreenshot(WebDriver driver, String username) {
        try {
            // Tomar la captura de pantalla
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // Tomar la captura de pantalla como un archivo
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")); // Obtener la fecha y hora actual en el formato deseado
            String screenshotName = "locked_out_" + username + "_" + timestamp + ".png"; // Crear un nombre único para la captura de pantalla
            Path destination = Paths.get("screenshots", screenshotName); // Crear una ruta para guardar la captura de pantalla
            Files.createDirectories(destination.getParent()); // Crear el directorio si no existe
            Files.copy(screenshotFile.toPath(), destination); // Copiar la captura de pantalla al directorio de destino
            System.out.println("Screenshot saved: " + destination.toAbsolutePath()); // Imprimir la ruta de la captura de pantalla guardada
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage()); // Imprimir el mensaje de error si la captura de pantalla falla
        }
    }
}