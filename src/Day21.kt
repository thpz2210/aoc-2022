private class Solution21(input: List<String>) {

    val monkeys = input
        .map { it.split(": ", " ") }
        .map {
            if (it.size == 2) Monkey(id = it[0], number = it[1].toLong()) else Monkey(
                id = it[0],
                operation = it[2],
                left = it[1],
                right = it[3]
            )
        }
        .associateBy { it.id }.toMutableMap()

    fun part1() = computeNumber("root")

    fun part2(): Long {
        monkeys.values.forEach { it.computed = null }
        val root = monkeys["root"]!!
        monkeys[root.id] = Monkey(root.id, operation = "-", left = root.left, right = root.right)
        return computeHumn(root.id, 0)
    }

    private fun computeHumn(monkeyId: String, expected: Long): Long {
        val monkey = monkeys[monkeyId]!!
        if (monkeyId == "humn") return expected

        if (computeChildren(monkey.left!!).contains("humn")) {
            val opposite = computeNumber(monkey.right!!)
            return when (monkey.operation) {
                "+" -> computeHumn(monkey.left, expected - opposite)
                "-" -> computeHumn(monkey.left, expected + opposite)
                "*" -> computeHumn(monkey.left, expected / opposite)
                "/" -> computeHumn(monkey.left, expected * opposite)
                else -> throw IllegalArgumentException()
            }
        } else if (computeChildren(monkey.right!!).contains("humn")) {
            val opposite = computeNumber(monkey.left)
            return when (monkey.operation) {
                "+" -> computeHumn(monkey.right, expected - opposite)
                "-" -> computeHumn(monkey.right, opposite - expected)
                "*" -> computeHumn(monkey.right, expected / opposite)
                "/" -> computeHumn(monkey.right, opposite / expected)
                else -> throw IllegalArgumentException()
            }
        }
        throw IllegalStateException()
    }

    fun computeNumber(monkeyId: String): Long {
        val monkey = monkeys[monkeyId]!!
        if (monkey.number != null) {
            monkey.computed = monkey.number
        } else {
            monkey.computed = when (monkey.operation) {
                "+" -> computeNumber(monkey.left!!) + computeNumber(monkey.right!!)
                "-" -> computeNumber(monkey.left!!) - computeNumber(monkey.right!!)
                "*" -> computeNumber(monkey.left!!) * computeNumber(monkey.right!!)
                "/" -> computeNumber(monkey.left!!) / computeNumber(monkey.right!!)
                else -> throw IllegalArgumentException()
            }
        }
        return monkey.computed!!
    }

    fun computeChildren(monkeyId: String): Set<String> {
        val monkey = monkeys[monkeyId]!!
        return if (monkey.left == null || monkey.right == null)
            setOf(monkeyId)
        else
            computeChildren(monkey.left).union(computeChildren(monkey.right))
    }

    data class Monkey(
        val id: String,
        var number: Long? = null,
        val operation: String? = null,
        val left: String? = null,
        val right: String? = null,
        var computed: Long? = null,
        val children: Set<String> = mutableSetOf()
    )

}

fun main() {
    val testSolution = Solution21(readInput("Day21_test"))
    check(testSolution.part1() == 152L)
    check(testSolution.part2() == 301L)

    val solution = Solution21(readInput("Day21"))
    println(solution.part1())
    println(solution.part2())
}
