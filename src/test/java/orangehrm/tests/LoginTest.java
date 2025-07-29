
package orangehrm.tests;

import Common.utils.ExcelUtils;
import orangehrm.pages.LoginPage;
import orangehrm.base.BaseTest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginWithExcelData() {
        String excelPath = "TestData/TestingFile.xlsx"; // relative path from project root
        String sheetName = "OrangeHRM";

        int rowCount = ExcelUtils.getRowCount(excelPath, sheetName);
        StringBuilder failures = new StringBuilder();

        for (int i = 1; i <= rowCount; i++) {
            System.out.println("Running test for Row: " + i);

            String username = ExcelUtils.getCellData(excelPath, sheetName, i, 0);
            String password = ExcelUtils.getCellData(excelPath, sheetName, i, 1);
            String expectedResult = ExcelUtils.getCellData(excelPath, sheetName, i, 2);

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            LoginPage loginPage = new LoginPage(driver);

            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            String actualResult = loginPage.getLoginResult(wait);

            System.out.println("Expected: [" + expectedResult + "], Actual: [" + actualResult + "]");

            ExcelUtils.setCellData(excelPath, sheetName, i, 3, actualResult);
            if (actualResult.trim().equalsIgnoreCase(expectedResult.trim())) {
                ExcelUtils.setCellData(excelPath, sheetName, i, 4, "PASS");
            } else {
                ExcelUtils.setCellData(excelPath, sheetName, i, 4, "FAIL");
                failures.append("Row ").append(i)
                        .append(" failed: Expected [").append(expectedResult)
                        .append("] but got [").append(actualResult).append("]\n");
            }

            loginPage.logoutIfLoggedIn();
        }

        if (failures.length() > 0) {
            throw new AssertionError(failures.toString());
        }
    }
}

