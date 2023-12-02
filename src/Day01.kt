import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        val numMap = input.map{ s -> s.toCharArray().filter { it.isDigit() } }
        println(numMap)
        val coordMap = numMap.map { list -> (list[0].toString() + list.last()).toInt() }
        return coordMap.sum()
    }

    fun part2(input: List<String>): Int {
        val writtenMap = input.map { s -> s
            .replace("one", "one1one")
            .replace("two", "two2two")
            .replace("three", "three3three")
            .replace("four", "four4four")
            .replace("five", "five5five")
            .replace("six", "six6six")
            .replace("seven", "seven7seven")
            .replace("eight", "eight8eight")
            .replace("nine", "nine9nine")
        }
        return part1(writtenMap)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 142)

    val solution1: Int
    val time1 = measureTimeMillis {
        solution1 = part1(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution1", time1 / 1000.0))

    val testInput2 = readInput("Day01_test2")

    println("\nPart 2:\n=======")
    val testSolution2: Int
    val timeTest2 = measureTimeMillis {
        testSolution2 = part2(testInput2)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution2", timeTest2 / 1000.0))
    check(testSolution2 == 281)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}