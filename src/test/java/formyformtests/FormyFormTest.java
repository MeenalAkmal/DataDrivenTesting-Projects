package formyformtests;

import formyformpages.FormyFormPage;
import Common.utils.ExcelUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FormyFormTest {

    private WebDriver driver;
    private final String excelPath = "TestData/TestingFile.xlsx";
    private final String sheetName = "FormyForm";  

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); // rely on explicit waits
    }

    @Test
    public void dataDriven_Form_Submission() throws InterruptedException {
        int totalRows = ExcelUtils.getRowCount(excelPath, sheetName);

        for (int i = 1; i <= totalRows; i++) {
            driver.get("https://formy-project.herokuapp.com/form");
            FormyFormPage form = new FormyFormPage(driver);

            String firstName  = ExcelUtils.getCellData(excelPath, sheetName, i, 0);
            String lastName   = ExcelUtils.getCellData(excelPath, sheetName, i, 1);
            String jobTitle   = ExcelUtils.getCellData(excelPath, sheetName, i, 2);
            String education  = ExcelUtils.getCellData(excelPath, sheetName, i, 3);
            String gender     = ExcelUtils.getCellData(excelPath, sheetName, i, 4);
            String experience = ExcelUtils.getCellData(excelPath, sheetName, i, 5);
            String date       = ExcelUtils.getCellData(excelPath, sheetName, i, 6);
            String expected   = ExcelUtils.getCellData(excelPath, sheetName, i, 7); // "The form was successfully submitted!"

            // Fill form
            form.enterFirstName(firstName);
            form.enterLastName(lastName);
            form.enterJobTitle(jobTitle);
            form.selectEducation(education);
            form.selectGender(gender);
            form.selectYears(experience);
            form.enterDate(date);
            form.submit();

            // Give you 1.5s to visually see
            Thread.sleep(1500);

            // Grab success text
            String actual = form.getSuccessMessage();

            // Normalize to avoid whitespace/quote problems
            String cleanedExpected = clean(expected);
            String cleanedActual   = clean(actual);

            // Write back to Excel
            ExcelUtils.setCellData(excelPath, sheetName, i, 8, actual); // Actual Result
            if (cleanedActual.equalsIgnoreCase(cleanedExpected)) {
                ExcelUtils.setCellData(excelPath, sheetName, i, 9, "PASS");
                System.out.println("Row " + i + ": PASS");
            } else {
                ExcelUtils.setCellData(excelPath, sheetName, i, 9, "FAIL");
                System.out.println("Row " + i + ": FAIL (Expected: " + expected + ", Got: " + actual + ")");
            }
        }
    }

    private String clean(String s) {
        if (s == null) return "";
        return s.replace("\"", "").replace("\n", " ").replace("\r", " ").trim().replaceAll("\\s+", " ");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
