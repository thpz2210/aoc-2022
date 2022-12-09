private class Solution08(input: List<String>) {

    val trees = input.map { it.toCharArray() }
    val coords = trees.indices.cartesianProduct(trees[0].indices)
    val maxX = trees[0].indices.max()
    val maxY = trees.indices.max()

    fun getTree(c: Coordinate) = trees[c.y][c.x]

    fun getTreesAbove(c: Coordinate) = (0 until c.y).map { trees[it][c.x] }
    fun getTreesBelow(c: Coordinate) = (c.y + 1..maxY).map { trees[it][c.x] }
    fun getTreesLeft(c: Coordinate) = (0 until c.x).map { trees[c.y][it] }
    fun getTreesRight(c: Coordinate) = (c.x + 1..maxX).map { trees[c.y][it] }

    fun getScenicScoreAbove(c: Coordinate) =
        if (c.y == 0) 0 else (c.y - 1 downTo 1).takeWhile { getTree(c) > trees[it][c.x] }.count() + 1

    fun getScenicScoreBelow(c: Coordinate) =
        if (c.y == maxY) 0 else (c.y + 1 until maxY).takeWhile { getTree(c) > trees[it][c.x] }.count() + 1

    fun getScenicScoreLeft(c: Coordinate) =
        if (c.x == 0) 0 else (c.x - 1 downTo 1).takeWhile { getTree(c) > trees[c.y][it] }.count() + 1

    fun getScenicScoreRight(c: Coordinate) =
        if (c.x == maxX) 0 else (c.x + 1 until maxX).takeWhile { getTree(c) > trees[c.y][it] }.count() + 1

    fun part1() = coords.count { c ->
        getTreesAbove(c).all { getTree(c) > it } ||
                getTreesBelow(c).all { getTree(c) > it } ||
                getTreesLeft(c).all { getTree(c) > it } ||
                getTreesRight(c).all { getTree(c) > it }
    }

    fun part2() = coords.maxOf {
        getScenicScoreAbove(it) * getScenicScoreBelow(it) * getScenicScoreLeft(it) * getScenicScoreRight(it)
    }

}

fun main() {
    val testSolution = Solution08(readInput("Day08_test"))
    check(testSolution.part1() == 21)
    check(testSolution.part2() == 8)

    val solution = Solution08(readInput("Day08"))
    println(solution.part1())
    println(solution.part2())
}
