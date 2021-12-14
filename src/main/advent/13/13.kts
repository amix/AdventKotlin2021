import java.io.File

data class Fold(val direction: String, val value: Int)

typealias MapDots = Array<BooleanArray>

data class Map(val dots: MapDots, val folds: List<Fold>)

fun getInitialMap(): Map {
    val (coordinatesRaw, foldsRaw) = File("data.txt").readText().split("\n\n")

    val coors = coordinatesRaw.lines().map { it.split(",").map { it.toInt() } }
    val dots = Array(coors.maxOf { it[1] } + 1) { BooleanArray(coors.maxOf { it[0] } + 1) }
    for ((x, y) in coors) {
        dots[y][x] = true
    }

    val folds = mutableListOf<Fold>()
    for (fold in foldsRaw.lines().map { it.replace("fold along ", "") }) {
        val (direction, value) = fold.split("=")
        folds.add(Fold(direction, value.toInt()))
    }

    return Map(dots, folds)
}

fun fold(map: Map, onlyFirstFold: Boolean): MapDots {
    var dots = map.dots
    for (fold in map.folds) {
        when (fold.direction) {
            "y" -> {
                dots.mapIndexed { y, row ->
                    if (y < fold.value) {
                        row.mapIndexed { x, value ->
                            val foldY = (fold.value * 2) - y
                            dots[y][x] = dots[foldY][x] || value
                        }
                    }
                }
                dots = dots.copyOfRange(0, fold.value)
            }
            "x" -> {
                dots.mapIndexed { y, row ->
                    row.mapIndexed { x, value ->
                        if (x < fold.value) {
                            val foldX = (fold.value * 2) - x
                            dots[y][x] = dots[y][foldX] || value
                        }
                    }
                }
                dots = dots.map { it.copyOfRange(0, fold.value) }.toTypedArray()
            }
        }
        if (onlyFirstFold) {
            break
        }
    }
    return dots
}

fun part1(): Int {
    return fold(getInitialMap(), true).sumOf { it.count { it } }
}

fun part2() {
    val finalDots = fold(getInitialMap(), false)
    for (row in finalDots) {
        for (value in row) {
            when (value) {
                true -> print("#")
                false -> print(".")
            }
        }
        println("")
    }
}

println("Part 1: " + part1())
println("Part 2:")
part2()