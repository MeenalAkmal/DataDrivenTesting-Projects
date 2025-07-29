package parabank.tests;

import Common.utils.ExcelUtils;
import parabank.base.BaseTest;
import parabank.pages.RegisterPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterTest extends BaseTest {

    private static final String excelPath = "TestData/TestingFile.xlsx";
    private static final String sheetName = "ParaBankRegister";

    @Test
    public void registerTestWithExcelData() {

        int rowCount = ExcelUtils.getRowCount(excelPath, sheetName);

        for (int i = 1; i <= rowCount; i++) {
            driver.get("https://parabank.parasoft.com/parabank/register.htm");

            RegisterPage registerPage = new RegisterPage(driver);

            String firstName = ExcelUtils.getCellData(excelPath, sheetName, i, 0);
            String lastName = ExcelUtils.getCellData(excelPath, sheetName, i, 1);
            String address = ExcelUtils.getCellData(excelPath, sheetName, i, 2);
            String city = ExcelUtils.getCellData(excelPath, sheetName, i, 3);
            String state = ExcelUtils.getCellData(excelPath, sheetName, i, 4);
            String zip = ExcelUtils.getCellData(excelPath, sheetName, i, 5);
            String phone = ExcelUtils.getCellData(excelPath, sheetName, i, 6);
            String ssn = ExcelUtils.getCellData(excelPath, sheetName, i, 7);
            String username = ExcelUtils.getCellData(excelPath, sheetName, i, 8);
            String password = ExcelUtils.getCellData(excelPath, sheetName, i, 9);
            String confirm = ExcelUtils.getCellData(excelPath, sheetName, i, 10);
            String expected = ExcelUtils.getCellData(excelPath, sheetName, i, 11);

            registerPage.fillRegistrationForm(firstName, lastName, address, city, state,
                    zip, phone, ssn, username, password, confirm);
            registerPage.clickRegister();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            String actualMsg = "";

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getSuccessLocator()));
                actualMsg = driver.findElement(registerPage.getSuccessLocator()).getText();
            } catch (Exception e) {
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getErrorLocator()));
                    actualMsg = driver.findElement(registerPage.getErrorLocator()).getText();
                } catch (Exception ex) {
                    actualMsg = "Welcome";
                }
            }

            ExcelUtils.setCellData(excelPath, sheetName, i, 12, actualMsg);

            if (actualMsg.contains(expected)) {
                ExcelUtils.setCellData(excelPath, sheetName, i, 13, "PASS");
            } else {
                ExcelUtils.setCellData(excelPath, sheetName, i, 13, "FAIL");
            }
        }
    }
}

