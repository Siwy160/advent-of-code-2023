data class Seeds(val seeds: List<Long>)

data class ConversionMap(val sourceRange: LongRange, val destinationRange: LongRange)

fun main() {


    fun parse(linesFromInputFile: List<String>): Pair<Seeds, MutableMap<Int, MutableList<ConversionMap>>> {
        var currentStep = 0
        var seeds: Seeds? = null
        val conversionMap: MutableMap<Int, MutableList<ConversionMap>> = mutableMapOf()
        linesFromInputFile.forEach {
            if (it.isEmpty()) {
                currentStep++
                return@forEach
            }
            if (it.contains("-")) return@forEach
            when (currentStep) {
                0 -> {
                    val seedNumbers = it.removePrefix("seeds: ").split(" ").map { it.toLong() }
                    seeds = Seeds(seedNumbers)
                }

                else -> {
                    val mapNumbers = it.split(" ").map { it.toLong() }
                    val convertedRanges = ConversionMap(
                            destinationRange = mapNumbers[0] until (mapNumbers[0] + mapNumbers[2]),
                            sourceRange = mapNumbers[1] until (mapNumbers[1] + mapNumbers[2])
                    )
                    val conversionList = conversionMap.getOrDefault(currentStep, mutableListOf())
                    conversionList.add(convertedRanges)
                    conversionMap[currentStep] = conversionList
                }
            }
        }
        val pair: Pair<Seeds, MutableMap<Int, MutableList<ConversionMap>>> = seeds!! to conversionMap
        return pair
    }


    fun part1(linesFromInputFile: List<String>): Long {
        val pair = parse(linesFromInputFile)
        val ranges = pair.second
        return pair.first.seeds.map { seed ->
            var result = seed
            ranges.forEach {
                val range = it.value.firstOrNull { it.sourceRange.contains(result) } ?: return@forEach //52 50 48
                result = result - (range.sourceRange.first - range.destinationRange.first)
                println()
            }
            result
        }
                .min()

    }

    fun part2(linesFromInputFile: List<String>): Long {
        val pair = parse(linesFromInputFile)
        val ranges = pair.second
        var lowest = Long.MAX_VALUE
        var pairs = pair.first.seeds.chunked(2)
        val chunkCount = pairs.size
        var currentChunk = 0
        for (pair in pairs) {

            val seeds = (pair.first()until pair.first()+pair.last())
            for (seed in seeds){
                var result = seed
                ranges.forEach {
                    val range = it.value.firstOrNull { it.sourceRange.contains(result) } ?: return@forEach //52 50 48
                    result = result - (range.sourceRange.first - range.destinationRange.first)

                }
                if (result<lowest){
                    lowest = result
                }
            }

            currentChunk++
            println(currentChunk.toFloat()/chunkCount.toFloat())
        }


        return lowest
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
