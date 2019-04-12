package ru.wrike;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;


public class TestWrike {
    public static void main(String[] args) throws InterruptedException {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.wrike.com/");
        System.out.println("Web resource opened...");

        webDriver.manage().window().maximize();
        System.out.println("Screen of web browser maximized...");

        try {
            webDriver.findElement(By.xpath("//div[@class='wg-header__desktop']" +
                    "//button[@class='wg-header__free-trial-button wg-btn wg-btn--green']"))
                    .click();
            System.out.println("Click on 'Get started for free' button...");

            String randomEmailAddress = generateRandomEmailAddress();
            webDriver.findElement(By.xpath("//input[@class='wg-input modal-form-trial__input']"))
                    .sendKeys(randomEmailAddress);
            System.out.println("Random email address for submit: " + randomEmailAddress);

            webDriver.findElement(By.xpath("//button[@class='wg-btn wg-btn--blue modal-form-trial__submit']"))
                    .click();
            System.out.println("Click on 'Create account'...");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(5000);
            webDriver.quit();
            System.out.println("Web browser closed...");
        }
    }

    /**
     *
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
