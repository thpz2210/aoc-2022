private open class Solution11(input: List<String>) {

    val monkeys = input.chunked(7).map { Monkey(it) }

    fun solve(times: Int): Long {
        repeat(times) { doRound() }
        return monkeys.map { it.counter }.sorted().takeLast(2).reduce(Long::times)
    }

    private fun doRound() {
        monkeys.forEach { m -> m.items.forEach { processItem(m, it) }; m.items.clear() }
    }

    private fun processItem(monkey: Monkey, it: Long) {
        var worryLevel = when (monkey.operation["code"]) {
            "*" -> if (monkey.operation["value"] == "old") it * it else it * monkey.operation["value"]!!.toLong()
            "+" -> if (monkey.operation["value"] == "old") it + it else it + monkey.operation["value"]!!.toLong()
            else -> throw IllegalArgumentException()
        }
        worryLevel = reduceWorryLevel(worryLevel)
        if (worryLevel % monkey.testValue == 0L)
            monkeys[monkey.ifTrue].items.add(worryLevel)
        else
            monkeys[monkey.ifFalse].items.add(worryLevel)
        monkey.counter += 1
    }

    open fun reduceWorryLevel(worryLevel: Long) = worryLevel / 3L

    class Monkey(input: List<String>) {
        val items = input[1].split(":").last().split(",").map { it.trim().toLong() }.toMutableList()
        val operation = mapOf(
            Pair("code", input[2].trim().split(" ")[4]),
            Pair("value", input[2].trim().split(" ")[5]))
        val testValue = input[3].trim().split(" ").last().toLong()
        val ifTrue = input[4].trim().split(" ").last().toInt()
        val ifFalse = input[5].trim().split(" ").last().toInt()
        var counter = 0L
    }

}

private class Solution11Part2(input: List<String>) : Solution11(input) {

    val commonMultiple = monkeys.map { it.testValue }.reduce(Long::times)

    override fun reduceWorryLevel(worryLevel: Long) = worryLevel % commonMultiple

}

fun main() {
    val testSolution = Solution11(readInput("Day11_test"))
    check(testSolution.solve(20) == 10605L)

    val testSolution2 = Solution11Part2(readInput("Day11_test"))
    check(testSolution2.solve(10000) == 2713310158L)

    val solution = Solution11(readInput("Day11"))
    println(solution.solve(20))

    val solution2 = Solution11Part2(readInput("Day11"))
    println(solution2.solve(10000))
}
