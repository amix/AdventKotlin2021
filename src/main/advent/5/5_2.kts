import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "($x, $y)"
}

data class Line(val start: Point, val end: Point) {
    fun pointOnLine(point: Point): Boolean {
        val dxc = point.x - start.x
        val dyc = point.y - start.y
        val dxl = end.x - start.x
        val dyl = end.y - start.y
        val cross = dxc * dyl - dyc * dxl
        return cross == 0
    }
    override fun toString(): String = "$start -> $end"
}

fun main() {
    val lines = mutableListOf<Line>()
    var maxX = 0
    var maxY = 0
    for (rawLine in File("data.txt").readLines()) {
        val pairs = rawLine.split("\\s+->\\s+".toRegex()).map { it.split(",").map { it.toInt() } }
        for ((start, end) in pairs.windowed(2)) {
            val line = Line(Point(start[0], start[1]), Point(end[0], end[1]))
            lines.add(line)
            maxX = maxOf(maxX, maxOf(line.start.x, line.end.x))
            maxY = maxOf(maxY, maxOf(line.start.y, line.end.y))
        }
    }

    val intersections = Array(maxX + 1) { Array(maxY + 1) { 0 } }
    for (line in lines) {
        val xRange = if (line.start.x > line.end.x) line.start.x downTo line.end.x else line.start.x..line.end.x
        val yRange = if (line.start.y > line.end.y) line.start.y downTo line.end.y else line.start.y..line.end.y

        val xDiff = abs(line.start.x - line.end.x)
        val yDiff = abs(line.start.y - line.end.y)

        if (line.start.x == line.end.x || line.start.y == line.end.y || xDiff == yDiff) {
            for(x in xRange) {
                for(y in yRange) {
                    if (x in xRange && y in yRange) {
                        if (line.pointOnLine(Point(x, y))) {
                            intersections[x][y] += 1
                        }
                    }
                }
            }
        }
    }

    var dangerousAreas = 0
    for (x in 0 .. maxX) {
        for( y in 0 .. maxY) {
            val value = intersections[y][x]
            // print(if (value > 0) value.toString() else '.')
            if (value > 1) {
                dangerousAreas++
            }
        }
        // println()
    }
    println("Dangerous Areas: $dangerousAreas" )
}

main()