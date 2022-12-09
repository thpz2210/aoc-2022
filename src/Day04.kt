private class Solution04(input: List<String>) {

    private val sections = input.map { Section(it) }

    private class Section(line: String) {

        private var elf1: IntRange
        private var elf2: IntRange

        init {
            val limits = line.split(",", "-").map { it.toInt() }
            elf1 = IntRange(limits[0], limits[1])
            elf2 = IntRange(limits[2], limits[3])
        }

        fun isFullyContained() = (elf1 subtract elf2).isEmpty() || (elf2 subtract elf1).isEmpty()

        fun isOverlapping() = (elf1 intersect elf2).isNotEmpty()

    }

    fun part1() = sections.count { it.isFullyContained() }

    fun part2() = sections.count { it.isOverlapping() }

}

fun main() {
    val testSolution = Solution04(readInput("Day04_test"))
    check(testSolution.part1() == 2)
    check(testSolution.part2() == 4)

    val solution = Solution04(readInput("Day04"))
    println(solution.part1())
    println(solution.part2())
}
