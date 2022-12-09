private class Solution03(input: List<String>) {

    private val rucksacks = input.map { Rucksack(it) }

    private fun getGroupItem(chunk: List<Rucksack>): Set<Char> {
        return chunk[0].items.toSet().intersect(
            chunk[1].items.toSet().intersect(
                chunk[2].items.toSet()
            )
        )
    }

    private fun getScore(charSet: Set<Char>): Int {
        val c = charSet.first()
        return if (c.isUpperCase()) c - 'A' + 27 else c - 'a' + 1
    }

    private fun getDuplicateItem(rucksack: Rucksack) = rucksack.comp2.toSet().intersect(rucksack.comp1.toSet())

    private data class Rucksack(val items: String) {
        val comp1 = items.subSequence(0, items.length / 2)
        val comp2 = items.subSequence(items.length / 2, items.length)
    }

    fun part1() = rucksacks.map { getDuplicateItem(it) }.sumOf { getScore(it) }

    fun part2() = rucksacks.chunked(3).map { getGroupItem(it) }.sumOf { getScore(it) }

}

fun main() {
    val testSolution = Solution03(readInput("Day03_test"))
    check(testSolution.part1() == 157)
    check(testSolution.part2() == 70)

    val solution = Solution03(readInput("Day03"))
    println(solution.part1())
    println(solution.part2())
}
