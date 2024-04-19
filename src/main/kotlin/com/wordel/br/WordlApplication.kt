package com.wordel.br

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.util.*
import com.wordel.logic.*

@SpringBootApplication
class WordlApplication

fun main(args: Array<String>) {
	//runApplication<WordlApplication>(*args)
	//val wordAndLine = getRandomWordAndLine()
	//println("Wylosowane słowo: ${wordAndLine.first}, numer linii: ${wordAndLine.second}")
	val guessResult = checkGuess("MOKRE", 3385)
	println("Wynik zgadywania: $guessResult")
	val guessResult2 = checkGuess("LUBIĆ", 3385)
	println("Wynik zgadywania: $guessResult2")
	val guessResult3 = checkGuess("MPPRR", 3385)
	println("Wynik zgadywania: $guessResult3")
}