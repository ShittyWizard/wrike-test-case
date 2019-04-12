package ru.wrike;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;


public class TestWrike {
    private static WebDriver webDriver = new ChromeDriver();

    @BeforeClass
    public static void startWebDriver() {
        webDriver.get("https://www.wrike.com/");
        System.out.println("Web resource opened...");

        webDriver.manage().window().maximize();
        System.out.println("Screen of web browser maximized...");
    }

    @Test
    public void testGetStartedCase() throws InterruptedException {
        System.out.println("Try to click on 'Get started for free' button");
        WebElement buttonGetStarted = webDriver.findElement(By.xpath("//div[@class='wg-header__desktop']" +
                "//button[@class='wg-header__free-trial-button wg-btn wg-btn--green']"));

        assert buttonGetStarted.isDisplayed();
        assert buttonGetStarted.isEnabled();

        buttonGetStarted.click();
        System.out.println("Click is successful...");

        WebElement inputEmailAddress = webDriver.findElement(By.xpath("//input[@class='wg-input modal-form-trial__input']"));

        assert inputEmailAddress.isEnabled();

        String randomEmailAddress = generateRandomEmailAddress();
        inputEmailAddress.sendKeys(randomEmailAddress);

        WebElement buttonCreateAccount = webDriver.findElement(By.xpath("//button[@class='wg-btn wg-btn--blue modal-form-trial__submit']"));

        String currentUrl = webDriver.getCurrentUrl();
        buttonCreateAccount.click();
        Thread.sleep(5000);
        assert !webDriver.getCurrentUrl().equals(currentUrl);
        Thread.sleep(5000);
    }

    @AfterClass
    public static void quitWebDriver() {
        webDriver.quit();
    }

    /**
     * @return - random email address with mask " <random text> + wpt@wriketask.qaa "
     */
    private static String generateRandomEmailAddress() {
        final String CHAR_LIST =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final int RANDOM_STRING_LENGTH = getRandomNumber();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            char c = CHAR_LIST.charAt(getRandomNumber());
            randomString.append(c);
        }
        // Mask for random email address
        randomString.append("wpt@wriketask.qaa");
        return randomString.toString();
    }

    private static int getRandomNumber() {
        int randomInt;
        Random randomGenerator = new Random();
        // CHAR_LIST.length = 62
        randomInt = randomGenerator.nextInt(62);
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
}
