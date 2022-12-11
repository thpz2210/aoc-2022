private class Solution10(input: List<String>) {

    val states = input.fold(mutableListOf(1)) { acc, command -> executeCommand(command, acc) }

    private fun executeCommand(command: String, acc: MutableList<Int>): MutableList<Int> {
        val split = command.split(" ")
        acc.addAll(
            when (split[0]) {
                "noop" -> listOf(acc.last())
                "addx" -> listOf(acc.last(), acc.last() + split[1].toInt())
                else -> throw IllegalArgumentException()
            }
        )
        return acc
    }

    fun part1() = listOf(20, 60, 100, 140, 180, 220).sumOf { it * states[it - 1] }

    fun part2() = (0..5).map { y -> (0..40).map { x -> getPixel(x, y) }.joinToString("") }

    private fun getPixel(x: Int, y: Int): Char {
        val sprite = (states[y * 40 + x]..states[y * 40 + x] + 2)
        return if (x + 1 in sprite) '#' else ' '
    }

}

fun main() {
    val testSolution = Solution10(readInput("Day10_test"))
    check(testSolution.part1() == 13140)
    check(
        testSolution.part2() == listOf(
            "##  ##  ##  ##  ##  ##  ##  ##  ##  ##   ",
            "###   ###   ###   ###   ###   ###   ###  ",
            "####    ####    ####    ####    ####     ",
            "#####     #####     #####     #####      ",
            "######      ######      ######      #### ",
            "#######       #######       #######      "
        )
    )

    val solution = Solution10(readInput("Day10"))
    println(solution.part1())
    println(solution.part2().joinToString("\n"))
}
