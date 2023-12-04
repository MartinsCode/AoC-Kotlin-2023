import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun matchCount (line: String): Int {
    val importantParts = line.split(": ")[1].split(" | ")
    val winningText = importantParts[0]
    val myText = importantParts[1]
    val winningNumbers = winningText.split(" ").filter { it != "" }.map { it.toInt() }
    val myNumbers = myText.split(" ").filter { it != "" }.map { it.toInt() }
    val winCount = myNumbers.count { it in winningNumbers }
    return winCount
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { 2.0.pow(matchCount(it) - 1).toInt() }
    }

    fun part2(input: List<String>): Int {
        val cardWorth = mutableMapOf<Int, Int>()
        val cardPile = mutableMapOf<Int, Int>()
        // Init cardWorth
        input.forEach {
            val cardNo = it.split(" ", ":").filter { it != "" }[1].toInt()
            cardWorth[cardNo] = matchCount(it)
            cardPile[cardNo] = 1
        }
        println(cardPile)
        println(cardWorth)
        // now process
        val cards = cardPile.keys.size
        (1..cards).forEach { actCard ->
            val noCopies = cardPile[actCard]!!
            val noFurther = cardWorth[actCard]!!
            (1 .. noFurther).forEach {
                cardPile[actCard + it] = cardPile[actCard + it]!! + noCopies
            }
        }
        println(cardPile)
        return cardPile.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 13)

    val solution1: Int
    val time1 = measureTimeMillis {
        solution1 = part1(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution1", time1 / 1000.0))


    println("\nPart 2:\n=======")
    val testSolution2: Int
    val timeTest2 = measureTimeMillis {
        testSolution2 = part2(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution2", timeTest2 / 1000.0))
    check(testSolution2 == 30)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}