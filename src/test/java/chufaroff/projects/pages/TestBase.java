package chufaroff.projects.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.BrowserPerTestStrategyExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static io.qameta.allure.Allure.attachment;

@ExtendWith({BrowserPerTestStrategyExtension.class})
public class TestBase {
    @BeforeAll

    static void setUp() {
        // Конфигурация Selenide
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 5000;
        Configuration.headless = false; // если нужен видимый браузер

        // Настройка Allure
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true)
        );
    }

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }

    // @Attachment - аннотация Allure, которая автоматически сохраняет возвращаемое значение как вложение в отчет
    // value = "Screenshot" - название вложения в отчете
    // type = "image/png" - MIME-тип (для изображений PNG)
    // fileExtension = "png" - расширение файла
    // Метод возвращает byte[] - байтовый массив скриншота
    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html", fileExtension = ".html")
    public static String attachPageSource() {
        return WebDriverRunner.source();
    }

    @Attachment(value = "Console Logs", type = "text/plain", fileExtension = ".txt")
    public static String attachConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs("browser"));
    }
    // webdriver().driver().source() - получает полный HTML-код страницы, включая весь отрендеренный DOM
    // attachment("Source", ...) - создает вложение в Allure-отчет с названием "Source" и полученным HTML-содержимым
    // attachment("Source", webdriver().driver().source());

    public static void attachmentPageSource() {
        attachment("Page Source", WebDriverRunner.source());
    }
}

