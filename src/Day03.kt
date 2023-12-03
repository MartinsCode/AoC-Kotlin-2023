import kotlin.system.measureTimeMillis

/**
 * PartNumber
 * number: the number itself
 * x: which column (0..max) it begins
 * y: Which line (0..max) it begins
 * length: How many characters are used
 */
data class PartNumber (val number: Int, val x: Int, val y: Int, val length: Int)

fun getPartList(input: List<String>): List<PartNumber> {
    val listOfParts = mutableListOf<PartNumber>()
    input.forEachIndexed { lineNumber, line ->
        val numberSequence = Regex("(\\d+)").findAll(line)
        numberSequence.forEach {
            listOfParts.add(PartNumber(it.value.toInt(), it.range.first, lineNumber, it.value.length))
        }
    }
    return listOfParts
}

val symbols = mutableSetOf<Char>()

fun initSymbols(input: List<String>) {
    input.forEach {
        it.forEach {
            val re = Regex("[\\d\\\\.]")
            if (! re.matches(it.toString()))
                symbols.add(it)
        }
    }
}

fun isSymbolIn(input: List<String>, minX: Int, minY: Int, maxX: Int, maxY: Int): Boolean {
    var hasSymbol = false
    input.slice(minY..maxY).forEach {
        it.slice(minX..maxX).forEach {
            hasSymbol = hasSymbol || symbols.contains(it)
        }
    }
    return hasSymbol
}

fun hasSymbolAttached(part: PartNumber, input: List<String>): Boolean {
    // Just check if there's a symbol in area given by part
    var minX = part.x - 1
    var minY = part.y - 1
    var maxX = part.x + part.length  // no +1 since included already
    var maxY = part.y + 1
    if (minX < 0) minX = 0
    if (minY < 0) minY = 0
    if (maxX > input[0].length - 1) maxX = input[0].length - 1  // TODO: This is based on assumption that *ALL* lines have equal length
    if (maxY > input.size - 1) maxY = input.size - 1
    return (isSymbolIn(input, minX, minY, maxX, maxY))
}

fun getStarList(input: List<String>): List<Pair<Int, Int>> {
    val listOfStars = mutableListOf<Pair<Int, Int>>()
    input.forEachIndexed { lineNumber, line ->
        line.forEachIndexed { column, c ->
            if (c == '*')
                listOfStars.add(Pair(column, lineNumber))
        }
    }
    return listOfStars
}

fun isAdjacent(part: PartNumber, x: Int, y: Int): Boolean {
    return (x >= part.x - 1 && x <= part.x + part.length && y >= part.y - 1 && y <= part.y + 1)
}

fun findAllParts(partlist: List<PartNumber>, x: Int, y: Int): List<Int> {
    val adjacentParts = mutableListOf<Int>()
    partlist.forEach {
        if (isAdjacent(it, x, y))
            adjacentParts.add(it.number)
    }
    return adjacentParts
}

fun getGearList(partlist: List<PartNumber>, starlist: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val gearlist = mutableListOf<Pair<Int, Int>>()
    starlist.forEach {
        val potentialGears = findAllParts(partlist, it.first, it.second)
        if (potentialGears.size == 2) {
            gearlist.add(Pair(potentialGears[0], potentialGears[1]))
        }
    }
    return gearlist
}

fun main() {
    fun part1(input: List<String>): Int {
        initSymbols(input)
        val partlist = getPartList(input)
        return partlist.filter { hasSymbolAttached(it, input) }.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        symbols.clear()
        symbols.add('*')   // We now only need potentially gears
        val partlist = getPartList(input)
        val starList = getStarList(input)
        val gearList = getGearList(partlist, starList)
        return gearList.sumOf { it.first * it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    println("Part 1:\n=======")
    val testSolution1: Int
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 4361)

    val solution1: Int
    val time1 = measureTimeMillis {
        solution1 = part1(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution1", time1 / 1000.0))
    check (solution1 != 527116)

    println("\nPart 2:\n=======")
    val testSolution2: Int
    val timeTest2 = measureTimeMillis {
        testSolution2 = part2(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution2", timeTest2 / 1000.0))
    check(testSolution2 == 467835)

    val solution2: Int
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}