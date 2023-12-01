fun main() {
    fun part1(input: List<String>): Int {
        return input
            .sumOf { line ->
                val digits = line.chars()
                    .mapToObj { Char(it) }
                    .filter { (it.isDigit()) }
                    .toList()
                (digits.first().toString() + digits.last().toString()).toInt()
            }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val indexMap = mutableMapOf<Int, String>()
            digitMap.values.forEach { digit ->
                indexMap[line.indexOf(digit)] = digit
                indexMap[line.lastIndexOf(digit)] = digit
            }
            digitMap.keys.forEach { digit ->
                indexMap[line.indexOf(digit)] = digitMap[digit]!!
                indexMap[line.lastIndexOf(digit)] = digitMap[digit]!!
            }
            indexMap.remove(-1)
            (indexMap[indexMap.keys.min()] + indexMap[indexMap.keys.max()]).toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    // test if implementation meets criteria from the description, like:
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

val digitMap = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)
