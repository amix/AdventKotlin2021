import java.io.File
import java.io.BufferedReader

data class Position(val row: Int, val column: Int)

val heights = File("data.txt").readLines().map { it.split("").filter { it.length > 0 }.map { it.toInt() } }
val basinCache = mutableMapOf<Position, Set<Position>>()

fun getBasin(position: Position): Set<Position> {
    basinCache[position]?.let { return it }
    val basin = mutableSetOf<Position>()
    val currentHeight = heights[position.row][position.column]
    basin.add(position)
    val adjPositions = sequenceOf(
        Position(position.row - 1, position.column),
        Position(position.row, position.column + 1),
        Position(position.row + 1, position.column),
        Position(position.row, position.column - 1)
    )
    for(adjPosition in adjPositions) {
        var adjHeight = heights.getOrNull(adjPosition.row)?.getOrNull(adjPosition.column)
        if (adjHeight != null && adjHeight != 9 && currentHeight < adjHeight) {
            basin.addAll(getBasin(adjPosition))
        }
    }
    return basin.also {
        basinCache[position] = it
    }
}

if (heights.size > 0) {
    val basins = mutableListOf<Set<Position>>()
    for (rowIndex in heights.indices) {
        val row = heights[rowIndex]
        for (columnIndex in row.indices) {
            basins.add(getBasin(Position(rowIndex, columnIndex)))
        }
    }

    basins.sortBy { -it.size }
    println(basins.take(3).map { it.size }.reduce(Int::times))
}

