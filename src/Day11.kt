import kotlin.math.abs

sealed class CelestialBody {
    data object Empty : CelestialBody()
    data class Galaxy(
        val x: Int,
        val y: Int,
        val index: Int,
    ) : CelestialBody()
}

class Universe(input: List<String>, expansionRate: Int) {
    private var starCounter = 1
    val galaxies: List<CelestialBody.Galaxy> = input
        .expandUniverse(expansionRate)

    private fun List<String>.expandUniverse(expansionRate: Int): List<CelestialBody.Galaxy> {
        val yExpansions = mutableListOf<Int>()
        val xExpansions = mutableListOf<Int>()
        this.forEachIndexed { index, row ->
            if (row.any { it == '#' }.not()) {
                yExpansions.add(index)
            }
        }
        for (columnIndex in 0..this[0].lastIndex) {
            val column = this.map { it[columnIndex] }
            if (column.any { it == '#' }.not()) {
                xExpansions.add(columnIndex)
            }
        }
        var parsed = this.parse()
        yExpansions.forEachIndexed { index, row ->
            parsed = parsed.map {
                if (it.y > (row + (expansionRate - 1) * index)) {
                    it.copy(x = it.x, y = it.y + (expansionRate - 1))
                } else it
            }
        }
        xExpansions.forEachIndexed { index, column ->
            parsed = parsed.map {
                if (it.x > column + (expansionRate - 1) * index) {
                    it.copy(x = it.x + (expansionRate - 1), y = it.y)
                } else it
            }
        }
        return parsed
    }

    private fun List<String>.parse(): List<CelestialBody.Galaxy> = mapIndexed { y, row ->
        row.mapIndexed { x, char ->
            when (char) {
                '#' -> CelestialBody.Galaxy(x, y, starCounter).also { starCounter++ }
                else -> CelestialBody.Empty
            }
        }
    }
        .map { it.filterIsInstance<CelestialBody.Galaxy>() }
        .flatten()

    override fun toString(): String = galaxies.asMap()

    private fun List<CelestialBody.Galaxy>.asMap(): String {
        val width = map { it.x }.max()
        val height = map { it.y }.max()
        var result = ""
        for (y in 0..height) {
            for (x in 0..width) {
                result += if (any { it.x == x && it.y == y }) '#' else '.'
            }
            result += "\n"
        }
        return result
    }
}

fun main() {

    fun sumOfLengthsOfShorestPaths(universe: Universe): Long {
        val pairs = universe.galaxies.map { galaxy ->
            universe.galaxies.map { galaxy to it }
        }
            .flatten()
            .filter { it.first != it.second }
            .distinctBy { (first, second) ->
                if (first.index < second.index) {
                    first.index to second.index
                } else {
                    second.index to first.index
                }
            }
        val distances = pairs.map { (first, second) ->
            //Manhattan distance
            abs(first.x - second.x) + abs(first.y - second.y)
        }
        return distances.sumOf { it.toLong() }
    }

    fun part1(input: List<String>, expansionRate: Int): Long {
        val universe = Universe(input, expansionRate = expansionRate)
        return sumOfLengthsOfShorestPaths(universe)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput, expansionRate = 2), 374L)
    check(part1(testInput, expansionRate = 10), 1030L)
    check(part1(testInput, expansionRate = 100), 8410L)

    val input = readInput("Day11")
    part1(input, 2).println()
    part1(input, 1_000_000).println()
}
