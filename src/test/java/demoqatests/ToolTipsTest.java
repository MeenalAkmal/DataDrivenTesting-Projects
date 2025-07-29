package demoqatests;

import demoqabasetest.basetest;
import demoqapages.ToolTipsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

public class ToolTipsTest extends basetest {

    @Test
    public void testToolTipText() throws InterruptedException {
        driver.get("https://demoqa.com/tool-tips");

        ToolTipsPage page = new ToolTipsPage(driver);
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Hover over the button
        actions.moveToElement(page.getToolTipButton()).perform();
        wait.until(ExpectedConditions.visibilityOf(page.getTooltipContent()));
        String buttonTooltip = page.getTooltipContent().getText();
        System.out.println("Tooltip on button: " + buttonTooltip);
        assertEquals("You hovered over the Button", buttonTooltip);

        // Hover over the text field
        actions.moveToElement(page.getToolTipTextBox()).perform();
        wait.until(ExpectedConditions.visibilityOf(page.getTooltipContent()));
        String textFieldTooltip = page.getTooltipContent().getText();
        System.out.println("Tooltip on text field: " + textFieldTooltip);
        assertEquals("You hovered over the text field", textFieldTooltip);

        System.out.println(" Tooltip tests passed!");
    }
}
