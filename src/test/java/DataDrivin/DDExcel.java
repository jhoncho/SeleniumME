package DataDrivin;

import org.apache.poi.ss.usermodel.*; // Importar clases de Apache POI para manejar archivos Excel
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Importar clase para manejar archivos Excel (.xlsx)
import org.openqa.selenium.*; // Importar clases de Selenium WebDriver
import org.openqa.selenium.chrome.ChromeDriver; // Importar clase para el driver de Chrome
import org.openqa.selenium.support.ui.ExpectedConditions; // Importar clase para manejar condiciones de espera
import org.openqa.selenium.support.ui.WebDriverWait; // Importar clase para manejar esperas explícitas

import java.io.File; // Importar clase para manejar archivos
import java.io.FileInputStream; // Importar clase para leer archivos de entrada
import java.io.IOException; // Importar clase para manejar excepciones de entrada/salida
import java.time.Duration; // Importar clase para manejar duraciones de tiempo
import java.time.LocalDateTime; // Importar clase para manejar fecha y hora
import java.time.format.DateTimeFormatter; // Importar clase para formatear fechas y horas
import java.nio.file.Files; // Importar clase para manejar operaciones de archivos y directorios
import java.nio.file.Path; // Importar clase para manejar rutas de archivos
import java.nio.file.Paths; // Importar clase para construir rutas de archivos

public class DDExcel {
    public static void main(String[] args) {
        // Configurar el WebDriver para usar Chrome
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");// Establecer la propiedad del sistema con la ruta del driver de Chrome
        WebDriver driver = new ChromeDriver(); // Crear una instancia de ChromeDriver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Crear una instancia de WebDriverWait con un tiempo de espera de 10 segundos

        try {
            // Navegar a la página de Sauce Demo
            driver.get("https://www.saucedemo.com/"); // Abrir la página de Sauce Demo en el navegador

            // Leer datos desde el archivo Excel
            String excelFilePath = "Files/Usuarios.xlsx"; // Ruta al archivo Excel que contiene los datos de login
            try (FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath)); // Crear un FileInputStream para leer el archivo Excel
                 Workbook workbook = new XSSFWorkbook(fileInputStream)) { // Crear un Workbook para manejar el archivo Excel

                Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja del archivo Excel

                // Iterar sobre cada fila de la hoja
                for (Row row : sheet) {
                    // Omitir la primera fila que contiene los encabezados
                    if (row.getRowNum() == 0) {
                        continue; // Saltar la primera fila
                    }

                    String username = row.getCell(0).getStringCellValue(); // Leer el valor de la primera columna (nombre de usuario)
                    String password = row.getCell(1).getStringCellValue(); // Leer el valor de la segunda columna (contraseña)

                    // Ejecutar prueba para cada usuario
                    performLogin(driver, wait, username, password); // Llamar al método performLogin con las credenciales del usuario
                }
            } catch (IOException e) {
                e.printStackTrace(); // Imprimir el stack trace en caso de error al leer el archivo Excel
            }
        } finally {
            // Cerrar el WebDriver
            driver.quit(); // Cerrar el navegador y finalizar la sesión del WebDriver
        }
    }

    private static void performLogin(WebDriver driver, WebDriverWait wait, String username, String password) {
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
            System.out.println("Login successful for user " + username + ": " + productTitle.isDisplayed()); // Imprimir el resultado del inicio de sesión
        } catch (Exception e) {
            // Capturar el mensaje de usuario bloqueado y tomar una captura de pantalla
            WebElement errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")); // Encontrar el mensaje de error
            if (errorMessage.getText().contains("Epic sadface: Sorry, this user has been locked out.")) { // Verificar si el mensaje de error contiene el texto de bloqueo
                System.out.println("User is locked out: " + username); // Imprimir que el usuario está bloqueado
                takeScreenshot(driver, username); // Llamar al método takeScreenshot para capturar una captura de pantalla
            } else {
                System.out.println("Login failed for user " + username + ": " + e.getMessage()); // Imprimir el mensaje de error si el inicio de sesión falla por otra razón
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