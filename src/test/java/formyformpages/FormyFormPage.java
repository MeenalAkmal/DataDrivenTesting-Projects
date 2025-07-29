package formyformpages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class FormyFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public FormyFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private final By firstNameField = By.id("first-name");
    private final By lastNameField  = By.id("last-name");
    private final By jobTitleField  = By.id("job-title");

    private final By highSchoolRadio = By.id("radio-button-1");
    private final By collegeRadio    = By.id("radio-button-2");
    private final By gradSchoolRadio = By.id("radio-button-3");

    private final By maleCheckbox         = By.id("checkbox-1");
    private final By femaleCheckbox       = By.id("checkbox-2");
    private final By preferNotSayCheckbox = By.id("checkbox-3");

    private final By yearsDropdown = By.id("select-menu");
    private final By dateField     = By.id("datepicker");
    private final By submitBtn     = By.cssSelector(".btn.btn-lg.btn-primary");

    private final By thanksHeader      = By.tagName("h1");                     // "Thanks for submitting your form"
    private final By successMessageDiv = By.cssSelector(".alert.alert-success"); // "The form was successfully submitted!"

    // -------- Actions (all with explicit waits) --------

    public void enterFirstName(String fname) {
        if (isBlank(fname)) return;
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        el.clear();
        el.sendKeys(fname);
    }

    public void enterLastName(String lname) {
        if (isBlank(lname)) return;
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        el.clear();
        el.sendKeys(lname);
    }

    public void enterJobTitle(String title) {
        if (isBlank(title)) return;
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(jobTitleField));
        el.clear();
        el.sendKeys(title);
    }

    public void selectEducation(String education) {
        if (isBlank(education)) return;
        switch (education.trim().toLowerCase()) {
            case "high school" -> wait.until(ExpectedConditions.elementToBeClickable(highSchoolRadio)).click();
            case "college"     -> wait.until(ExpectedConditions.elementToBeClickable(collegeRadio)).click();
            case "grad school" -> wait.until(ExpectedConditions.elementToBeClickable(gradSchoolRadio)).click();
            default -> System.out.println("WARN: Unknown education: " + education);
        }
    }

    public void selectGender(String gender) {
        if (isBlank(gender)) return;
        switch (gender.trim().toLowerCase()) {
            case "male"              -> wait.until(ExpectedConditions.elementToBeClickable(maleCheckbox)).click();
            case "female"            -> wait.until(ExpectedConditions.elementToBeClickable(femaleCheckbox)).click();
            case "prefer not to say" -> wait.until(ExpectedConditions.elementToBeClickable(preferNotSayCheckbox)).click();
            default -> System.out.println("WARN: Unknown gender: " + gender);
        }
    }

    public void selectYears(String years) {
        if (isBlank(years)) return;
        try {
            WebElement dd = wait.until(ExpectedConditions.visibilityOfElementLocated(yearsDropdown));
            Select select = new Select(dd);
            select.selectByVisibleText(years);
        } catch (NoSuchElementException e) {
            System.out.println("WARN: '" + years + "' is not a valid option in dropdown.");
        }
    }

    public void enterDate(String date) {
        if (isBlank(date)) return;
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(dateField));
        el.clear();
        el.sendKeys(date);
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        // Wait for thank-you page
        wait.until(ExpectedConditions.urlContains("/thanks"));
    }

    public String getSuccessMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageDiv)).getText();
        } catch (TimeoutException e) {
            return "No Success Message";
        }
    }

    public String getThanksHeader() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(thanksHeader)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}

