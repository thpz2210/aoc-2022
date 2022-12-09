import kotlin.math.abs
import kotlin.math.sign

private class Solution09(input: List<String>, ropeLength: Int) {

    val moves = input.map { it.split(" ") }.flatMap { (move, count) -> (0 until count.toInt()).map { move } }
    var rope = (0 until ropeLength).map { Coordinate() }.toMutableList()
    val tailPositions = mutableSetOf<Coordinate>()

    fun solve(): Int {
        for (m in moves) {
            rope.indices.forEach { if (it == 0) moveHead(m) else if (hasToMove(it)) move(it) }
            tailPositions.add(rope.last())
        }
        return tailPositions.size
    }

    private fun moveHead(m: String) {
        val head = rope.first()
        rope[0] = when (m) {
            "U" -> Coordinate(head.x, head.y + 1)
            "D" -> Coordinate(head.x, head.y - 1)
            "L" -> Coordinate(head.x - 1, head.y)
            "R" -> Coordinate(head.x + 1, head.y)
            else -> throw IllegalArgumentException()
        }
    }

    private fun hasToMove(node: Int): Boolean {
        val predecessor = rope[node - 1]
        val successor = rope[node]
        return abs(predecessor.x - successor.x) > 1 || abs(predecessor.y - successor.y) > 1
    }

    private fun move(node: Int) {
        val predecessor = rope[node - 1]
        val successor = rope[node]
        rope[node] = Coordinate(
            successor.x - (successor.x - predecessor.x).sign,
            successor.y - (successor.y - predecessor.y).sign
        )
    }

}

fun main() {
    val testSolution = Solution09(readInput("Day09_test"), 2)
    check(testSolution.solve() == 13)

    val testSolution2 = Solution09(readInput("Day09_test"), 10)
    check(testSolution2.solve() == 1)

    val testSolution3 = Solution09(readInput("Day09_test2"), 10)
    check(testSolution3.solve() == 36)

    val solution = Solution09(readInput("Day09"), 2)
    println(solution.solve())

    val solution2 = Solution09(readInput("Day09"), 10)
    println(solution2.solve())
}
