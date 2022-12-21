private class Solution18(input: List<String>) {

    val coordinates =
        input.map { it.trim().split(",") }.map { Coordinate3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }.toSet()

    val steam = mutableSetOf<Coordinate3D>()

    val minX = coordinates.minOf { it.x - 1 }
    val maxX = coordinates.maxOf { it.x + 1 }
    val minY = coordinates.minOf { it.y - 1 }
    val maxY = coordinates.maxOf { it.y + 1 }
    val minZ = coordinates.minOf { it.z - 1 }
    val maxZ = coordinates.maxOf { it.z + 1 }

    fun part1() = coordinates.sumOf { 6 - adjacent(it).count { adjacent -> coordinates.contains(adjacent) } }

    fun part2(): Int {
        letStreamFlow()
        return coordinates.sumOf { adjacent(it).count { adjacent -> steam.contains(adjacent) } }
    }

    private fun letStreamFlow() {
        addBox()
        while (true) {
            if (!steam.addAll(steam.flatMap { adjacent(it) }.filter { isInBox(it) && !coordinates.contains(it) }))
                break
        }
    }

    private fun addBox() {
        for (x in minX..maxX)
            for (y in minY..maxY) {
                steam.add(Coordinate3D(x, y, minZ))
                steam.add(Coordinate3D(x, y, maxZ))
            }
        for (x in minX..maxX)
            for (z in minZ..maxZ) {
                steam.add(Coordinate3D(x, minY, z))
                steam.add(Coordinate3D(x, maxY, z))
            }
        for (y in minY..maxY)
            for (z in minZ..maxZ) {
                steam.add(Coordinate3D(minX, y, z))
                steam.add(Coordinate3D(maxX, y, z))
            }
    }

    private fun isInBox(coordinate: Coordinate3D) =
        coordinate.x in minX..maxX && coordinate.y in minY..maxY && coordinate.z in minZ..maxZ

    fun adjacent(coordinate: Coordinate3D) = setOf(
        Coordinate3D(coordinate.x + 1, coordinate.y, coordinate.z),
        Coordinate3D(coordinate.x - 1, coordinate.y, coordinate.z),
        Coordinate3D(coordinate.x, coordinate.y + 1, coordinate.z),
        Coordinate3D(coordinate.x, coordinate.y - 1, coordinate.z),
        Coordinate3D(coordinate.x, coordinate.y, coordinate.z + 1),
        Coordinate3D(coordinate.x, coordinate.y, coordinate.z - 1)
    )

    data class Coordinate3D(val x: Int, val y: Int, val z: Int)

}

fun main() {
    val testSolution = Solution18(readInput("Day18_test"))
    check(testSolution.part1() == 64)
    check(testSolution.part2() == 58)

    val solution = Solution18(readInput("Day18"))
    println(solution.part1())
    println(solution.part2())
}
