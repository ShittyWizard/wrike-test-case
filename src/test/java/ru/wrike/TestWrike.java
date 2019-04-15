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
        // Check that 'Get Started for free' button is visible
        By buttonGetStartedXpath = By.xpath("//div[@class='wg-header__desktop']" +
                "//button[contains(text(), 'Get started')]");
        assert isElementVisible(buttonGetStartedXpath);

        WebElement buttonGetStarted = webDriver.findElement(buttonGetStartedXpath);

        assert buttonGetStarted.isEnabled();

        logger.info("Try to click on 'Get started for free' button");
        buttonGetStarted.click();
        logger.info("Successful click on 'Get started for free' button...");

        By inputEmailAddressXpath = By.xpath("//input[@class='wg-input modal-form-trial__input']");
        assert isElementVisible(inputEmailAddressXpath);

        WebElement inputEmailAddress = webDriver.findElement(inputEmailAddressXpath);

        String randomEmailAddress = generateRandomEmailAddress();

        logger.info("Try to input random email address...");
        inputEmailAddress.sendKeys(randomEmailAddress);
        logger.info("Successful input random email address...");

        logger.info("Try to click on 'Create Wrike account' button...");
        By buttonCreateAccountXpath = By.xpath("//button[@class='wg-btn wg-btn--blue modal-form-trial__submit']");
        assert isElementVisible(buttonCreateAccountXpath);

        WebElement buttonCreateAccount = webDriver.findElement(buttonCreateAccountXpath);
        assert buttonCreateAccount.isEnabled();

        String currentUrl = webDriver.getCurrentUrl();
        logger.info("Try to click on 'Create Wrike account' button...");
        buttonCreateAccount.click();
        logger.info("Successful click on 'Create Wrike account' button...");

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@class='survey-form']")));
        assert !webDriver.getCurrentUrl().equals(currentUrl);

        // Get the whole form
        By surveyFormXpath = By.xpath("//form[@class='survey-form']");
        assert isElementVisible(surveyFormXpath);

        WebElement surveyForm = webDriver.findElement(surveyFormXpath);

        // Fill form for question "How would you describe your interest in using a solution like Wrike?"
        logger.info("Try to fill part of form (Interest in solution)");

        By interestInSolutionXpath = By.xpath("//div[@data-code='interest_in_solution']/label");
        assert isElementVisible(interestInSolutionXpath);

        WebElement interestInSolution = getRandomElementFromList(surveyForm.findElements(interestInSolutionXpath));
        assert interestInSolution.isEnabled();

        interestInSolution.click();
        logger.info("Successful filling part of form (Interest in solution)");

        // Fill form for question "Ideally, how many total team members will eventually be using Wrike?"
        logger.info("Try to fill part of form (Team members)");

        By teamMembersXpath = By.xpath("//div[@data-code='team_members']/label");
        assert isElementVisible(teamMembersXpath);
        WebElement teamMembers = getRandomElementFromList(surveyForm.findElements(teamMembersXpath));

        teamMembers.isEnabled();
        teamMembers.click();
        logger.info("Successful filling part of from (Team members) ");

        // Fill form for question "Does your team follow a process for managing work?"
        logger.info("Try to fill third part of form (Primary Business)");

        By primaryBusinessXpath = By.xpath("//div[@data-code='primary_business']/label");
        assert isElementVisible(primaryBusinessXpath);
        WebElement primaryBusiness = getRandomElementFromList(surveyForm.findElements(primaryBusinessXpath));

        primaryBusiness.isEnabled();
        primaryBusiness.click();
        logger.info("Successful third part of form (Primary Business) ");

        logger.info("Checking that form is submitted...");
        By buttonSubmitResultXpath = By.xpath("//button[@class='submit wg-btn wg-btn--navy js-survey-submit']");
        assert isElementVisible(buttonSubmitResultXpath);

        WebElement buttonSubmitResult = surveyForm.findElement(buttonSubmitResultXpath);
        assert buttonSubmitResult.isEnabled();

        logger.info("Try to click on 'Resend email' button");
        By buttonResendEmailXpath = By.xpath("//button[text()='Resend email' and contains(@class,'wg-btn--hollow button')]");
        assert isElementVisible(buttonResendEmailXpath);
        WebElement buttonResendEmail = webDriver.findElement(buttonResendEmailXpath);

        assert buttonResendEmail.isEnabled();
        buttonResendEmail.click();

        // Check that 'Resend email' starts loading
        assert isElementVisible(By.xpath("//button[text()='Resend email' and contains(@class,'wg-btn--loading')]"));

        // Check that word 'again' shows into text
        assert webDriver.findElement(By.xpath("//span[@class='again']")).getCssValue("opacity").equals("0");
        webDriverWait.until(ExpectedConditions.attributeContains(By.xpath("//span[@class='again']"), "opacity", "1"));
        assert webDriver.findElement(By.xpath("//span[@class='again']")).getCssValue("opacity").equals("1");
        logger.info("Successful click on 'Resend email' button and check it");

        logger.info("Check Follow us section: Twitter link");

        By followTwitterWrikeXpath = By.xpath("//li[@class='wg-footer__social-item']//a[@href='https://twitter.com/wrike' and @rel='dofollow']/..");
        assert isElementVisible(followTwitterWrikeXpath);

        logger.info("Successful check Twitter link");

        logger.info("Check correct logo for Twitter");
        assert isElementVisible(By.xpath("//*[local-name()='use' and contains(@*|href,'twitter')]"));
        logger.info("Successful check correct logo for Twitter ");

//        TODO: Report by allure plugin
    }

    @AfterClass
    public static void quitWebDriver() {
        webDriver.quit();
        logger.info("Web driver quits");
    }

    private boolean isElementVisible(By query) {
        if (webDriver.findElements(query).size() != 0) {
            return true;
        } else {
            logger.info("No element for query:" + query.toString());
            return false;
        }
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
