import java.io.File

data class RiskNode(
    val value: Int,
    val row: Int,
    val column: Int
)

data class RiskGraph<T>(
    val vertices: List<T>,
    val edges: Map<T, List<T>>
) {
    fun getEdges(node:T): List<T> {
        val edges = edges.get(node)
        if (edges != null)
            return edges
        else
            return emptyList()
    }
}

fun getRiskGraph(): RiskGraph<RiskNode> {
    val nodes = mutableListOf<RiskNode>()
    var lineRow = 0
    var lineCol = 0
    for (line in File("data.txt").readLines()) {
        for (nodeValue in line.split("").filter { it.length > 0 }.map { it.toInt() }) {
            nodes.add(RiskNode(nodeValue, lineRow, lineCol++))
        }
        lineRow++
        lineCol = 0
    }
    return connectGraph(nodes)
}

fun connectGraph(nodes: List<RiskNode>): RiskGraph<RiskNode> {
    val nodeByRowColumn = nodes.map { Pair(it.row, it.column) to it }.toMap()
    val edges = mutableMapOf<RiskNode, List<RiskNode>>()
    for (node in nodes) {
        val adjNodes = sequenceOf(
            nodeByRowColumn.get(Pair(node.row - 1, node.column)),
            nodeByRowColumn.get(Pair(node.row, node.column + 1)),
            nodeByRowColumn.get(Pair(node.row + 1, node.column)),
            nodeByRowColumn.get(Pair(node.row, node.column - 1)),
        ).filterNotNull()
        edges[node] = adjNodes.toList()
    }
    return RiskGraph<RiskNode>(nodes, edges)
}

fun enlargeGraph(graph: RiskGraph<RiskNode>): RiskGraph<RiskNode> {
    val maxRow = graph.vertices.maxOf { it.row }
    val maxColumn = graph.vertices.maxOf { it.column }
    val nodeByRowColumn = graph.vertices.map { Pair(it.row, it.column) to it }.toMap()
    val newNodes = mutableListOf<RiskNode>()
    for (row in 0..maxRow) {
        for (column in 0..maxColumn) {
            val node = nodeByRowColumn.get(Pair(row, column))!!
            for (copyRows in 0..5 - 1) {
                for (copyColumns in 0..5 - 1) {
                    var value = node.value + copyRows + copyColumns
                    if (value > 9) {
                        value = value - 9
                    }
                    val copyRow = row + copyRows + (maxRow * copyRows)
                    val copyColumn = column + copyColumns + (maxColumn * copyColumns)
                    val copyNode = RiskNode(value, copyRow, copyColumn)
                    newNodes.add(copyNode)
                }
            }
        }
    }
    return connectGraph(newNodes)
}

fun calcLowestRiskValue(graph: RiskGraph<RiskNode>): Int {
    val start = graph.vertices.first()

    val risks = graph.vertices.map {
        it to if (it == start) 0 else Int.MAX_VALUE
    }.toMap().toMutableMap()
    fun getRisk(node: RiskNode):Int { return risks.get(node)!! }

    val queue = ArrayDeque<RiskNode>().apply { add(start) }
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        for (adjNode in graph.getEdges(node)) {
            val riskNode = getRisk(node)
            if (getRisk(adjNode) > riskNode + adjNode.value) {
                risks.put(adjNode, riskNode + adjNode.value)
                queue.add(adjNode)
            }
        }
    }
    return risks.get(graph.vertices.last())!!
}

fun part1(): Int {
    return calcLowestRiskValue(getRiskGraph())
}

fun part2(): Int {
    return calcLowestRiskValue(enlargeGraph(getRiskGraph()))
}

println("Part 1: " + part1())
println("Part 2: " + part2())