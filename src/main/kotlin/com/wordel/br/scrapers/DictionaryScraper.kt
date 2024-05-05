package com.wordel.br.scrapers

import org.openqa.selenium.WebDriver

interface DictionaryScraper {
    val driver: WebDriver;
    val url: String;
    fun start() {}
}