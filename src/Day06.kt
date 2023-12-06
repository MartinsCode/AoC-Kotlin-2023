import kotlin.system.measureTimeMillis

fun distance (holdTime: Int, totalTime: Int): Int {
    return (totalTime - holdTime) * holdTime
}

fun distanceLong (holdTime: Int, totalTime: Int): Long {
    return (totalTime.toLong() - holdTime.toLong()) * holdTime.toLong()
}
data class RaceRecord (val totalTime: Int, val recordDistance: Int)

fun getRecords(input: List<String>): List<RaceRecord> {
    val times = input[0].split(" ").filter { it != "" }.drop(1).map { it.toInt() }
    val distances = input[1].split(" ").filter { it != "" }.drop(1).map { it.toInt() }
    val records = mutableListOf<RaceRecord>()
    times.forEachIndexed { index, s ->
        records.add(RaceRecord(s, distances[index]))
    }
    return records
}

fun main() {
    fun part1(input: List<String>): Int {
        val records = getRecords(input)
        val possibilities = mutableListOf<Int>()
        records.forEach { record ->
            var countFurther = 0
            (0..record.totalTime).forEach {
                if (distance(it, record.totalTime) > record.recordDistance)
                    countFurther++
            }
            possibilities.add(countFurther)
        }
        var errorMargin = 1
        possibilities.forEach{
            errorMargin *= it
        }
        return errorMargin
    }

    fun part2(input: List<String>): Int {
        val recordTime = input[0].toCharArray().filter { it.isDigit() }.joinToString("", "", "").toInt()
        val recordDistance = input[1].toCharArray().filter { it.isDigit() }.joinToString("", "", "").toLong()
        println("$recordTime $recordDistance")
        var countFurther = 0
        (0..recordTime).forEach {
            if (distanceLong(it, recordTime) > recordDistance)
                countFurther++
        }
        return countFurther
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 288)

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
    check(testSolution2 == 71503)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}