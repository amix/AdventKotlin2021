import java.io.File

val maxDay = 256
val newFishTimer = 8
val subBirthTimer = 6
val fishCache = mutableMapOf<Fish, Long>()

data class Fish(val day: Int, val timer: Int)

fun calcFishGrowth(fish: Fish): Long {
    fishCache[fish]?.let { return it }

    var fishToAdd = 0L
    var timer = fish.timer
    for (day in fish.day..maxDay-1) {
        when(timer) {
            0 -> {
                fishToAdd += 1 + calcFishGrowth(Fish(day+1, newFishTimer))
                timer = subBirthTimer
            }
            else -> {
                timer -= 1
            }
        }
    }
    return fishToAdd.also {
        fishCache[fish] = it
    }
}

val initialSeed = File("data.txt").readLines().first().split(",").map { it.toInt() }
println(initialSeed.map { timer -> 1 + calcFishGrowth(Fish(0, timer)) }.sum())