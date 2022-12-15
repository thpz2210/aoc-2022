import kotlin.math.max
import kotlin.math.min

private class Solution14(input: List<String>) {

    val paths = input.map { line -> line.split(" -> ").map { c -> c.split(",").map { it.toInt() } } }

    val maxY = paths.flatten().maxOf { it[1] }

    var initialCave = paths.flatMap { path ->
        (1 until path.size).flatMap {
            coordinatesBetween(path[it - 1][0], path[it - 1][1], path[it][0], path[it][1])
        }
    }.toSet()

    fun coordinatesBetween(x0: Int, y0: Int, x1: Int, y1: Int): List<Coordinate> {
        val minX = min(x0, x1)
        val maxX = max(x0, x1)
        val minY = min(y0, y1)
        val maxY = max(y0, y1)
        if (minX == maxX) return (minY..maxY).map { y -> Coordinate(minX, y) }
        if (minY == maxY) return (minX..maxX).map { x -> Coordinate(x, minY) }
        throw IllegalStateException()
    }

    fun part1(): Int {
        val cave = initialCave.toMutableSet()
        dropSand(cave)
        return cave.size - initialCave.size
    }

    fun part2(): Int {
        val cave = initialCave.toMutableSet()
        dropSand2(cave)
        return cave.size - initialCave.size
    }

    fun dropSand(cave: MutableSet<Coordinate>) {
        while (true) {
            var position = Coordinate(500, 0)
            var nextPosition = findNextPosition(position, cave)
            while (nextPosition != null) {
                if (nextPosition.y > maxY) return
                position = nextPosition
                nextPosition = findNextPosition(position, cave)
            }
            cave.add(position)
        }
    }

    fun dropSand2(cave: MutableSet<Coordinate>) {
        while (true) {
            var position = Coordinate(500, 0)
            var nextPosition = findNextPosition(position, cave)
            if (nextPosition == null) {
                cave.add(position)
                return
            }
            while (nextPosition != null) {
                if (nextPosition.y > maxY + 1) {
                    break
                }
                position = nextPosition
                nextPosition = findNextPosition(position, cave)
            }
            cave.add(position)
        }
    }

    private fun findNextPosition(position: Coordinate, cave: Set<Coordinate>): Coordinate? {
        val down = Coordinate(position.x, position.y + 1)
        if (!cave.contains(down)) return down

        val downLeft = Coordinate(position.x - 1, position.y + 1)
        if (!cave.contains(downLeft)) return downLeft

        val downRight = Coordinate(position.x + 1, position.y + 1)
        if (!cave.contains(downRight)) return downRight

        return null
    }

}

fun main() {
    val testSolution = Solution14(readInput("Day14_test"))
    check(testSolution.part1() == 24)
    check(testSolution.part2() == 93)

    val solution = Solution14(readInput("Day14"))
    println(solution.part1())
    println(solution.part2())
}
