import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX_test")
    val input = readInput("DayXX")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 0)

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
    check(testSolution2 == 0)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}