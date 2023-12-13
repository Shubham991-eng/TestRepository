package RegressionTestCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DynamicTableAutomation {

    // Initializing WebDriver
    public static WebDriver driver;

    // WebElements using @FindBy annotation
    @FindBy(xpath = "//summary[text()='Table Data']")
    private WebElement tableDataButton;
    
    @FindBy(xpath="//textarea[@id=\"jsondata\"]")
    private WebElement dataInputTextBox;

    @FindBy(xpath = "//button[text()='Refresh Table']")
    private WebElement refreshTableButton;

    @FindBy(xpath = "//table[@id=\"dynamictable\"]//tr")
    private List<WebElement> tableRows;

    // Constructor to initialize elements
    public DynamicTableAutomation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method to perform the automation steps
    public void performAutomation() {
        // Step 1: Open the URL
        driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");

       // Maximize the browser window
        driver.manage().window().maximize();
        
        // Step 2: Click on Table Data button
        tableDataButton.click();

        // Step 3: Remove existing data, insert new data, and click Refresh Table button
        clearInputTextBox();
        String jsonData = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\": \"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\": \"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";
        dataInputTextBox.sendKeys(jsonData);
        refreshTableButton.click();

        // Step 4: Verify the entered data in the table
        assertTableData(jsonData);
    }

    // Method to clear existing data from the input text box
    private void clearInputTextBox() {
        dataInputTextBox.clear();
    }

    // Method to assert the data in the table
    private void assertTableData(String expectedData) {
        // Extracting data from table and comparing with expected data
        for (int i = 0; i < tableRows.size(); i++) {
            String actualRowData = tableRows.get(i).getText();
            assert actualRowData.equals(expectedData) : "Data mismatch in row " + (i + 1);
        }

        System.out.println("Assertion passed: Data matches in the table!");
    }

    public static void main(String[] args) {
       

        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();

        // Create an object of the automation class
        DynamicTableAutomation automation = new DynamicTableAutomation(driver);

        // Perform the automation steps
        automation.performAutomation();

        // Close the browser
        driver.quit();
    }
}

