private class Solution01(input: List<String>) {

    private val elves = mutableListOf(0)

    init {
        input.forEach { if (it.isBlank()) elves.add(0) else elves[elves.lastIndex] += it.toInt() }
    }

    fun part1() = elves.max()

    fun part2() = elves.sorted().takeLast(3).sum()

}

fun main() {
    val testSolution = Solution01(readInput("Day01_test"))
    check(testSolution.part1() == 24000)
    check(testSolution.part2() == 45000)

    val solution = Solution01(readInput("Day01"))
    println(solution.part1())
    println(solution.part2())
}
