package orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Locators
    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginButton = By.cssSelector("button[type='submit']");
    By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    By errorMessage = By.cssSelector(".oxd-alert-content-text");
    By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    By logoutLink = By.xpath("//a[text()='Logout']");

    // Actions

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public String getLoginResult(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
            return "Success";
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
                WebElement error = driver.findElement(errorMessage);
                return error.getText();
            } catch (Exception ex) {
                return "Unknown Result";
            }
        }
    }

    public void logoutIfLoggedIn() {
        try {
            if (driver.findElements(userDropdown).size() > 0) {
                wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
                wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
                System.out.println("Logged out successfully");
            }
        } catch (Exception e) {
            // Ignore if not logged in
        }
    }
}
