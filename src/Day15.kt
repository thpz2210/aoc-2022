import kotlin.math.abs

private class Solution15(input: List<String>) {

    val sensors = input.map { getSensorFrom(it) }

    private fun getSensorFrom(line: String): Sensor {
        val split = line.split("x=", "y=", ",", ":")
        val position = Coordinate(split[1].toInt(), split[3].toInt())
        val beacon = Coordinate(split[5].toInt(), split[7].toInt())
        return Sensor(position, beacon)
    }

    fun part1(y: Int) = lineCover(y).sumOf { it.to - it.from + 1 } - beaconsInLine(y)

    private fun beaconsInLine(y: Int) = sensors.map { it.beacon }.filter { it.y == y }.toSet().size

    fun part2(max: Int): Long {
        for (y in (0..max)) {
            val lineCover = lineCover(y).singleOrNull() { it.from in 1..max }
            if (lineCover != null) return (lineCover.from - 1) * 4000000L + y
        }
        throw IllegalStateException()
    }

    private fun lineCover(y: Int): List<LineCover> = sensors.mapNotNull { it.lineCover(y) }.sortedBy { it.from }
            .fold<LineCover, List<LineCover>>(listOf()) { acc, fromTo -> reduceLineCovers(acc, fromTo) }
            .fold(listOf()) { acc, fromTo -> reduceLineCovers(acc, fromTo) }    // here happens magic

    private fun reduceLineCovers(acc: List<LineCover>, rightCover: LineCover): List<LineCover> {
        if (acc.isEmpty())
            return listOf(rightCover)

        val newAcc = mutableSetOf<LineCover>()
        acc.forEach { leftCover ->
            if (leftCover.from == rightCover.from && leftCover.to <= rightCover.to)
                newAcc.add(rightCover)
            else if (rightCover.to <= leftCover.to)
                newAcc.add(leftCover)
            else if (leftCover.to + 1 >= rightCover.from)
                newAcc.add(LineCover(leftCover.from, rightCover.to))
            else {
                newAcc.add(leftCover)
                newAcc.add(rightCover)
            }
        }
        return newAcc.sortedBy { it.from }
    }

    data class Sensor(val position: Coordinate, val beacon: Coordinate) {

        val distance = abs(position.x - beacon.x) + abs(position.y - beacon.y)

        fun lineCover(y: Int): LineCover? {
            val dx = distance - abs(y - position.y)
            return if (dx >= 0) LineCover(position.x - dx, position.x + dx) else null
        }
    }

    data class LineCover(var from: Int, var to: Int)

}

fun main() {
    val testSolution = Solution15(readInput("Day15_test"))
    check(testSolution.part1(10) == 26)
    check(testSolution.part2(20) == 56000011L)

    val solution = Solution15(readInput("Day15"))
    println(solution.part1(2000000))
    println(solution.part2(4000000))
}
