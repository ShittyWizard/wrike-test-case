# wrike-test-case

# short comment
It was my 1st project with using Java Selenium, and I will be glad to hear tips and mistakes. Thank you!

I can't add allure plugin to my project (using this "tutorial" - https://habr.com/en/company/sberbank/blog/358836/), because I always get exception from maven-surefire-plugin.


# Task:
You need to implement a test which contains following steps:

Test case scenario:

- Open url: wrike.com;
- Click "Get started for free" button near "Login" button;
- Fill in the email field with random generated value of email with mask “<random_text>+wpt@wriketask.qaa” (e.g. “abcdef+wpt@wriketask.qaa”);
- Click on "Create my Wrike account" button + check with assertion that you are moved to the next page;
- Fill in the Q&A section at the left part of page (like random generated answers) + check with assertion that your answers are submitted;
- Click on "Resend email" + check it with assertion;
- Check that section "Follow us" at the site footer contains the "Twitter" button that leads to the correct url and has the correct icon;
- Create results report using allure plugin (by maven).  
(you may close all needless tooltips on your way)


Stack of technologies for implementation:

- Platform: java8
- Build and run: maven
- Test framework: junit4
- UI test: selenium 3 (without wrappers)
- Pattern: pageObject (test -> steps -> pages)
- Reporting: allure plugin
- Selector: xpath (should be short and stable)

