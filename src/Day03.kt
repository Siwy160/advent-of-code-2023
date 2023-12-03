import java.lang.Exception

fun main() {

    fun List<String>.getChar(x: Int, y: Int): Char? {
        return try {
            this[y][x]
        } catch (e: Exception) {
            return null
        }
    }

    fun List<String>.getNumber(x: Int, y: Int): Int {
        val line = this[y]
        val leftSide = line.substring(0, x)
        val rightSize = line.substring(x, line.length)
        val start = leftSide.takeLastWhile { it.isDigit() }
        val end = rightSize.takeWhile { it.isDigit() }
        return (start + end).toInt()
    }

    fun part1(linesFromInputFile: List<String>): Int {
        var result = 0
        for (y in linesFromInputFile.indices) {
            for (x in linesFromInputFile[0].indices) {
                val char = linesFromInputFile.getChar(x, y)
                val isDot = char == '.'
                val isDigit = char?.isDigit() ?: false
                val isSymbol = isDot.not() && isDigit.not()

                if (isSymbol) {
                    val numbers: MutableSet<Int> = mutableSetOf() //Dobre/Złe założenie
                    for (yAround in -1..1) {
                        for (xAround in -1..1) {
                            val adjacentSymbol = linesFromInputFile.getChar(x + xAround, y + yAround)
                            if (adjacentSymbol?.isDigit() == true) {
                                val number = linesFromInputFile.getNumber(x + xAround, y + yAround)
                                numbers.add(number)
                            }
                        }
                    }
                    result += numbers.sum()
                }
            }
        }
        return result
    }

    fun part2(linesFromInputFile: List<String>): Int {
        var result = 0
        for (y in linesFromInputFile.indices) {
            for (x in linesFromInputFile[0].indices) {
                val char = linesFromInputFile.getChar(x, y)
                val isSymbol = char == '*'

                if (isSymbol) {
                    val numbers: MutableSet<Int> = mutableSetOf() //Dobre/Złe założenie
                    for (yAround in -1..1) {
                        for (xAround in -1..1) {
                            val adjacentSymbol = linesFromInputFile.getChar(x + xAround, y + yAround)
                            if (adjacentSymbol?.isDigit() == true) {
                                val number = linesFromInputFile.getNumber(x + xAround, y + yAround)
                                numbers.add(number)
                            }
                        }
                    }
                    if (numbers.size == 2) {
                        result += numbers.elementAt(0) * numbers.elementAt(1)
                    }
                }
            }
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
