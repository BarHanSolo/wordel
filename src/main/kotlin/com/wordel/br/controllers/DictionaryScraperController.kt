package com.wordel.br.controllers

import com.wordel.br.dto.dictionary_scraper.ScrapeDictionaryPostDTO
import com.wordel.br.scrapers.implementations.PLSgjpDictionaryScraper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DictionaryScraperController {
    @PostMapping("/scrape-dictionary")
    fun scrapeDictionary(@RequestBody body: ScrapeDictionaryPostDTO): ScrapeDictionaryPostDTO {
        val scraper = PLSgjpDictionaryScraper();

        scraper.start()

        return body
    }
}