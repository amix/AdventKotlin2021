import java.io.File
import java.io.BufferedReader

val maxDay = 80
val initialTimer = 8
val subInitialTimer = 6

fun main() {
    val allFish: MutableList<Int> = File("data.txt").readLines().first().split(",").map { it.toInt() }.toMutableList()
    for(day in 0 .. maxDay) {
        if (day > 0) {
            val fishToAdd = mutableListOf<Int>()
            allFish.forEachIndexed { index, timer ->
                if (timer == 0) {
                    fishToAdd.add(initialTimer)
                    allFish[index] = subInitialTimer
                }
                else {
                    allFish[index] = timer - 1
                }
            }
            allFish.addAll(fishToAdd)
        }
        println("Day $day: " + allFish.size.toString())
    }
}

main()