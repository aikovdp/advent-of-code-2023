fun main() {
    fun part1(input: List<String>): Int {
        val races = parseInput1(input)
        return races.map { it.waysToBeat() }.reduce(Int::times)

    }

    fun part2(input: List<String>): Int {
        return parseInput2(input).waysToBeat()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day06_test")
    check(part1(testInput1) == 288)

    // test if implementation meets criteria from the description, like:
    val testInput2 = readInput("Day06_test")
    check(part2(testInput2) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

fun String.parseValues1(): List<Int> = this.substringAfter(':').trim().split("\\s+".toRegex()).map { it.toInt() }

fun parseInput1(input: List<String>): List<Race> {
    val times = input[0].parseValues1()
    val distances = input[1].parseValues1()
    return times.indices.map { Race(times[it], distances[it]) }
}

fun parseInput2(input: List<String>): Race {
    val time = input[0].substringAfter(':').replace(" ", "").toLong()
    val distance = input[1].substringAfter(':').replace(" ", "").toLong()
    return Race(time, distance)
}

data class Race(val allowedTime: Long, val recordDistance: Long) {
    constructor(allowedTime: Int, recordDistance: Int): this(allowedTime.toLong(), recordDistance.toLong())
    fun waysToBeat(): Int {
        var waysToBeat = 0
        for (timeHeld in 1 until allowedTime) {
            if (calculateDistanceForTimeHeld(timeHeld, allowedTime) > recordDistance) {
                waysToBeat++
            }
        }
        return waysToBeat
    }
}

fun calculateDistanceForTimeHeld(timeHeld: Long, allowedTime: Long): Long {
    val speed = timeHeld
    val timeToRace = allowedTime - timeHeld
    return speed * timeToRace
}
