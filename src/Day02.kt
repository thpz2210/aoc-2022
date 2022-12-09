class Solution02(private val input: List<String>) {

    private fun scorePart1(shapes: String): Int {
        return when(shapes.trim()) {
            "A X" -> 3 + 1
            "A Y" -> 6 + 2
            "A Z" -> 0 + 3
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 6 + 1
            "C Y" -> 0 + 2
            "C Z" -> 3 + 3
            else -> throw IllegalArgumentException()
        }
    }

    private fun scorePart2(shapes: String): Int {
        return when(shapes.trim()) {
            "A X" -> 0 + 3
            "A Y" -> 3 + 1
            "A Z" -> 6 + 2
            "B X" -> 0 + 1
            "B Y" -> 3 + 2
            "B Z" -> 6 + 3
            "C X" -> 0 + 2
            "C Y" -> 3 + 3
            "C Z" -> 6 + 1
            else -> throw IllegalArgumentException()
        }
    }

    fun part1() = input.sumOf { scorePart1(it) }

    fun part2() = input.sumOf { scorePart2(it) }

}

fun main() {
    val testSolution = Solution02(readInput("Day02_test"))
    check(testSolution.part1() == 15)
    check(testSolution.part2() == 12)

    val solution = Solution02(readInput("Day02"))
    println(solution.part1())
    println(solution.part2())
}