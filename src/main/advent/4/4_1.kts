import java.io.File
import java.io.BufferedReader

class Board() {
    val boardSize = 5

    val rows = Array(boardSize) { Array(boardSize) { 0 } }
    val markedRows = Array(boardSize) { Array(boardSize) { 0 } }

    fun hasWon(): Boolean {
        for (index in 0..boardSize - 1) {
            if (markedRows[index].all { it == 1 }) {
                return true
            }
            if (IntArray(markedRows.size) { markedRows[it][index] }.all { it == 1 }) {
                return true
            }
        }
        return false
    }

    fun markNumber(number: Int): Boolean {
        for (row in 0..boardSize - 1) {
            for (column in 0..boardSize - 1) {
                if (rows[row][column] == number) {
                    markedRows[row][column] = 1
                    return true
                }
            }
        }
        return false
    }
}

fun main() {
    val lines: MutableList<List<Int>> = File("data.txt").readLines()
        .map { it.split("(,|\\s+)".toRegex()).filter { it.length > 0 }.map { it.toInt() } }.toMutableList()

    var numbersDrawn = lines.removeFirst()
    var boards = mutableListOf<Board>()
    var currentBoard: Board? = null
    var currentRow = 0
    for (line in lines) {
        if (currentBoard == null || line.size == 0) {
            currentBoard = Board()
            currentRow = 0
            boards.add(currentBoard)

            if (line.size == 0)
                continue
        }
        line.forEachIndexed { currentCol, value ->
            currentBoard.rows[currentRow][currentCol] = value
        }
        currentRow += 1
    }

    for (number in numbersDrawn) {
        for (board in boards) {
            val marked = board.markNumber(number)
            if (marked && board.hasWon()) {
                var unmarkedSum = 0
                for (row in board.markedRows.indices) {
                    for (column in board.markedRows[row].indices) {
                        if (board.markedRows[row][column] == 0) {
                            unmarkedSum += board.rows[row][column]
                        }
                    }
                }
                println("Number: " + number)
                println("Unmarked sum: " + unmarkedSum)
                println("Number * Unmarked sum: " + (number * unmarkedSum))
                return
            }
        }
    }
}

main()