fun main() {
    data class Reveal(val red: Int, val green: Int, val blue: Int)

    fun parse(linesFromInputFile: List<String>): List<List<Reveal>> {
        return linesFromInputFile.map { inputFileLine ->
            inputFileLine // Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                .split(": ").last() // 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                .split("; ")// 1 green, 3 red, 6 blue
                .map {
                    val colors = it.split(", ")  // 1 green
                    Reveal(
                        red = colors.firstOrNull { it.contains("red") }?.split(" ")?.first()?.toInt() ?: 0,
                        green = colors.firstOrNull { it.contains("green") }?.split(" ")?.first()?.toInt() ?: 0,
                        blue = colors.firstOrNull { it.contains("blue") }?.split(" ")?.first()?.toInt() ?: 0,
                    )
                }

        }
    }

    fun part1(linesFromInputFile: List<String>): Int {
        val redCubes = 12
        val greenCubes = 13
        val blueCubes = 14

        val result = parse(linesFromInputFile)
            .mapIndexed { index, reveals ->
                val gameId = index + 1
                val isInvalid = reveals.any {
                    it.red > redCubes || it.blue > blueCubes || it.green > greenCubes
                }
                if (isInvalid) 0 else gameId
            }
            .sum()
        return result
    }

    fun part2(linesFromInputFile: List<String>): Int {
        val result = parse(linesFromInputFile)
            .map { game ->
                val maxReds = game.map { it.red }.max()
                val maxBlue = game.map { it.blue }.max()
                val maxGreens = game.map { it.green }.max()

                maxReds * maxBlue * maxGreens
            }
        return result.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
