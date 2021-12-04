import java.io.File
import java.io.BufferedReader

fun main() {
    val lines = File("data.txt").readLines().map { it.toCharArray() }
    val bitSize = lines[0].size
    val gamma = CharArray(bitSize)
    val epsilon = CharArray(bitSize)
    for(bit in 0..bitSize-1) {
        val onCount = lines.count { it[bit] == '1' }
        val offCount = lines.size - onCount
        gamma[bit] = if (onCount > offCount) '1' else '0'
        epsilon[bit] = if (onCount > offCount) '0' else '1'
    }
    val gammaNumber = Integer.parseInt(gamma.joinToString(""), 2)
    val epsilonNumber = Integer.parseInt(epsilon.joinToString(""), 2)
    println("gamma: " + gammaNumber)
    println("epsilon: " + epsilonNumber)
    println("gamma * epsilon: " + (gammaNumber * epsilonNumber))
}

main()