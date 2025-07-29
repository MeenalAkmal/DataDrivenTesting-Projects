

package demoqatests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses
({
    AlertsTest.class,
    BrowserWindowsTest.class,
    WebTablesTest.class,
    PracticeFormTest.class,
    DynamicPropertiesTest.class,
    LinksTest.class,
    ButtonsTest.class,
    CheckBoxTest.class,
    TextBoxTest.class,
    RadioButtonTest.class,
    FramesTest.class,    
    UploadAndDownloadTest.class,
    // AccordianTest.class,
    DatePickerTest.class,
    SliderTest.class,
     ProgressBarTest.class,
     ToolTipsTest.class,
    ModalDialogsTest.class,
    AutoCompleteTest.class,
    // BrokenLinksAndImages.class,
    MenuTest.class,
     TabsTest.class,
    SelectMenuTest.class,
    SortableTest.class,
    SelectableTest.class,
    ResizableTest.class,
    DroppableTest.class,
    DraggableTest.class,
    ProfileTest.class,
    LoginTest.class,
    BookStoreTest.class,
    BookDetailsTest.class
})
public class AllTestSuite {
    
}

