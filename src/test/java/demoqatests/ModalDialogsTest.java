package demoqatests;

import demoqabasetest.basetest;
import demoqapages.ModalDialogsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModalDialogsTest extends basetest {

    @Test
    public void testModalDialogs() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        ModalDialogsPage modalPage = new ModalDialogsPage(driver);

        //  Test Small Modal
        modalPage.openSmallModal();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-sm")));

        try {
            String smallTitle = modalPage.getSmallModalText();
            System.out.println("Small Modal Title: " + smallTitle);
            assertEquals("Small Modal", smallTitle, "Small modal title mismatch!");
        } catch (Exception e) {
            System.out.println(" Error with small modal: " + e.getMessage());
            e.printStackTrace();
        }

        modalPage.closeSmallModal();
        Thread.sleep(3000);

        //  Test Large Modal
        modalPage.openLargeModal();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));

        try {
            String largeTitle = modalPage.getLargeModalText();
            System.out.println(" Large Modal Title: " + largeTitle);
            assertEquals("Large Modal", largeTitle, "Large modal title mismatch!");
        } catch (Exception e) {
            System.out.println(" Error with large modal: " + e.getMessage());
            e.printStackTrace();
        }
        modalPage.closeLargeModal();
        Thread.sleep(2000);
    }
}
