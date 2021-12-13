import java.io.File
import java.io.BufferedReader

data class Cave(
    val name: String,
) {
    val edges = mutableSetOf<Cave>()
    val isBigCave = name.first().isUpperCase()
    override fun hashCode(): Int {
        return name.hashCode()
    }
}
typealias Graph = MutableMap<String, Cave>
typealias Path = MutableList<Cave>
typealias Paths = MutableList<Path>

fun getCaveGraph(): Graph {
    val graph: Graph = mutableMapOf<String, Cave>()
    for (line in File("data.txt").readLines()) {
        val (node0, node1) = line.split('-').map { name -> graph.getOrPut(name) { Cave(name) } }
        node0.edges.add(node1)
        node1.edges.add(node0)
    }
    return graph
}

fun dfs(cave: Cave, endCave: Cave, paths: Paths, currentPath: Path, allowedSmallCaveVisits: Int) {
    if (cave == endCave) {
        paths.add(currentPath.toMutableList())
        return
    }
    for (adjNode in cave.edges) {
        when {
            adjNode.name == "start" -> continue
            adjNode.isBigCave || adjNode !in currentPath -> {
                currentPath.add(adjNode)
                dfs(adjNode, endCave, paths, currentPath, allowedSmallCaveVisits)
                currentPath.removeLast()
            }
            currentPath.count { it.name == adjNode.name } < allowedSmallCaveVisits -> {
                currentPath.add(adjNode)
                dfs(adjNode, endCave, paths, currentPath, allowedSmallCaveVisits - 1)
                currentPath.removeLast()
            }
        }
    }
}

fun part1(): Int {
    val graph = getCaveGraph()
    val paths: Paths = mutableListOf()
    val startPath = mutableListOf(graph["start"]!!)
    dfs(graph["start"]!!, graph["end"]!!, paths, startPath, 0)
    return paths.size
}

fun part2(): Int {
    val graph = getCaveGraph()
    val paths: Paths = mutableListOf()
    val startPath = mutableListOf(graph["start"]!!)
    dfs(graph["start"]!!, graph["end"]!!, paths, startPath, 2)
    return paths.size
}

println("Part 1: " + part1())
println("Part 2: " + part2())