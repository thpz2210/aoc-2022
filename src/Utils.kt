import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", "$name.txt").readLines()


/**
 * Holds a 2 dimensional grid
 */
class Grid2D<T>(initialItems: List<List<T>>) {

    private val itemMap = initialItems.indices.flatMap { y ->
            initialItems[y].indices.map { x -> Coordinate(x, y) to initialItems[y][x] }
        }.toMap()

    fun itemAt(coordinate: Coordinate) = itemMap[coordinate]!!

    fun findFirstCoordinate(predicate: (T) -> Boolean) = itemMap.filterValues { predicate(it) }.map { it.key }.first()

    fun orthogonalNeighborsOf(coordinate: Coordinate) = listOf(
        Coordinate(coordinate.x, coordinate.y - 1),
        Coordinate(coordinate.x, coordinate.y + 1),
        Coordinate(coordinate.x - 1, coordinate.y),
        Coordinate(coordinate.x + 1, coordinate.y)
    ).filter { itemMap.keys.contains(it) }.toSet()

}


/**
 * Holds a 2 dimensional coordinate
 */
data class Coordinate(val x: Int = 0, val y: Int = 0) {
    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)
    override fun toString() ="(x=$x, y=$y)"
}


/**
 * Returns the cartesian product of the given IntRanges
 */
fun IntRange.cartesianProduct(other: IntRange): List<Coordinate> = this.flatMap { s ->
    List(other.count()) { s }.zip(other).map { Coordinate(it) }
}
