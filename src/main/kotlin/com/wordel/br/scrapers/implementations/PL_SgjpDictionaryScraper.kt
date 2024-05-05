package com.wordel.br.scrapers.implementations

import com.wordel.br.scrapers.DictionaryScraper
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class PL_SgjpDictionaryScraper: DictionaryScraper {
    override fun start() {
        val driver: WebDriver = ChromeDriver();
        val url = "http://sgjp.pl/leksemy/#9670/akcja";

        driver.get(url);

        val wait = WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        val emptyElementCondition = ExpectedConditions
            .presenceOfElementLocated(By.cssSelector(".article-header"));

        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(By.tagName("body")), emptyElementCondition))

        val keywordsElements = driver.findElements(By.className("main-column"));
        val keywords = keywordsElements.map { keyword ->
            keyword.text
        };

        println(keywords);

        driver.quit();
    }
}