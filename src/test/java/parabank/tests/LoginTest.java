package parabank.tests;

import Common.utils.ExcelUtils;
import parabank.base.BaseTest;
import parabank.pages.LoginPage;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends BaseTest {

    @Test
    public void testParaBankLoginWithExcelData() {
        String excelPath = "TestData/TestingFile.xlsx";
        String sheetName = "ParaBankLogin";

        int rowCount = ExcelUtils.getRowCount(excelPath, sheetName);
        for (int i = 1; i <= rowCount; i++) {
            try {
                String username = ExcelUtils.getCellData(excelPath, sheetName, i, 0);
                String password = ExcelUtils.getCellData(excelPath, sheetName, i, 1);
                String expectedResult = ExcelUtils.getCellData(excelPath, sheetName, i, 2);

                driver.get("https://parabank.parasoft.com/parabank/index.htm");
               LoginPage loginPage = new LoginPage(driver);

                loginPage.enterUsername(username);
                loginPage.enterPassword(password);
                loginPage.clickLogin();

                String actualResult;
                if (loginPage.isLoggedIn()) {
                    actualResult = "Login Success";
                    loginPage.logout();
                } else {
                    actualResult = loginPage.getErrorMessage();
                    if (actualResult.isEmpty()) {
                        actualResult = "No message displayed";
                    }
                }

                ExcelUtils.setCellData(excelPath, sheetName, i, 3, actualResult);
                String status = actualResult.trim().equalsIgnoreCase(expectedResult.trim()) ? "PASS" : "FAIL";
                ExcelUtils.setCellData(excelPath, sheetName, i, 4, status);

            } catch (Exception e) {
                // Log the error and continue
                ExcelUtils.setCellData(excelPath, sheetName, i, 3, "Error: " + e.getMessage());
                ExcelUtils.setCellData(excelPath, sheetName, i, 4, "FAIL");
            }}}
}





