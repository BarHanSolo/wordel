package com.wordel.br

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File
import java.util.*

@SpringBootApplication
class WordlApplication

fun main(args: Array<String>) {
	runApplication<WordlApplication>(*args)
	//val wordAndLine = getRandomWordAndLine()
	//println("Wylosowane słowo: ${wordAndLine.first}, numer linii: ${wordAndLine.second}")
	val guessResult = checkGuess("MOKRE", 3385)
	println("Wynik zgadywania: $guessResult")
	val guessResult2 = checkGuess("LUBIĆ", 3385)
	println("Wynik zgadywania: $guessResult2")
	val guessResult3 = checkGuess("MPPRR", 3385)
	println("Wynik zgadywania: $guessResult3")
}

fun getRandomWordAndLine(): Pair<String, Int> {
	val filename = "pl" // nazwa pliku z listą słów
	val file = File(object {}.javaClass.getResource("/static/$filename").toURI())
	val lines = file.readLines()

	// Losowanie numeru linii
	val random = Random()
	val lineNumber = random.nextInt(lines.size) + 1 // numer linii zaczyna się od 1

	// Wybór losowego słowa z danej linii
	val line = lines[lineNumber - 1]
	val words = line.split("\\s+".toRegex()) // podział linii na słowa
	val randomWord = words[random.nextInt(words.size)]

	return Pair(randomWord, lineNumber)
}

fun checkGuess(guessWord: String, lineNumber: Int): String {
	val filename = "pl" // nazwa pliku z listą słów
	val file = File(object {}.javaClass.getResource("/static/$filename").toURI())
	val lines = file.readLines()

	// Sprawdzenie czy numer linii jest w granicach pliku
	if (lineNumber <= 0 || lineNumber > lines.size) {
		println("Błąd: Numer linii jest poza zakresem dostępnych linii w pliku.")
		return "Błąd"
	}

	// Sprawdzenie czy zgadywane słowo występuje dokładnie w danej linii
	val targetLine = lines[lineNumber - 1]
	val wordsOnLine = targetLine.split("\\s+".toRegex()) // podział linii na słowa
	if (wordsOnLine.contains(guessWord)) {
		return "Dobrze"
	}

	// Sprawdzenie czy zgadywane słowo występuje gdzie indziej w pliku
	for (line in lines) {
		val wordsInLine = line.split("\\s+".toRegex())
		if (wordsInLine.contains(guessWord)) {
			return "Prawie dobrze"
		}
	}

	// Jeśli zgadywane słowo nie istnieje w pliku
	return "Źle"
}