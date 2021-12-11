import java.io.File
import java.io.BufferedReader

val matchingLastOpen = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

var points = 0
for (line in File("data.txt").readLines()) {
    val openStack = mutableListOf<Char>()
    for (char in line) {
        when (char) {
            in charArrayOf('(', '[', '{', '<') -> openStack.add(char)
            in charArrayOf(')', ']', '}', '>') -> {
                val lastOpen = openStack.removeLast()
                if (lastOpen != matchingLastOpen[char]) {
                    when(char) {
                        ')' -> points += 3
                        ']' -> points += 57
                        '}' -> points += 1197
                        '>' -> points += 25137
                    }
                    break
                }
            }
        }
    }
}
println(points)