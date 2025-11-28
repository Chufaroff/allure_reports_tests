package chufaroff.projects.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.BrowserPerTestStrategyExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.Map;

@ExtendWith({BrowserPerTestStrategyExtension.class})
public class TestBase {

    @BeforeAll
    static void setUp() {
        // Получаем параметры из системных свойств (Jenkins передает их как -D параметры)
        String remoteUrl = System.getProperty("remote.url", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        String baseUrl = System.getProperty("base.url", "https://github.com/");
        String browser = System.getProperty("browser", "chrome");
        // String browserVersion = System.getProperty("browser.version", "100.0");
        String browserSize = System.getProperty("browser.size", "1920x1080");

        // Настройка Selenide
        Configuration.baseUrl = baseUrl;
        Configuration.browserSize = browserSize;
        Configuration.browser = browser;
        // Configuration.browserVersion = browserVersion;
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = false;



            // Настройка Allure
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true)
                    .includeSelenideSteps(true)
            );

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Configuration.remote = remoteUrl;

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }
}

