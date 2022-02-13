package test;

import base.BasePage;
import base.ExcelReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class ElementsTest extends BasePage {
    public static void zoomOut() throws AWTException {
        Robot robot = new Robot();

        for (int i = 0; i <3; i++) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public static ExcelReader er;

    @BeforeMethod
    public void setUpPage() throws InterruptedException, AWTException, IOException {

        List<WebElement> elementsList = driver.findElements(By.className("card-up"));

        elementsList.get(0).click();

        Thread.sleep(2000);

        er = new ExcelReader("src/main/resources/Data.xlsx");
    }

    @Test
    public void verifyTextBoxIsOpened()  {
        elementsPage.getTextBox().click();

        String url = er.getStringData("URL", 2, 0);
        Assert.assertEquals("https://demoqa.com/text-box", url);


    }

    @Test
    public void verifyCheckBoxIsOpened()  {
        elementsPage.getCheckBox().click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/checkbox");


    }

    @Test
    public void verifyRadioButtonIsOpened()  {
        elementsPage.getRadioButton().click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/radio-button");


    }

    @Test
    public void verifyThatDynamicIsOpended(){
        WebElement dyn = driver.findElement(By.id("item-8"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dyn);

        dyn.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/dynamic-properties");

    }

    @Test
    public void verifyTextBoxAcceptInput() throws AWTException {
        verifyTextBoxIsOpened();



        textBoxPage.getUserName().sendKeys("loka");
        textBoxPage.getEmail().sendKeys("k@a.kako");
        textBoxPage.getAddress().sendKeys("loka");
        textBoxPage.getPermanentAddress().sendKeys("kako");


        WebElement dugme = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dugme);

        dugme.click();

       boolean prikaz = textBoxPage.getOutput().isDisplayed();
       Assert.assertTrue(prikaz);

    }


    @Test
    public void verifyListCanBeExpanded(){
        verifyCheckBoxIsOpened();
        checkBox.getArrow().click();

    }

    @Test
    public void verifyListMemberIsClickable(){
        verifyListCanBeExpanded();
        checkBox.getBox().click();

        String url = er.getStringData("URL", 3, 0);
        Assert.assertEquals("https://demoqa.com/checkbox", url);
    }

    @Test
    public void verifyRadioButtonIsClicked(){
        elementsPage.getRadioButton().click();
        checkBox.getRadioB().click();

        String url = er.getStringData("URL", 4, 0);
        Assert.assertEquals("https://demoqa.com/radio-button", url);

        WebElement we = checkBox.getRadioBText();
        Assert.assertTrue(we.isDisplayed());

        Assert.assertEquals(we.getText(), "You have selected Yes");
    }



    @AfterMethod
    public void back() throws InterruptedException {
        Thread.sleep(2000);
      driver.navigate().to("https://demoqa.com/");
    }



    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);

        driver.quit();
    }
}
