import java.lang.Math.floorMod
import kotlin.math.sign

private class Solution20(input: List<String>) {

    val numbersPart1 = input.mapIndexed { index, it -> Number(it.toLong(), moveIndex = index) }.toMutableList()
    val numbersPart2 = input.mapIndexed { index, it -> Number(it.toLong() * 811589153L, moveIndex = index) }.toMutableList()

    fun part1(): Long {
        mixNumbers(numbersPart1)
        return groveCoordinates(numbersPart1)
    }

    fun part2(): Long {
        repeat(10) { mixNumbers(numbersPart2) }
        return groveCoordinates(numbersPart2)
    }

    private fun mixNumbers(numbers: MutableList<Number>) {
        numbers.forEach { it.isMoved = false }
        while (true) {
            val item = numbers.filter { !it.isMoved }.minByOrNull { it.moveIndex } ?: break
            item.isMoved = true

            val index = numbers.indexOf(item)
            numbers.removeAt(index)

            var newIndex = floorMod(index + item.value, numbers.size)
            if (newIndex == 0 && item.value.sign == -1) newIndex = numbers.size
            numbers.add(newIndex, item)
        }
    }

    private fun groveCoordinates(numbers: List<Number>) = listOf(1000, 2000, 3000)
        .map { n -> numbers.indexOfFirst { it.value == 0L } + n }
        .sumOf { numbers[it % numbers.size].value }

    data class Number(val value: Long, var isMoved: Boolean = false, val moveIndex: Int)

}

fun main() {
    val testSolution = Solution20(readInput("Day20_test"))
    check(testSolution.part1() == 3L)
    check(testSolution.part2() == 1623178306L)

    val solution = Solution20(readInput("Day20"))
    println(solution.part1())
    println(solution.part2())
}
