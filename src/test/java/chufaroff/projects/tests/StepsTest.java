package chufaroff.projects.tests;

import chufaroff.projects.pages.TestBase;
import chufaroff.projects.pages.WebStepsPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

@Owner("Chufarov Bogdan")
@Severity(SeverityLevel.NORMAL)
@Feature("Issue в репозитории")
@Story("Поиск Issue")
public class StepsTest extends TestBase {

    private static final String REPOSITORY = "eroshenkoam/allure-github-example";
    private static final int ISSUE = 3;

    @Test
    @Link(name = "Testing", url = "https://github.com/")
    @DisplayName("Проверка наличия вкладки Issue с номером #3 в конкретном репозитории")
    void testLambdaStep() {

        step("Открываем главную страницу", () -> {
            open("https://github.com/");
        });

        step("Ищем репозиторий " + REPOSITORY, () -> {
            $("button[placeholder='Search or jump to...']").click();
            $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        });

        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });

        step("Открываем таб Issue", () -> {
            $("#issues-tab").click();

            // webdriver().driver().source() - получает полный HTML-код страницы, включая весь отрендеренный DOM
            // attachment("Source", ...) - создает вложение в Allure-отчет с названием "Source" и полученным HTML-содержимым
            attachment("Source", webdriver().driver().source());
        });

        step("Проверяем наличие Issue с номером " + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(exist);
        });
    }

    @Test
    @Link(name = "Testing", url = "https://github.com/")
    @DisplayName("Проверка наличия вкладки Issue с номером #3 в конкретном репозитории")
    void testAnnotatedStep() {
        WebStepsPage webSteps = new WebStepsPage();

        webSteps.openMainPage();
        webSteps.searchForRepository(REPOSITORY);
        webSteps.clickOnRepositoryLink(REPOSITORY);
        webSteps.openIssueTab();
        webSteps.shouldSeeIssueWithNumber(ISSUE);
        takeScreenshot();
        attachmentPageSource();
    }
}
