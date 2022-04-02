package com.github.btpka3.firstselenium;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * @see <a href="https://www.selenium.dev/">selenium</a>
 * @see <a href="https://www.scrapingbee.com/blog/introduction-to-chrome-headless/">Introduction to Chrome Headless with Java</a>
 * @see <a href="https://www.testcontainers.org/supported_docker_environment/">Testcontainers - General Docker requirements</a>
 * @see <a href="https://www.testcontainers.org/features/configuration/">Testcontainers - Custom configuration</a>
 * @see <a href="https://github.com/docker/for-mac/issues/770">Docker for Mac doesn't listen on 2375</a>
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/">Chrome DevTools Protocol</a>
 * @see <a href="https://developers.google.com/web/tools/puppeteer">Puppeteer</a>
 * @see <a href="https://applitools.com/blog/selenium-chrome-devtools-protocol-cdp-how-does-it-work/">Selenium Chrome DevTools Protocol (CDP) API: How Does It Work?</a>
 * @see WebDriverEventListener
 * @see CapabilityType
 */
public class Selenium2Test {

    @Test
    void test01() throws IOException, InterruptedException {

        String targetUrl = "https://pages.tmall.com/wow/member-club/act/login-proxy?redirectURL=https%3A%2F%2Fs.click.taobao.com%2FamBzppu&un=415463145ebeab4aa8fb54add16a2d54&share_crt_v=1&ut_sk=1.utdid_21549244_1618810829012.TaoPassword-Outside.lianmeng-app&spm=a2159r.13376465.0.0&sp_tk=SUtmRlhYSmtldWQ=";
        String chromeDriverPath = System.getProperty("user.home") + "/Downloads/chromedriver-89";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--headless", "--disable-gpu", "--window-size=375,2000", "--ignore-certificate-errors", "--silent");
        //options.addExtensions (new File("/path/to/extension.crx"));

        //Proxy proxy = new Proxy();
        //proxy.setSocksProxy("");
        //options.setCapability("proxy", proxy);

        WebDriver driver = new ChromeDriver(options);

        driver.get(targetUrl);

        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("/tmp/Selenium2Test.test01.png"));

        // 网页源码：注意：不是DOM
        String html = driver.getPageSource();
        FileUtils.writeStringToFile(new File("/tmp/Selenium2Test.test01.html"), html, StandardCharsets.UTF_8);

        // https://www.guru99.com/execute-javascript-selenium-webdriver.html
        //driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Call executeAsyncScript() method to wait for 5 seconds
        String domHtml = js.executeScript("return document.getElementsByTagName(\"html\")[0].outerHTML").toString();
        //System.out.println("domHtml= " + domHtml);
        //String domHtml = driver.findElement(By.tagName("html")).getAttribute("outerHTML");
        FileUtils.writeStringToFile(new File("/tmp/Selenium2Test.test01.dom.html"), domHtml, StandardCharsets.UTF_8);


         // TODO : save file as Webpage,Complete
        driver.quit();


        Assertions.assertTrue(domHtml.contains("<img"));

    }

    public static class MyListener extends AbstractWebDriverEventListener {

    }
}
