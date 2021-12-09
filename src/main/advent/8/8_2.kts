import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

val outputLines = File("data.txt").readLines().map { it.split(" | ").map { it.split(" ") } }
var totalValue = 0
for ((patterns, output) in outputLines) {
    val number1 = patterns.first { it.length == 2 }.toSet()
    val number4 = patterns.first { it.length == 4 }.toSet()
    val number2 = patterns.filter { it.length == 5 && (it.toSet() intersect number4).count() == 2 }.first().toSet()
    val number9 = patterns.filter { it.length == 6 && (it.toSet() intersect number4).count() == 4 }.first().toSet()

    val numbersMap = mapOf(
        1 to number1,
        2 to number2,
        4 to number4,
        7 to patterns.first { it.length == 3 }.toSet(),
        8 to patterns.first { it.length == 7 }.toSet(),
        3 to patterns.filter { it.length == 5 && (it.toSet() intersect number2).count() == 4 }.first().toSet(),
        5 to patterns.filter { it.length == 5 && (it.toSet() intersect number2).count() == 3 }.first().toSet(),
        6 to patterns.filter { it.length == 6 }.map{ it.toSet() }.filter { it != number9 }.filter { (it intersect number1).count() == 1 }.first(),
        9 to number9,
        0 to patterns.filter { it.length == 6 }.map{ it.toSet() }.filter { it != number9 }.filter { (it intersect number1).count() == 2 }.first(),
    )

    fun decode(value: Set<Char>): Int {
        for ((number, chars) in numbersMap) {
            if (chars == value) {
                return number
            }
        }
        return -1
    }

    totalValue += output.map { it.toSet() }.map {  decode(it) }.joinToString("").toInt()
}
println("Total value: $totalValue")