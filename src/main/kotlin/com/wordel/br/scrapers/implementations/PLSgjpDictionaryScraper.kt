package com.wordel.br.scrapers.implementations

import com.wordel.br.scrapers.DictionaryScraper
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlin.random.Random

class PLSgjpDictionaryScraper: DictionaryScraper {
    override val driver: WebDriver = ChromeDriver(getDriverOptions());
    override val url = "http://sgjp.pl/leksemy";

    private val js = driver as JavascriptExecutor;
    private val wait = WebDriverWait(driver, Duration.ofSeconds(60));
    private val loadingSpinnerSelector = "#left > div:nth-child(2) > canvas";

    override fun start() {
        driver.manage().window().size = Dimension(1366, 768);
        driver.get(url);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        val emptyElementCondition = ExpectedConditions
            .presenceOfElementLocated(By.cssSelector(".article-header"));

        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(By.tagName("body")), emptyElementCondition))

        val splashButton = driver.findElement(By.id("splash-button"));

        clickOn(splashButton);

        prepareFilters();

        collectKeywords();
        goToNextKeywordsPage();

        driver.quit();
    }

    private fun prepareFilters() {
        val filterButton = driver.findElement(By.id("filter-button"));
        val addFilterButton = driver.findElement(By.id("add-filter-button"));

        clickOn(filterButton) // open filters popup

        clickOn(addFilterButton);
        clickOn(addFilterButton); // add two additional blank filters

        var dropdownMenus = driver.findElements(By.cssSelector(".ui-selectmenu-dropdown, .ui-multiselect2"));
        dropdownMenus.removeFirst(); // First one is not part of filters

        setFilterKey(dropdownMenus[3], "Klasa leksemów");
        setFilterKey(dropdownMenus[6], "Pospolitość");

        dropdownMenus = driver.findElements(By.cssSelector(".ui-selectmenu-dropdown, .ui-multiselect2"));
        dropdownMenus.removeFirst(); // First one is not part of filters


        setFilterValues(dropdownMenus[5], listOf("rzeczownik", "przymiotnik", "liczebnik"));
        setFilterValues(
            dropdownMenus[8],
            listOf(
                "nazwa pospolita",
                "nazwa święta",
                "nazwa geograficzna",
                "człon nazwy święta",
                "nazwa własna astronomiczna",
                "nazwa własna środka lokomocji"
            )
        );
    }

    private fun setFilterKey(filterElement: WebElement, filterText: String) {
        clickOn(filterElement);
        val filtersDropdownMenuContent = getFiltersDropdownContent();
        val filterDropdownElement = findElementByText(filtersDropdownMenuContent, filterText);
        clickOn(filterDropdownElement);
    }

    private fun setFilterValues(filterElement: WebElement, filterValueTexts: List<String> ) {
        clickOn(filterElement);
        val filtersDropdownMenuContent = getFiltersCheckboxesContent();
        println(filtersDropdownMenuContent.map { it.tagName });
        filterValueTexts.forEach{text ->
            val span = findElementByText(filtersDropdownMenuContent, text);
            val label = getParentOf(span);
            println("SPAN -- ${span.text}");
            println("TEXT -- ${text}");
            val input = label.findElement(By.tagName("input"));

            println("INPUT -- ${input.tagName}")

            clickOn(input);
            randomWait();
        }
        clickOn(filterElement);
    }

    private fun getFiltersDropdownContent(): List<WebElement> {
        val dropdownMenuContent = driver.findElements(By.cssSelector(".ui-selectmenu-menu-dropdown > li"));

        return dropdownMenuContent;
    }

    private fun getFiltersCheckboxesContent(): List<WebElement> {
        val dropdownCheckboxesContent = driver.findElements(By.cssSelector(".ui-multiselect-checkboxes > li"));

        return dropdownCheckboxesContent;
    }

    private fun findElementByText(elementList: List<WebElement>, text: String): WebElement {
        var element = elementList.find { el ->
            el.text.trim() == text
        }

        if (element === null) {
            println(elementList);
            elementList.forEach { el ->
                element = el.findElement(By.xpath("//*[text()='${text}']"))
            }
        }

        if (element == null) {
            throw Error("No element with text $text found")
        }

        return element as WebElement;
    }

    private fun goToNextKeywordsPage() {
        val keywordsContainer = driver.findElement(By.cssSelector(".slick-viewport"));

        js.executeScript("arguments[0].scrollTo(0, 1200)", keywordsContainer);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(loadingSpinnerSelector)));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(loadingSpinnerSelector)));
    }

    private fun collectKeywords() {
        val keywordsElements = driver.findElements(By.className("main-column"));
        val keywords = keywordsElements.map { keyword ->
            keyword.text
        };

        println(keywords);
    }

    private fun clickOn(el: WebElement) {
        js.executeScript(
            "arguments[0].click();" +
                "arguments[0].dispatchEvent(new MouseEvent('mousedown'));" +
                "arguments[0].dispatchEvent(new MouseEvent('mouseup'));",
            el);
    }

    private fun getParentOf(el: WebElement): WebElement {
        return js.executeScript("return arguments[0].parentElement", el) as WebElement;
    }

    private fun randomWait() {
        val randomMs = Random.nextLong(50, 300);
        Thread.sleep(randomMs);
    }

    private fun getDriverOptions(): ChromeOptions {
        val chromeOptions = ChromeOptions();
        val userAgents = listOf(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.1234.56 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.1234.56 Safari/537.36",
        );

        val randomIndex = Random.nextInt(userAgents.size)
        chromeOptions.addArguments("--user-agent=${userAgents[randomIndex]}");

        println(userAgents[randomIndex])

        return chromeOptions;
    }
}