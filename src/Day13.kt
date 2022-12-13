import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import java.lang.Integer.max

private class Solution13(input: List<String>) {

    val pairs = input.chunked(3).map { it[0] to it[1] }
    val packages = input.filter { it.isNotBlank() }.toMutableList()

    fun part1() =
        pairs.indices.filter { Comparator.compare(pairs[it].first, pairs[it].second) == RIGHT_ORDER }.sumOf { it + 1 }

    fun part2(): Int {
        val divider1 = "[[2]]"
        val divider2 = "[[6]]"
        packages.addAll(listOf(divider1, divider2))
        val sorted = packages.sortedWith(Comparator)
        return (sorted.indexOfFirst { it == divider1 } + 1) * (sorted.indexOfFirst { it == divider2 } + 1)
    }

    companion object {
        const val RIGHT_ORDER = -1
        const val EQUAL = 0
        const val NOT_RIGHT_ORDER = 1

        val Comparator =
            Comparator<String> { left, right -> compare(ObjectMapper().readTree(left), ObjectMapper().readTree(right)) }

        fun compare(left: JsonNode, right: JsonNode): Int {
            if (left is IntNode && right is IntNode) {
                return compareValues(left.intValue(), right.intValue())
            } else if (left is ArrayNode && right is ArrayNode) {
                for (i in (0 until max(left.size(), right.size()))) {
                    if (left.size() <= i) {
                        return RIGHT_ORDER
                    } else if (right.size() <= i) {
                        return NOT_RIGHT_ORDER
                    }
                    val c = compare(left[i], right[i])
                    if (c != 0) return c
                }
                return EQUAL
            } else if (left is IntNode && right is ArrayNode) {
                val node = JsonNodeFactory.instance.arrayNode()
                node.add(left)
                return compare(node, right)
            } else {
                val node = JsonNodeFactory.instance.arrayNode()
                node.add(right)
                return compare(left, node)
            }
        }
    }
}

fun main() {
    val comparator = Solution13.Comparator
    check(comparator.compare("[1,1,3,1,1]", "[1,1,5,1,1]") == Solution13.RIGHT_ORDER)
    check(comparator.compare("[[1],[2,3,4]]", "[[1],4]") == Solution13.RIGHT_ORDER)
    check(comparator.compare("[9]", "[[8,7,6]]") == Solution13.NOT_RIGHT_ORDER)
    check(comparator.compare("[[4,4],4,4]", "[[4,4],4,4,4]") == Solution13.RIGHT_ORDER)
    check(comparator.compare("[7,7,7,7]", "[7,7,7]") == Solution13.NOT_RIGHT_ORDER)
    check(comparator.compare("[]", "[3]") == Solution13.RIGHT_ORDER)
    check(comparator.compare("[[[]]]", "[[]]") == Solution13.NOT_RIGHT_ORDER)
    check(
        comparator.compare(
            "[1,[2,[3,[4,[5,6,7]]]],8,9]",
            "[1,[2,[3,[4,[5,6,0]]]],8,9]"
        ) == Solution13.NOT_RIGHT_ORDER
    )

    val testSolution = Solution13(readInput("Day13_test"))
    check(testSolution.part1() == 13)
    check(testSolution.part2() == 140)

    val solution = Solution13(readInput("Day13"))
    println(solution.part1())
    println(solution.part2())
}
