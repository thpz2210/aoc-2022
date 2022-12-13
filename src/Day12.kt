private class Solution12(input: List<String>) {

    var grid = Grid2D(input.map { it.toCharArray().toList() })

    fun part1() = distanceFrom('E' to 'S')

    fun part2() = distanceFrom('E' to 'a')

    private fun distanceFrom(fromTo: Pair<Char, Char>): Int {
        val fromChar = fromTo.first
        val toChar = fromTo.second

        val walked = mutableSetOf(grid.findFirstCoordinate { it == fromChar })
        var nextLayer = walked.toSet()
        for (layerCounter in generateSequence(1) { it + 1 }) {
            nextLayer = nextLayer.flatMap { admissibleSteps(it, walked) }.toSet()
            if (nextLayer.map { grid.itemAt(it) }.contains(toChar))
                return layerCounter
            walked.addAll(nextLayer)
        }
        return 0
    }

    private fun admissibleSteps(coordinate: Coordinate, walked: MutableSet<Coordinate>) =
        grid.orthogonalNeighborsOf(coordinate).filter { it !in walked && height(coordinate) - 1 <= height(it) }

    private fun height(coordinate: Coordinate) = when (grid.itemAt(coordinate)) {
        'S' -> 'a'
        'E' -> 'z'
        else -> grid.itemAt(coordinate)
    }

}

fun main() {
    val testSolution = Solution12(readInput("Day12_test"))
    check(testSolution.part1() == 31)
    check(testSolution.part2() == 29)

    val solution = Solution12(readInput("Day12"))
    println(solution.part1())
    println(solution.part2())
}
