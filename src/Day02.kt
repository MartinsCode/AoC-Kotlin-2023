import kotlin.system.measureTimeMillis

fun getGameNumber(game: String): Int {
    val parts = game.split(" ")
    return parts[1].replace(":", "").toInt()
}

fun isPossibleGame(game: String): Boolean {
    // get max numbers of each game
    val parts = game.split(":", ",", ";")  //  no need to distinguish between draws
    // part[0] is "Game 12:"
    var maxgreen = 0
    var maxblue = 0
    var maxred = 0
    parts.forEach {
        if (! it.startsWith("Game")) {
            val cubes = it.split(" ")
            val number = cubes[1].toInt()
            if (cubes[2].startsWith("green") && number > maxgreen) {
                maxgreen = number
            }
            if (cubes[2].startsWith("blue") && number > maxblue) {
                maxblue = number
            }
            if (cubes[2].startsWith("red") && number > maxred) {
                maxred = number
            }
        }
    }
    // then compare to max cubes at all
    // then return if possible
    return (maxred <= 12 && maxblue <= 14 && maxgreen <= 13)
}

fun powerOfGame(game: String): Int {
    val parts = game.split(":", ",", ";")  //  no need to distinguish between draws
    // part[0] is "Game 12:"
    var maxgreen = 0
    var maxblue = 0
    var maxred = 0
    parts.forEach {
        if (! it.startsWith("Game")) {
            val cubes = it.split(" ")
            val number = cubes[1].toInt()
            if (cubes[2].startsWith("green") && number > maxgreen) {
                maxgreen = number
            }
            if (cubes[2].startsWith("blue") && number > maxblue) {
                maxblue = number
            }
            if (cubes[2].startsWith("red") && number > maxred) {
                maxred = number
            }
        }
    }
    return maxgreen * maxblue * maxred
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.filter { isPossibleGame(it) }.sumOf { getGameNumber(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { powerOfGame(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 8)

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
    check(testSolution2 == 2286)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}