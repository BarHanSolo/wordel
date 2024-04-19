package com.wordel.logic

import java.io.File
import java.util.*

fun getRandomWordAndLine(): Pair<String, Int> {
    val filename = "pl" // name of the file with the list of words
    // opening the file and reading all lines
    val file = File(object {}.javaClass.getResource("/static/$filename").toURI())
    val lines = file.readLines()

    // get random line number
    val random = Random()
    val lineNumber = random.nextInt(lines.size) + 1

    // get word from that random linenumber
    val line = lines[lineNumber - 1]
    val words = line.split("\\s+".toRegex())
    val randomWord = words[random.nextInt(words.size)]

    return Pair(randomWord, lineNumber)
}

fun checkGuess(guessWord: String, lineNumber: Int): String {
    val filename = "pl" // name of the file with the list of words
    // opening the file and reading all lines
    val file = File(object {}.javaClass.getResource("/static/$filename").toURI())
    val lines = file.readLines()

    // Checking if the line number is within the file boundaries
    if (lineNumber <= 0 || lineNumber > lines.size) {
        println("Błąd: Numer linii jest poza zakresem dostępnych linii w pliku.")
        return "Error"
    }

    // Checking if the guessed word appears exactly on the specified line
    val targetLine = lines[lineNumber - 1]
    val wordsOnLine = targetLine.split("\\s+".toRegex())
    if (wordsOnLine.contains(guessWord)) {
        return "Correct"
    }

    // Checking if the guessed word appears elsewhere in the dictionary
    for (line in lines) {
        val wordsInLine = line.split("\\s+".toRegex())
        if (wordsInLine.contains(guessWord)) {
            return "Almost correct"
        }
    }

    // If the guessed word does not exist in the file
    return "Wrong"
}