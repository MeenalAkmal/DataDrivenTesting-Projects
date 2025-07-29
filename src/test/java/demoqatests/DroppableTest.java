package demoqatests;

import demoqapages.DroppablePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroppableTest {

    @Test
    public void testAllDroppableTabs() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/droppable");
        driver.manage().window().maximize();

        DroppablePage page = new DroppablePage(driver);
        Actions actions = new Actions(driver);

        // 1️ Simple Tab
        page.switchToTab("Simple");
        actions.dragAndDrop(page.getDragMeBox(), page.getDropHereBox()).perform();
        Thread.sleep(1000);
        String simpleResult = page.getDropResultText();
        System.out.println(" Simple Tab Result: " + simpleResult);
        assertEquals("Dropped!", simpleResult);

        // 2️ Accept Tab
        page.switchToTab("Accept");
        actions.dragAndDrop(page.getNotAcceptable(), page.getAcceptDropBox()).perform();
        Thread.sleep(1000);
        String notAcceptText = page.getAcceptDropText();
        System.out.println(" Not Acceptable Result: " + notAcceptText);
        assertEquals("Drop here", notAcceptText);

        actions.dragAndDrop(page.getAcceptable(), page.getAcceptDropBox()).perform();
        Thread.sleep(1000);
        String acceptText = page.getAcceptDropText();
        System.out.println(" Acceptable Result: " + acceptText);
        assertEquals("Dropped!", acceptText);

        // 3️ Prevent Propagation Tab
        page.switchToTab("Prevent Propogation");
        actions.dragAndDrop(page.getDragBoxPropagation(), page.getOuterDropBox()).perform();
        Thread.sleep(1000);
        String outerText = page.getOuterDropText();
        String innerText = page.getInnerDropText();
        System.out.println(" Outer Drop Text: " + outerText);
        System.out.println(" Inner Drop Text: " + innerText);

        // 4️ Revert Draggable Tab
        page.switchToTab("Revert Draggable");
        actions.dragAndDrop(page.getWillRevert(), page.getRevertDropBox()).perform();
        Thread.sleep(1000);
        System.out.println(" Dragged 'Will Revert'");

        actions.dragAndDrop(page.getNotRevert(), page.getRevertDropBox()).perform();
        Thread.sleep(1000);
        System.out.println(" Dragged 'Not Revert'");

        driver.quit();
    }
}
