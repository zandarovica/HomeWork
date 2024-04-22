import com.github.javafaker.Faker;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static java.time.Duration.ofSeconds;
import static lv.acodemy.utils.ConfigurationProperties.getConfiguration;

@Slf4j
public class AddStudentTest {

    Faker fake = new Faker();
    ChromeDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, ofSeconds(getConfiguration().getLong("wait.time")));


    @Test
    public void acodemyTest() {
        driver.manage().timeouts().implicitlyWait(ofSeconds(getConfiguration().getLong("wait.time")));

        logger.info("Will open now: " + getConfiguration().getString("app.url"));

        driver.get(getConfiguration().getString("app.url"));
        WebElement addStudentButton = driver.findElement(By.id("addStudentButton"));
        addStudentButton.click();

        WebElement newNameField = driver.findElement(By.id("name"));
        newNameField.sendKeys(fake.name().fullName());

        WebElement newEmailField = driver.findElement(By.id("email"));
        newEmailField.sendKeys(fake.internet().emailAddress());

        driver.findElement(By.id("gender")).click();
        driver.findElement(By.xpath("//div[@class='rc-virtual-list-holder-inner']//div[text()='FEMALE']")).click();

        WebElement submitButton = driver.findElement(By.xpath("//span[text()='Submit']//parent::button"));
        submitButton.submit();

        WebElement notificationMessage = driver.findElement(By.className("ant-notification-notice-message"));
        wait.until(ExpectedConditions.textToBePresentInElement(notificationMessage, "Student successfully added"));
        Assertions.assertThat(notificationMessage.getText()).isEqualTo("Student successfully added");

    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
