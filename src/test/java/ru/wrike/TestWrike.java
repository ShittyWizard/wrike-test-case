package ru.wrike;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


public class TestWrike {
    private static WebDriver webDriver = new ChromeDriver();
    private static Logger logger = Logger.getLogger(TestWrike.class.getName());

    @BeforeClass
    public static void startWebDriver() {
        logger.info("Test started");
        String url = "https://www.wrike.com/";
        webDriver.get(url);
        logger.info("Web driver for url: " + url + " starts...");

        webDriver.manage().window().maximize();
        logger.info("Screen of web driver maximised...");
    }

    @Test
    public void testGetStartedCase() {
//        TODO: change all System.out.println() to logger.log()
        assert webDriver.getTitle().equals("Your online project management software - Wrike");

        if (webDriver.findElements(By.xpath("//form[@class='wg-header__free-trial-form wg-form bf_form_init']" +
                "//button[text()='Get started']")).size() != 0) {
            logger.info("'Get started for free' button is on the page");
            assert true;
            logger.info("Try to click on 'Get started for free' button...");
//            WebElement buttonGetStarted = webDriver.findElement(By.xpath("//div[@class='wg-header__desktop']" +
//                    "//button[@class='wg-header__free-trial-button wg-btn wg-btn--green']"));

            WebElement buttonGetStarted = webDriver.findElement(By.xpath("//form[@class='wg-header__free-trial-form wg-form bf_form_init']" +
                    "//button[text()='Get started']"));

            buttonGetStarted.click();
            logger.info("Successful click on 'Get started for free' button...");
        } else {
            logger.info("There is no 'Get started for free' button...");
            assert false;
        }


        logger.info("Try to input random email address...");
        WebElement inputEmailAddress = webDriver.findElement(By.xpath("//input[@class='wg-input modal-form-trial__input']"));

        assert inputEmailAddress.isDisplayed();
        assert inputEmailAddress.isEnabled();

        String randomEmailAddress = generateRandomEmailAddress();
        inputEmailAddress.sendKeys(randomEmailAddress);
        logger.info("Successful input random email address...");

        logger.info("Try to click on 'Create Wrike account' button...");
        WebElement buttonCreateAccount = webDriver.findElement(By.xpath("//button[@class='wg-btn wg-btn--blue modal-form-trial__submit']"));

        assert buttonCreateAccount.isDisplayed();
        assert buttonCreateAccount.isEnabled();

        String currentUrl = webDriver.getCurrentUrl();
        buttonCreateAccount.click();
        logger.info("Successful click on 'Create Wrike account' button...");

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@class='survey-form']")));
        assert !webDriver.getCurrentUrl().equals(currentUrl);

        // Get the whole form
        WebElement form = webDriver.findElement(By.xpath("//form[@class='survey-form']"));

        assert form.isDisplayed();
        assert form.isEnabled();

        // Fill form for question "How would you describe your interest in using a solution like Wrike?"
        logger.info("Try to fill first part of form (Interest in solution)");

        WebElement interestInSolution = getRandomElementFromList(form.findElements(By.xpath("//div[@data-code='interest_in_solution']/label")));
        assert interestInSolution.isDisplayed();
        assert interestInSolution.isEnabled();

        interestInSolution.click();
        logger.info("Successful first part of form (Interest in solution)  ");

        // Fill form for question "Ideally, how many total team members will eventually be using Wrike?"
        logger.info("Try to fill second part of form (Team members)");

        WebElement teamMembers = getRandomElementFromList(form.findElements(By.xpath("//div[@data-code='team_members']/label")));
        assert teamMembers.isDisplayed();
        assert teamMembers.isEnabled();

        teamMembers.click();
        logger.info("Successful second part of from (Team members) ");

        // Fill form for question "Does your team follow a process for managing work?"
        logger.info("Try to fill third part of form (Primary Business)");

        WebElement primaryBusiness = getRandomElementFromList(form.findElements(By.xpath("div[@data-code='primary_business']/label")));
        assert primaryBusiness.isDisplayed();
        assert primaryBusiness.isEnabled();

        primaryBusiness.click();
//        If uncomment throw org.openqa.selenium.ElementNotInteractableException: element not interactable
//        if (primaryBusiness.getText().equals("Other")) {
//            primaryBusiness.findElement(By.xpath("//input")).sendKeys(new String[] {generateRandomString()});
//        }
        logger.info("Successful third part of form (Primary Business) ");

        logger.info("Try to click on 'Resend email' button");
//        TODO: Optimize XPath request 1
        WebElement buttonResendEmail = webDriver.findElement(By.xpath("//div[@class='wg-grid']/div/p/button"));
        assert buttonResendEmail.isDisplayed();
        assert buttonResendEmail.isEnabled();

        buttonResendEmail.click();
        logger.info("Successful click on 'Resend email' button  ");

//        Check that section "Follow us" at the site footer contains the "Twitter" button that leads to the correct url and has the correct icon;
        logger.info("Check Follow us section: Twitter");
//        TODO: Optimize XPath request 2
        WebElement followTwitterWrike = webDriver.findElement(By.xpath("//ul[@class='wg-footer__social-list']//a[@href='https://twitter.com/wrike']/.."));
        assert followTwitterWrike.isDisplayed();
        assert followTwitterWrike.isEnabled();

        logger.info("Successful check Twitter  ");

//        TODO: Check logo of Twitter
        logger.info("Check correct logo for Twitter");
        assert followTwitterWrike.findElement(By.xpath("//*[local-name()='use' and @*|href='/content/themes/wrike/dist/img/sprite/vector/footer-icons.symbol.svg?v2#twitter']")).isDisplayed();
//        assert followTwitterWrike.findElement(By.xpath("//*[contains(@*|href,'twitter')]")).isDisplayed();
        logger.info("Successful check correct logo for Twitter ");
//        TODO: Report by allure plugin
    }

    @AfterClass
    public static void quitWebDriver() {
        webDriver.quit();
        logger.info("Web driver quits");
    }

    /**
     * @return - random email address with mask " <random text> + wpt@wriketask.qaa "
     */
    private static String generateRandomEmailAddress() {
        // Mask for random email address
        return generateRandomString() + "wpt@wriketask.qaa";
    }

    private static String generateRandomString() {
        final String CHAR_LIST =
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final int RANDOM_STRING_LENGTH = getRandomNumber();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            char c = CHAR_LIST.charAt(getRandomNumber());
            randomString.append(c);
        }
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

    private static WebElement getRandomElementFromList(List<WebElement> listOfElements) {
        Random random = new Random();
        return listOfElements.get(random.nextInt(listOfElements.size()));
    }
}
