package chufaroff.projects.pages;

import chufaroff.projects.helpers.Attachment;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.linkText;


public class WebStepsPage extends TestBase {

    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("https://github.com/");
        Attachment.screenshotAs("Main Page Opened");
    }

    @Step("Ищем репозиторий {repo}")
    public void searchForRepository(String repo) {
        $("button[placeholder='Search or jump to...']").click();
        $("#query-builder-test").setValue(repo).pressEnter();
        Attachment.screenshotAs("Search perfomed");
    }

    @Step("Кликаем по ссылке репозитория {repo} ")
    public void clickOnRepositoryLink(String repo) {
        $(linkText(repo)).click();
        Attachment.screenshotAs("Repository opened");
    }

    @Step("Открываем таб Issue")
    public void openIssueTab() {
        $("#issues-tab").click();
        Attachment.screenshotAs("Issue tab opened");
        Attachment.pageSource();
    }

    @Step("Проверяем наличие Issue с номером {issue}")
    public void shouldSeeIssueWithNumber(int issue) {
        $(withText("#" + issue)).should(exist);
        Attachment.screenshotAs("Issue found");
        Attachment.browserConsoleLogs();
    }
}