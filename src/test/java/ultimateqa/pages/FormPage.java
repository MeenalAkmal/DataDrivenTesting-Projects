package ultimateqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FormPage {
    WebDriver driver;

    private By nameField = By.id("et_pb_contact_name_0");
    private By messageField = By.id("et_pb_contact_message_0");
    private By submitButton = By.name("et_builder_submit_button");
    private By resultText = By.cssSelector(".et-pb-contact-message");

    public FormPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterName(String name) {
        WebElement nameInput = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(nameField));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterMessage(String message) {
        WebElement messageInput = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(messageField));
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    public void clickSubmit() {
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(submitButton));
        button.click();
    }

    public String getResultMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement resultElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(resultText));
            return resultElement.getText();
        } catch (Exception e) {
            return "No Result";
        }
    }
}


