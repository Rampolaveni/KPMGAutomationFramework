package com.kpmg.webAutomation.utils;

import com.kpmg.webAutomation.controllers.DriverManager;
import com.kpmg.webAutomation.controllers.SetUpTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;

public class CommonUtilities extends SetUpTest {

    protected WebDriver driverInstance = DriverManager.getDriver();

    public WebDriverWait driverWait() {
        WebDriverWait explicitWait = new WebDriverWait(driverInstance, Duration.ofSeconds(30));
        return explicitWait;
    }

    public boolean isElementPresent(WebElement ele) {
        try {
            driverWait().until(ExpectedConditions.visibilityOf(ele));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean click(WebElement locator) throws Exception {
        boolean blnVal = false;
        try {
            driverWait().until(ExpectedConditions.elementToBeClickable(locator));
            if (locator.isDisplayed()) {
                locator.click();
                System.out.println("Clicked on element");
                blnVal = true;
            }

        } catch (Exception e) {
            System.out.println("Unable to click on element");
            e.printStackTrace();
            assertTrue(blnVal);
            throw (e);
        }
        return blnVal;
    }

    public boolean type(WebElement locator, String message, String strData) throws Exception {
        boolean blnVal = false;
        try {
            driverWait().until(ExpectedConditions.elementToBeClickable(locator));
            if (locator.isDisplayed()) {
                if (locator.getText() != null || !locator.getText().equalsIgnoreCase("")) {
                    locator.clear();
                }
                locator.sendKeys(strData);
                System.out.println("Text entered in the textbox is: " + strData);
                blnVal = true;

            }
        } catch (RuntimeException localRuntimeException) {
            System.out.println("Unable to Enter the value in the Textbox :");
            throw (localRuntimeException);
        }
        return blnVal;

    }

}
