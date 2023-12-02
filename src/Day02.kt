fun main() {
    fun part1(input: List<String>): Int {
        val maxPossibleSet = setOf(Red(12), Green(13), Blue(14))
        return input.map { Game(it) }
            .filter { it.isValid(maxPossibleSet) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { Game(it) }
            .sumOf { it.minimumPower() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)

    // test if implementation meets criteria from the description, like:
    val testInput2 = readInput("Day02_test2")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

class Game(input: String) {
    val id: Int
    private val subsets: List<Set<Cubes>>

    init {
        id = input.chars()
            .mapToObj { Char(it) }
            .dropWhile { !it.isDigit() }
            .takeWhile { it.isDigit() }
            .toArray()
            .joinToString("")
            .toInt()

        val game = input.split(": ")[1]
        subsets = game.split("; ")
            .map { it.toSubset() }
    }

    fun isValid(maxPossibleSet: Set<Cubes>): Boolean {
        return subsets.all { it.isValid(maxPossibleSet) }
    }

    fun minimumPower(): Int {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0

        subsets.forEach { subset ->
            val red = subset.find { it is Red }
            val green = subset.find { it is Green }
            val blue = subset.find { it is Blue }

            if (red != null && red.amount > minRed) minRed = red.amount
            if (green != null && green.amount > minGreen) minGreen = green.amount
            if (blue != null && blue.amount > minBlue) minBlue = blue.amount

        }
        return minRed * minGreen * minBlue
    }

}

sealed class Cubes(val amount: Int) : Comparable<Cubes?> {
    override fun compareTo(other: Cubes?): Int = this.amount - (other?.amount ?: 0)
}

class Red(amount: Int) : Cubes(amount)
class Green(amount: Int) : Cubes(amount)
class Blue(amount: Int) : Cubes(amount)

fun String.toSubset(): Set<Cubes> {
    return this.split(", ").map { cubeCount ->
        val countArray = cubeCount.split(" ")
        val count = countArray[0].toInt()
        val color = countArray[1]
        when (color) {
            "red" -> Red(count)
            "green" -> Green(count)
            "blue" -> Blue(count)
            else -> {
                throw IllegalArgumentException()
            }
        }
    }.toSet()
}

fun Set<Cubes>.isValid(maxPossibleSet: Set<Cubes>): Boolean {
    val maxRed = maxPossibleSet.find { it is Red }
    val maxGreen = maxPossibleSet.find { it is Green }
    val maxBlue = maxPossibleSet.find { it is Blue }

    val red = this.find { it is Red }
    val green = this.find { it is Green }
    val blue = this.find { it is Blue }

    if (red != null && red > maxRed) {
        return false
    }
    if (green != null && green > maxGreen) {
        return false
    }
    if (blue != null && blue > maxBlue) {
        return false
    }
    return true
}
