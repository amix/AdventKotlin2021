import java.io.File
import java.io.BufferedReader

val heights = File("data.txt").readLines().map { it.split("").filter { it.length > 0 }.map { it.toInt() } }

if (heights.size > 0) {
    val lowPoints = mutableListOf<IntArray>()
    for(rowIndex in heights.indices) {
        val row = heights[rowIndex]
        for(columnIndex in row.indices) {
            val currentHeight = row[columnIndex]

            val adjacentTop = heights.getOrNull(rowIndex - 1)?.getOrNull(columnIndex)
            val adjacentRight = heights[rowIndex].getOrNull(columnIndex + 1)
            val adjacentBottom = heights.getOrNull(rowIndex + 1)?.getOrNull(columnIndex)
            val adjacentLeft = heights[rowIndex].getOrNull(columnIndex - 1)

            var isLowPoint = true
            if (adjacentTop != null && adjacentTop <= currentHeight) {
                isLowPoint = false
            }
            if (adjacentRight != null && adjacentRight <= currentHeight) {
                isLowPoint = false
            }
            if (adjacentBottom != null && adjacentBottom <= currentHeight) {
                isLowPoint = false
            }
            if (adjacentLeft != null && adjacentLeft <= currentHeight) {
                isLowPoint = false
            }
            if (isLowPoint) {
                lowPoints.add(intArrayOf(rowIndex, columnIndex, currentHeight))
            }
        }
    }

    val riskLevel = lowPoints.sumOf { 1 + it[2] }
    println(riskLevel)
}

