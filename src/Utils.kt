import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("input", "$name.txt").readLines()

/**
 * Returns the cartesian product of the given list
 */
fun IntRange.cartesianProduct(other: IntRange): List<Coordinate> = this.flatMap { s ->
    List(other.count()) { s }.zip(other).map { Coordinate(it) }
}

/**
 * Holds a 2 dimensional coordinate
 */
class Coordinate(var x: Int = 0, var y: Int = 0) {

    constructor(coordPair: Pair<Int, Int>) : this(coordPair.first, coordPair.second)

    override fun toString(): String {
        return "Coord(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinate

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

}
