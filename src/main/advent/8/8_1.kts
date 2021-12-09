import java.io.File
import java.io.BufferedReader
import kotlin.math.abs

val outputLines = File("data.txt").readLines().map { it.split(" | ").map { it.split(" ") } }
var number1 = 0
var number4 = 0
var number7 = 0
var number8 = 0
for ((_, output) in outputLines) {
    number1 += output.count { it.length == 2 }
    number4 += output.count { it.length == 4 }
    number7 += output.count { it.length == 3 }
    number8 += output.count { it.length == 7 }
}
println("Total: " + (number1 + number4 + number7 + number8))