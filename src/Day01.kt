fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            val first = it.first { it.isDigit() }.digitToInt()
            val second = it.reversed().first { it.isDigit() }.digitToInt()
            first * 10 + second
        }.sum()
    }

    fun part2(input: List<String>): Int {

        val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

        fun processLine(input: String, reserved: Boolean): Int {
            fun String.startsWithAny(strings: List<String>, reserved: Boolean): String? = strings.firstOrNull {
                if (reserved.not()) {
                    this.startsWith(it)
                } else {
                    this.endsWith(it)
                }
            }

            fun String.first(reserved: Boolean): Char = if (reserved) last() else first()

            var line = input
            var first: Int = -1
            while (first == -1 && line.isNotEmpty()) {
                line.startsWithAny(digits, reserved)?.let {
                    first = digits.indexOf(it) + 1
                }
                if (line.first(reserved).isDigit()) {
                    first = line.first(reserved).digitToInt()
                }
                line = if (reserved.not()) {
                    line.substring(1..<line.length)
                } else {
                    line.substring(0..<line.length - 1)
                }
            }
            return first
        }

        return input.map {
            val first = processLine(it, false)
            val second = processLine(it, true)
            first * 10 + second
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
