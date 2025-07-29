package demoqatests;

import demoqabasetest.basetest;
import demoqapages.WebTablesPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTablesTest extends basetest {

    @Test
    public void testAddNewUser() throws InterruptedException {
        driver.get("https://demoqa.com/webtables");

        WebTablesPage webTablesPage = new WebTablesPage(driver);

        webTablesPage.clickAddButton();
        Thread.sleep(1000);

        // Test data
        String fname = "Meenal";
        String lname = "Akmal";
        String email = "teamworkmeenal@gmail.com";
        String salary = "100000";

        // Fill the form
        webTablesPage.enterFirstName(fname);
        webTablesPage.enterLastName(lname);
        webTablesPage.enterEmail(email);
        webTablesPage.enterAge("22");
        webTablesPage.enterSalary(salary);
        webTablesPage.enterDepartment("Testing");

        webTablesPage.clickSubmit();
        Thread.sleep(1500);  // Let the new row render

        //  Fetch all rows and check if the new one exists
        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group"));
        boolean userFound = false;
        for (WebElement row : rows) {
            String rowText = row.getText();
            System.out.println(" Row: " + rowText);
            if (rowText.contains(fname) && rowText.contains(lname) && rowText.contains(salary)) {
                userFound = true;
                break;
            }
        }

        assertTrue(userFound, " New user was not found in the Web Table!");
    }
}

