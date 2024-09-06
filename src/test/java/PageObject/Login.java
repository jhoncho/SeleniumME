package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends Base{

    //LOCALIZADORES
     By username = By.xpath("//input[@name='user-name']");
     By password = By.xpath("//input[@name='password']");
     By btnLogin = By.xpath("//input[@name='login-button']");


    public Login (WebDriver driver)
    {
        super(driver);
    }

    public void loginUser ()
    {
        type("standard_user", username);
        type("secret_sauce",password);
        click(btnLogin);
    }


}
