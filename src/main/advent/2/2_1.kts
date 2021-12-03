import java.io.File
import java.io.BufferedReader

fun readData(fileName: String): List<String> {
    val lines: List<String> = File(fileName).readLines()
    return lines.map { it }
}

fun main() {
    var horizontal = 0
    var depth = 0
    for (line in readData("data.txt")) {
        val parts = line.split(" ")
        val command = parts[0]
        val value = parts[1].toInt()
        when (command) {
            "forward" -> horizontal += value
            "down" -> depth += value
            "up" -> depth -= value
        }
    }
    println("Horizontal position: " + horizontal)
    println("Depth: " + depth)
    println("Horizontal * Depth: " + (horizontal*depth))
}

main()