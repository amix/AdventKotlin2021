import java.io.File
import java.io.BufferedReader

val matchingLastOpen = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

var linePoints = mutableListOf<Long>()
for (line in File("data.txt").readLines()) {
    val openStack = mutableListOf<Char>()
    var corrupted = false
    for (char in line) {
        when (char) {
            in charArrayOf('(', '[', '{', '<') -> openStack.add(char)
            in charArrayOf(')', ']', '}', '>') -> {
                val lastOpen = openStack.removeLast()
                if (lastOpen != matchingLastOpen[char]) {
                    corrupted = true
                }
            }
        }
    }
    if(!corrupted) {
        var points = 0L
        for (missingChar in openStack.reversed()) {
            when (missingChar) {
                '(' -> points = (points * 5L) + 1L
                '[' -> points = (points * 5L) + 2L
                '{' -> points = (points * 5L) + 3L
                '<' -> points = (points * 5L) + 4L
            }
        }
        linePoints.add(points)
    }
}
linePoints.sortDescending()
println(linePoints[(linePoints.size/2)])