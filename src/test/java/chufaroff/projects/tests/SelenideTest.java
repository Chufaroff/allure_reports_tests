package chufaroff.projects.tests;

import chufaroff.projects.pages.TestBase;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.linkText;

public class SelenideTest extends TestBase {

    @Test
    void testIssueSearch() {
        open("https://github.com/");
        $("button[placeholder='Search or jump to...']").click();
        $("#query-builder-test").setValue("eroshenkoam/allure-example").pressEnter();
        $(linkText("eroshenkoam/allure-github-example")).click();
        $("#issues-tab").click();
        $(withText("#3")).should(exist);
    }
}
