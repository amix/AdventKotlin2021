import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

val crabPositions: List<Int> = File("data.txt").readLines().first().split(",").map { it.toInt() }

val minValue = crabPositions.minOrNull()
val maxValue = crabPositions.maxOrNull()

if (minValue != null && maxValue != null) {
    val fuelCosts = IntArray(maxValue)
    for (position in minValue..maxValue - 1) {
        crabPositions.map { abs(it - position) }.sum().also {
            fuelCosts[position] = it
        }
    }
    print(fuelCosts.minOrNull())
}