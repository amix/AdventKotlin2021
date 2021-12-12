import java.io.File
import java.io.BufferedReader

data class Position(val row: Int, val column: Int)
typealias EnergyLevels = MutableList<MutableList<Int>>

val numColumns = 10
val numRows = 10

fun getInitialEnergyLevels(): EnergyLevels {
    return File("data.txt").readLines().map { it.split("").filter { it.length > 0 }.map { it.toInt() }.toMutableList() }
        .toMutableList()
}

fun increaseEnergy(energyLevels: EnergyLevels, position: Position, flashes: MutableMap<Position, Boolean>) {
    val currentEnergy = energyLevels.getOrNull(position.row)?.getOrNull(position.column)
    if (currentEnergy != null && position !in flashes) {
        var nextEnergy = currentEnergy + 1
        energyLevels[position.row][position.column] = nextEnergy
        if (position !in flashes && nextEnergy > 9) {
            flashes[position] = true
            sequenceOf(
                Position(position.row - 1, position.column), // top
                Position(position.row, position.column + 1), // right
                Position(position.row + 1, position.column), // bottom
                Position(position.row, position.column - 1), // left
                Position(position.row - 1, position.column - 1), // top-left
                Position(position.row - 1, position.column + 1), // top-right
                Position(position.row + 1, position.column - 1), // bottom-left
                Position(position.row + 1, position.column + 1), // bottom-right
            ).forEach { increaseEnergy(energyLevels, it, flashes) }
        }
    }
}

fun part1(): Int {
    val maxSteps = 100
    val energyLevels = getInitialEnergyLevels()

    var totalFlashes = 0
    val flashes = mutableMapOf<Position, Boolean>()
    for (i in 0..maxSteps - 1) {
        for (row in 0..numRows - 1) {
            for (column in 0..numColumns - 1) {
                increaseEnergy(energyLevels, Position(row, column), flashes)
            }
        }
        for ((flashedPosition, _) in flashes) {
            energyLevels[flashedPosition.row][flashedPosition.column] = 0
        }
        totalFlashes += flashes.size
        flashes.clear()
    }
    return totalFlashes
}

fun part2(): Int {
    val energyLevels = getInitialEnergyLevels()

    val flashes = mutableMapOf<Position, Boolean>()
    var steps = 0
    while (true) {
        steps += 1
        for (row in 0..numRows - 1) {
            for (column in 0..numColumns - 1) {
                increaseEnergy(energyLevels, Position(row, column), flashes)
            }
        }
        for ((flashedPosition, _) in flashes) {
            energyLevels[flashedPosition.row][flashedPosition.column] = 0
        }

        if (energyLevels.all { it.all { it == 0 } }) {
            break
        }
        flashes.clear()
    }
    return steps
}

println("Part 1: " + part1())
println("Part 2: " + part2())