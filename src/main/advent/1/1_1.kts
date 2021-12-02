import java.io.File
import java.io.BufferedReader

fun readData(fileName: String): List<Int> {
    val lines: List<String> = File(fileName).readLines()
    return lines.map { it.toInt() }
}

fun numberOfIncreases(measurements: List<Int>): Int {
    var lastMeasurement: Int? = null
    var increases = 0
    for(measurement in measurements) {
        if (lastMeasurement != null && measurement > lastMeasurement) {
            increases += 1
        }
        lastMeasurement = measurement
    }
    return increases
}

fun main() {
    print(numberOfIncreases(readData("data.txt")))
}

main()