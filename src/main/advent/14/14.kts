import java.io.File

data class Polymerization(val template: String, val pairInsertions: Map<String, String>)

fun parseDefs(): Polymerization {
    val (template, insertionRules) = File("data.txt").readText().split("\n\n")
    val pairInsertions = mutableMapOf<String, String>()
    for (insertion in insertionRules.lines()) {
        val (from, to) = insertion.split(" -> ")
        pairInsertions[from] = to
    }
    return Polymerization(template, pairInsertions)
}

fun getCharFreqs(template: String): Map<Char, Int> {
    val freq = mutableMapOf<Char, Int>()
    for (c in template) {
        freq.putIfAbsent(c, 0)
        freq[c] = freq[c]!! + 1
    }
    return freq
}

fun part1(): Int {
    val defs = parseDefs()
    var template = defs.template
    for (step in 0..9) {
        var newTemplate = StringBuilder()
        for (x in template.indices) {
            newTemplate.append(template[x])
            if (x + 2 <= template.length) {
                val pair = template.substring(x, x + 2)
                val insertion = defs.pairInsertions.get(pair)
                if (insertion != null) {
                    newTemplate.append(insertion)
                }
            }
        }
        template = newTemplate.toString()
    }
    val freqs = getCharFreqs(template)
    return freqs.maxOf { it.value } - freqs.minOf { it.value }
}

println("Part 1: " + part1())