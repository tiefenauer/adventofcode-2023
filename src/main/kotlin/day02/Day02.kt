/**
 * --- Day 2: Cube Conundrum ---
 * You're launched high into the atmosphere! The apex of your trajectory just barely reaches the surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's quite cold, but you don't see much snow. An Elf runs over to greet you.
 *
 * The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't get many visitors up here; would you like to play a game in the meantime?
 *
 * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.
 *
 * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.
 *
 * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
 *
 * For example, the record of a few games might look like this:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
 *
 * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
 *
 * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
 *
 * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 */
package day02

import readLines

private const val LINE_PREFIX = "Game "
private const val MAX_RED = 12
private const val MAX_GREEN = 13
private const val MAX_BLUE = 14

private val sample = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent().split("\n")

fun main() {
    val lines = "day02.txt".readLines()
    val part1 = lines.part1()
    println(part1) // 2105
    val part2 = lines.part2()
    println(part2) // 72422
}

private fun List<String>.part1() = filter { it.isPossible() }.sumOf { it.id() }

private fun List<String>.part2() = sumOf { it.getPower() }

private fun String.isPossible(): Boolean {
    rounds().forEach {
        val (numberOfCubes, color) = it.getNumberOfCubesAndColor()
        if (color == "red" && numberOfCubes.toInt() > MAX_RED) {
            return false
        }
        if (color == "green" && numberOfCubes.toInt() > MAX_GREEN) {
            return false
        }
        if (color == "blue" && numberOfCubes.toInt() > MAX_BLUE) {
            return false
        }
    }
    return true
}

private fun String.getPower(): Int {
    val minimums = calculateMinimumsByColor()
    val minRed = minimums.getOrDefault("red", 0)
    val minGreen = minimums.getOrDefault("green", 0)
    val minBlue = minimums.getOrDefault("blue", 0)
    return minRed * minGreen * minBlue
}

private fun String.calculateMinimumsByColor() = rounds()
    .map {
        val (numberOfCubes, color) = it.getNumberOfCubesAndColor()
        color to numberOfCubes.toInt()
    }
    .groupBy { it.first }.mapValues { it.value.maxBy { it.second }.second }

private fun String.rounds() = games().flatMap { it.split(", ") }
private fun String.games() = getIdAndGames().last().split("; ")
private fun String.id() = getIdAndGames().first().toInt()
private fun String.getIdAndGames() = drop(LINE_PREFIX.length).split(": ")
private fun String.getNumberOfCubesAndColor() = split(" ")

