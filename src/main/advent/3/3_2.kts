import java.io.File
import java.io.BufferedReader

fun filterReadings(lines: List<CharArray>, keepMajority: Boolean): CharArray? {
    val numberOfBits = lines[0].size
    val filteredLines = lines.toMutableList()
    for(bit in 0..numberOfBits-1) {
        var onCount = filteredLines.count { it[bit] == '1' }
        var offCount = filteredLines.size - onCount

        var majorityBit = if (onCount > offCount) { '1' }
        else if (onCount < offCount) { '0' }
        else '1'

        if (keepMajority) {
            filteredLines.removeAll({ it[bit] != majorityBit })
        }
        else {
            filteredLines.removeAll({ it[bit] == majorityBit })
        }
        if (filteredLines.size == 1) {
            return filteredLines[0]
        }
    }
    return null
}

fun main() {
    val lines = File("data.txt").readLines().map { it.toCharArray() }
    val oxygenGenRating = filterReadings(lines, true)
    val co2ScrubberRating = filterReadings(lines, false)
    if (oxygenGenRating != null && co2ScrubberRating != null) {
        val oxygenGenRatingNumber = Integer.parseInt(oxygenGenRating.joinToString(""), 2)
        val co2ScrubberRatingNumber = Integer.parseInt(co2ScrubberRating.joinToString(""), 2)
        println("oxygen: " + oxygenGenRatingNumber)
        println("co2: " + co2ScrubberRatingNumber)
        println("oxygen * co2: " + (oxygenGenRatingNumber * co2ScrubberRatingNumber))
    }
}

main()