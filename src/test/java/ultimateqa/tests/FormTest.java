package ultimateqa.tests;

import ultimateqa.pages.FormPage;
import Common.utils.ExcelUtils;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class FormTest {
    WebDriver driver;
    ExcelUtils excel;
    String excelPath = "TestData/TestingFile.xlsx";
    String sheetName = "UltimateQA";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

   //     excel = new ExcelUtils(excelPath, sheetName);
    }

    @Test
    public void formSubmissionDataDriven() {
        int rowCount = ExcelUtils.getRowCount(excelPath, sheetName);

        for (int i = 1; i <= rowCount; i++) {
            driver.get("https://ultimateqa.com/filling-out-forms/");
            FormPage form = new FormPage(driver);

            String name = ExcelUtils.getCellData(excelPath, sheetName, i, 0);
            String message = ExcelUtils.getCellData(excelPath, sheetName, i, 1);
            String expectedResult = ExcelUtils.getCellData(excelPath, sheetName, i, 2);

            form.enterName(name);
            form.enterMessage(message);
            form.clickSubmit();

            String actualResult = form.getResultMessage();

         // Write Actual Result to Excel
         ExcelUtils.setCellData(excelPath, sheetName, i, 3, actualResult); 

         // Clean and compare
         String cleanedActual = actualResult.trim().replaceAll("\\s+", " ");
         String cleanedExpected = expectedResult.trim().replaceAll("\\s+", " ");

         if (cleanedActual.equalsIgnoreCase(cleanedExpected)) {
             ExcelUtils.setCellData(excelPath, sheetName, i, 4, "PASS");
         } else {
             ExcelUtils.setCellData(excelPath, sheetName, i, 4, "FAIL");
         }

        }
    }

        
    

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


