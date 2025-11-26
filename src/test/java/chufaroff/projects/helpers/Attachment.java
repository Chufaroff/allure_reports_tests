package chufaroff.projects.helpers;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attachment {

    // @Attachment - аннотация Allure, которая автоматически сохраняет возвращаемое значение как вложение в отчет
    // value = "Screenshot" - название вложения в отчете
    // type = "image/png" - MIME-тип (для изображений PNG)
    // fileExtension = "png" - расширение файла
    // Метод возвращает byte[] - байтовый массив скриншота
        @io.qameta.allure.Attachment(value = "{attachName}", type = "image/png")
        public static byte[] screenshotAs(String attachName) {
            return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        }

        @io.qameta.allure.Attachment(value = "Page source", type = "text/plain")
        public static byte[] pageSource() {
            return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
        }

        @io.qameta.allure.Attachment(value = "{attachName}", type = "text/plain")
        public static String attachAsText(String attachName, String message) {
            return message;
        }

        public static void browserConsoleLogs() {
            attachAsText(
                    "Browser console logs",
                    String.join("\n", Selenide.getWebDriverLogs(BROWSER))
            );
        }

        @io.qameta.allure.Attachment(value = "Video", type = "text/html", fileExtension = ".html")
        public static String addVideo() {
            return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                    + getVideoUrl()
                    + "' type='video/mp4'></video></body></html>";
        }  // в конце теста вызываем метод:  addVideo();

        public static URL getVideoUrl() {
            String videoUrl = "https://selenoid.autotests.cloud/video/" + sessionId() + ".mp4";
            try {
                return new URL(videoUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        }

    // webdriver().driver().source() - получает полный HTML-код страницы, включая весь отрендеренный DOM
    // attachment("Source", ...) - создает вложение в Allure-отчет с названием "Source" и полученным HTML-содержимым
    // attachment("Source", webdriver().driver().source());
    }

