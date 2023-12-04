fun main() {

    fun countCardMatches(scratchcard: Pair<List<Int>, List<Int>>): Int {
        val winning = scratchcard.first
        val scratched = scratchcard.second
        var won = 0
        winning.forEach {
            if (scratched.contains(it)) {
                won += 1
            }
        }
        return won
    }

    fun parse(linesFromInputFile: List<String>) = linesFromInputFile
            .map {
                it.split(": ")[1]
            }
            .map {
                val split = it.split("| ")
                split[0] to split[1] + " "
            }
            .map {
                val winning = it.first
                        .split(" ")
                        .filterNot { it.isEmpty() }
                        .map {
                            it.toInt()
                        }
                val scratched = it.second
                        .split(" ")
                        .filterNot { it.isEmpty() }
                        .map {
                            it.toInt()
                        }
                Pair(winning, scratched)
            }

    fun part1(linesFromInputFile: List<String>): Int {
        val result = parse(linesFromInputFile)
                .map {
                    val winning = it.first
                    val scratched = it.second
                    var won = 0
                    winning.forEach {
                        if (scratched.contains(it)) {
                            if (won == 0) won = 1 else won *= 2
                        }
                    }
                    won
                }
                .sum()
        return result
    }

    fun part2(linesFromInputFile: List<String>): Int {
        class Card(var instances: Int, val matches: Int)

        var result = 0

        val parsedScratchcards = parse(linesFromInputFile).map { Card(1, countCardMatches(it)) }
        val iterator = parsedScratchcards.listIterator()
        for (scratchcard in iterator) {
            val index = parsedScratchcards.indexOf(scratchcard)
                parsedScratchcards.subList(index + 1, index + scratchcard.matches + 1)
                        .forEach {
                            it.instances += scratchcard.instances
                        }
        }
        return parsedScratchcards.map { it.instances }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
