private class Solution05(val input: List<String>) {

    private val moves = ArrayList<Triple<Int, Int, Int>>()
    private var stacks = ArrayList<ArrayDeque<Char>>()

    fun init() {
        val separatorIndex = input.indexOfFirst { it.isBlank() }

        val numberOfStacks = input[separatorIndex - 1].split(" ").count { it.isNotBlank() }

        stacks.clear()
        for (i in (1..numberOfStacks)) stacks.add(ArrayDeque())

        for (line in input.subList(0, separatorIndex - 1)) {
            for (s in 0..(line.length / 4)) {
                val item = line[s * 4 + 1]
                if (item.isLetter()) stacks[s].addFirst(item)
            }
        }

        moves.clear()
        for (line in input.subList(separatorIndex + 1, input.size)) {
            val splitted = line.split(" ")
            moves.add(Triple(splitted[1].toInt(), splitted[3].toInt(), splitted[5].toInt()))
        }
    }

    private fun doMove(move: Triple<Int, Int, Int>, reverseBatch: Boolean) {
        val from = move.second
        val to = move.third
        val size = move.first

        val batch = ArrayList<Char>()
        for (i in 0 until size) {
            batch.add(stacks[from - 1].last())
            stacks[from - 1].removeLast()
        }
        if (reverseBatch) batch.reverse()
        for (item in batch) stacks[to - 1].addLast(item)
    }

    fun part1() : String {
        init()
        moves.forEach { doMove(it, false) }
        return stacks.map { if (it.isEmpty()) ' ' else it.last() }.joinToString("")
    }

    fun part2(): String {
        init()
        moves.forEach { doMove(it, true) }
        return stacks.map { if (it.isEmpty()) ' ' else it.last() }.joinToString("")
    }

}

fun main() {
    val testSolution = Solution05(readInput("Day05_test"))
    check(testSolution.part1() == "CMZ")
    check(testSolution.part2() == "MCD")

    val solution = Solution05(readInput("Day05"))
    println(solution.part1())
    println(solution.part2())
}
