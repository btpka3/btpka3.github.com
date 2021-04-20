package com.github.btpka3.firstselenium;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @see <a href="https://www.selenium.dev/">selenium</a>
 * @see <a href="https://www.scrapingbee.com/blog/introduction-to-chrome-headless/">Introduction to Chrome Headless with Java</a>
 * @see <a href="https://www.testcontainers.org/supported_docker_environment/">Testcontainers - General Docker requirements</a>
 * @see <a href="https://www.testcontainers.org/features/configuration/">Testcontainers - Custom configuration</a>
 * @see <a href="https://github.com/docker/for-mac/issues/770">Docker for Mac doesn't listen on 2375</a>
 */
@Testcontainers
public class SeleniumTest {

    /*
    docker run -d -v /var/run/docker.sock:/var/run/docker.sock -p 127.0.0.1:1234:1234 bobrik/socat TCP-LISTEN:1234,fork UNIX-CONNECT:/var/run/docker.sock
    */

    @Container
    public static BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions()
                    .addArguments("--headless", "--disable-gpu", "--window-size=375,2000", "--ignore-certificate-errors", "--silent")
            )
            //.withImagePullPolicy(PullPolicy.defaultPolicy())
            ;

    @Test
    void test01() throws IOException {
        String targetUrl = "https://pages.tmall.com/wow/member-club/act/login-proxy?redirectURL=https%3A%2F%2Fs.click.taobao.com%2FamBzppu&un=415463145ebeab4aa8fb54add16a2d54&share_crt_v=1&ut_sk=1.utdid_21549244_1618810829012.TaoPassword-Outside.lianmeng-app&spm=a2159r.13376465.0.0&sp_tk=SUtmRlhYSmtldWQ=";

        WebDriver driver = chrome.getWebDriver();
        driver.get(targetUrl);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("/tmp/SeleniumTest.test01.png"));

        // 网页源码：注意：不是DOM
        String html = driver.getPageSource();
        FileUtils.writeStringToFile(new File("/tmp/SeleniumTest.test01.html"), html, StandardCharsets.UTF_8);

        String domHtml = driver.findElement(By.tagName("html")).getAttribute("outerHTML");
        FileUtils.writeStringToFile(new File("/tmp/SeleniumTest.test01.dom.html"), domHtml, StandardCharsets.UTF_8);

        driver.quit();

    }
}
