import kotlin.math.min
import kotlin.system.measureTimeMillis

data class Mapping(val fromItem: String,
                   val toItem: String,
                   val fromNum: Long,
                   val toNum: Long,
                   val range: Long)

val mappings = mutableListOf<Mapping>()
val seeds = mutableListOf<Long>()

fun initMappings(input: List<String>) {
    mappings.clear()
    seeds.clear()
    var source = ""
    var dest = ""
    input.forEach {
        if (it.startsWith("seeds:")) {
            // parse seeds
            seeds.addAll(it.split(" ").drop(1).map { it.toLong() })
        } else {
            if (it.endsWith("map:")) {
                // parse header of section
                val mapText = it.split(" ")[0]
                val arr = mapText.split("-to-")
                println(arr)
                source = arr[0]
                dest = arr[1]
            } else {
                if (it != "") {
                    // parse mapping-entry
                    val arr = it.split(" ").map { it.toLong() }
                    mappings.add(Mapping(source, dest, arr[1], arr[0], arr[2]))
                }
            }
        }
    }
}

fun getDest(source:String): String {
    val res = mappings.findLast { it.fromItem == source }!!
    return res.toItem
}

fun lookup(source: String, from: Long): Long {
    // print("From $source $from ... to ")
    val res = mappings.findLast { it.fromItem == source && from in it.fromNum until it.fromNum+it.range }
    // println(res)
    val retval = if (res == null) {
        from
    } else {
        from - res.fromNum + res.toNum
    }
    // println(retval)
    return retval
}

fun getLocation(seed: Long): Long {
    // println("*** Seed: $seed")
    var source = "seed"
    var dest = ""
    var fromLong = seed
    while (dest != "location") {
//         println ("converting from $dest")
        dest = getDest(source)
        fromLong = lookup(source, fromLong)
        source = dest
    }
    // println("corresponds to $fromLong")
    return fromLong
}

data class ThingRange(val start: Long, val range: Long)

val seedRangeList = mutableListOf<ThingRange>()

fun addSeeds(input: String) {
    val seedsString = input.split("eeds: ") [1]
    val seedList = seedsString.split(" ").map { it.toLong() }
    (0..seedList.lastIndex step 2).forEach{ index ->
        val range = seedList[index + 1]
        val start = seedList[index]
        println("Doing $start for $range")
        seedRangeList.add(ThingRange(start, range))
    }
    println("Done adding seeds")
}

fun getMapping(source: String, result: Pair<String, ThingRange>): Mapping {
    val transList = mappings.filter { it.fromItem == source && it.fromNum <= result.second.start && it.fromNum + it.range > result.second.start }
    val trans: Mapping
    if (transList.isNotEmpty()) {
        trans = transList.first()
    } else {
        // create a temp mapping to self (numbers from act. source to min start of next mapping
        var nextMapping = mappings.filter { it.fromItem == source && it.fromNum > result.second.start }.map { it.fromNum }.minOrNull()
        if (nextMapping == null)
            nextMapping = Long.MAX_VALUE
        trans = Mapping(fromItem = source,
            toItem = "selfmade",
            fromNum = result.second.start,
            toNum = result.second.start,
            range = nextMapping - result.second.start)
    }
    println("using mapping $trans")
    return trans
}

fun getLocations(list: List<ThingRange>): List<ThingRange> {
    var source = "seed"
    var dest = ""
    val resultList = mutableListOf<Pair<String, ThingRange>>()
    list.forEach { resultList.add(Pair(source, it)) }
    while (dest != "location") {
        dest = getDest(source)
        resultList.filter { it.first == source }.forEach { result: Pair<String, ThingRange> ->
            println("Doing the mapping of ... $result")
            var trans = getMapping(source, result)
            if (result.second.start + result.second.range < trans.fromNum + trans.range) {
                // all is fine: Transition is all in range
                resultList.add(Pair(dest, ThingRange(
                    result.second.start - trans.fromNum + trans.toNum,
                    result.second.range)))
            } else {
                // We have to split the range to transition
                var start = result.second.start
                var range = result.second.range
                while (range > 0) {
                    // transition of first part
                    val lastNum = min(start + range - 1, trans.fromNum + trans.range - 1)
                    val realRange = lastNum - start + 1
                    val transition = Pair(dest, ThingRange(start - trans.fromNum + trans.toNum, realRange))
                    // then reduce actual range by already done first part
                    start += transition.second.range
                    range -= transition.second.range
                    resultList.add(transition)
                    trans = getMapping(source, Pair(result.first, ThingRange(start, range)))
                }
            }
        }
        source = dest
    }
    println(resultList.filter { it.first == "location" })
    return resultList.filter { it.first == "location" }.map { it.second }
}


fun main() {
    fun part1(input: List<String>): Long {
        initMappings(input)
        return seeds.minOf { getLocation(it) }
    }

    fun part2(input: List<String>): Long {
        initMappings(input)
        addSeeds(input[0])
        // addSeeds("seeds: 82 1")
        val locations = getLocations(seedRangeList)

        return locations.map { it.start }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    println("Part 1:\n=======")
    val testSolution1: Long
    val timeTest1 = measureTimeMillis {
        testSolution1 = part1(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution1", timeTest1 / 1000.0))
    check(testSolution1 == 35L)

    val solution1: Long
    val time1 = measureTimeMillis {
        solution1 = part1(input)
    }
    println(String.format("Solution: %10.3fs  --  $solution1", time1 / 1000.0))


    println("\nPart 2:\n=======")
    val testSolution2: Long
    val timeTest2 = measureTimeMillis {
        testSolution2 = part2(testInput)
    }
    println(String.format("Test:     %10.3fs  --  $testSolution2", timeTest2 / 1000.0))
    check(testSolution2 == 46L)

    val solution2: Long
    val time2 = measureTimeMillis {
        solution2 = part2(input)
    }
    check(solution2 < 3851260974L)
    println(String.format("Solution: %10.3fs  --  $solution2", time2 / 1000.0))
}