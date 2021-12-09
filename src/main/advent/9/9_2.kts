import java.io.File
import java.io.BufferedReader

data class Position(val row: Int, val column: Int)

val heights = File("data.txt").readLines().map { it.split("").filter { it.length > 0 }.map { it.toInt() } }
val basinCache = mutableMapOf<Position, Set<Position>>()

fun getLowerValues(currentPosition: Position, directionFn: (position: Position) -> Position): Set<Position> {
    var curValue = heights[currentPosition.row][currentPosition.column]
    val position = directionFn(currentPosition)
    var value = heights.getOrNull(position.row)?.getOrNull(position.column)
    if (value != null && value != 9 && curValue < value) {
        return getBasin(position)
    }
    else {
        return emptySet<Position>()
    }
}

fun getBasin(position: Position): Set<Position> {
    basinCache[position]?.let { return it }
    val basin = mutableSetOf<Position>()
    val currentHeight = heights[position.row][position.column]
    if (currentHeight != 9) {
        basin.add(position)
        basin.addAll(getLowerValues(position, { p: Position -> Position(p.row - 1, p.column) }))
        basin.addAll(getLowerValues(position, { p: Position -> Position(p.row, p.column + 1) }))
        basin.addAll(getLowerValues(position, { p: Position -> Position(p.row + 1, p.column) }))
        basin.addAll(getLowerValues(position, { p: Position -> Position(p.row, p.column - 1) }))
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
    println(basins.subList(0, 3).map { it.size }.reduce { acc, i ->  acc * i})
}

