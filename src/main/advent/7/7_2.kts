import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

val crabPositions: List<Int> = File("data.txt").readLines().first().split(",").map { it.toInt() }

val minValue = crabPositions.minOrNull()
val maxValue = crabPositions.maxOrNull()

if (minValue != null && maxValue != null) {
    println((minValue..maxValue - 1).map {
        val position = it
        crabPositions.map {
            val cost = abs(it - position)
            (cost * (cost + 1)) / 2
        }.sum()
    }.minOrNull())
}