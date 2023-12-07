fun main() {

    fun parsePart1(linesFromInputFile: List<String>): List<List<Long>> {
        return linesFromInputFile.map { it.split(":").last().split(" ").filterNot { it.isEmpty() }.map { it.toLong() } }
    }

    fun parsePart2(linesFromInputFile: List<String>): List<Long> {
        return linesFromInputFile
                .map {
                    it
                            .split(":")
                            .last()
                            .replace(" ", "")
                            .toLong()
                }
    }


    fun solve(parsed: List<List<Long>>): Long {
        var solution = 0L
        val races = parsed[0].size
        for (race in 0 until races) {
            var solutionCounter = 0L
            for (time in 0 until parsed[0][race]) {
                val distanceTravelled = time * (parsed[0][race] - time)
                if (distanceTravelled > parsed[1][race]) {
                    solutionCounter++
                }
            }
            if (solution == 0L) {
                solution = solutionCounter
            } else {
                solution *= solutionCounter
            }
        }
        return solution
    }

    fun part1(linesFromInputFile: List<String>): Long {
        val parsed = parsePart1(linesFromInputFile)
        return solve(parsed)
    }

    fun part2(linesFromInputFile: List<String>): Long {
        val parsed = parsePart2(linesFromInputFile)
        return solve(listOf(listOf(parsed[0]), listOf(parsed[1])))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}