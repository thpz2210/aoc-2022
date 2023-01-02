private class Solution17(input: List<String>) {

    val jets = input.first().trim().split("").filter { it.isNotBlank() }
    val rocks = (0..6).map { MutableCoordinate(it, 0) }.toMutableSet()
    var jetCount = 0

    fun part1(): Int {
        repeat(2022) { count ->
            val rock = nextRock(count, MutableCoordinate(2, rocks.maxOf { it.y } + 4))
            while (true) {
                when (jets[jetCount % jets.size]) {
                    ">" -> rock.moveRight(rocks)
                    "<" -> rock.moveLeft(rocks)
                }
                jetCount++
                if (!rock.tryMoveDown(rocks))
                    break
            }
            rocks.addAll(rock.coordinates())
        }

        //rocks.groupBy { it.y }.toSortedMap(compareByDescending { it }).forEach{ y -> println((0..6).map { x -> if (MutableCoordinate(x, y.key) in rocks) "#" else " "}.joinToString("")) }

        return rocks.maxOf { it.y }
    }

    fun part2() = 0

    abstract class Rock(val bottomLeft: MutableCoordinate) {

        abstract fun coordinates(): Set<MutableCoordinate>

        abstract val height: Int
        abstract val width: Int

        fun moveRight(rocks: MutableSet<MutableCoordinate>) {
            if (bottomLeft.x + width < 7) bottomLeft.x += 1
            if ((rocks intersect coordinates()).isNotEmpty()) bottomLeft.x -= 1
        }

        fun moveLeft(rocks: MutableSet<MutableCoordinate>) {
            if (bottomLeft.x > 0) bottomLeft.x -= 1
            if ((rocks intersect coordinates()).isNotEmpty()) bottomLeft.x += 1
        }

        fun moveDown() {
            bottomLeft.y -= 1
        }

        fun moveUp() {
            bottomLeft.y += 1
        }

        fun tryMoveDown(rocks: MutableSet<MutableCoordinate>): Boolean {
            if (bottomLeft.y == 0) return false
            moveDown()
            if ((rocks intersect coordinates()).isEmpty()) return true
            moveUp()
            return false
        }

    }

    fun nextRock(count: Int, bottomLeft: MutableCoordinate): Rock {
        return when (count % 5) {
            0 -> Shape0(bottomLeft)
            1 -> Shape1(bottomLeft)
            2 -> Shape2(bottomLeft)
            3 -> Shape3(bottomLeft)
            4 -> Shape4(bottomLeft)
            else -> throw IllegalStateException()
        }
    }

    class Shape0(bottomLeft: MutableCoordinate) : Rock(bottomLeft) {
        override val height = 1
        override val width = 4
        override fun coordinates() = setOf(
            MutableCoordinate(bottomLeft.x, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 2, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 3, bottomLeft.y)
        )
    }

    class Shape1(bottomLeft: MutableCoordinate) : Rock(bottomLeft) {
        override val height = 3
        override val width = 3
        override fun coordinates() = setOf(
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y),
            MutableCoordinate(bottomLeft.x, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x + 2, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y + 2)
        )
    }

    class Shape2(bottomLeft: MutableCoordinate) : Rock(bottomLeft) {
        override val height = 3
        override val width = 3
        override fun coordinates() = setOf(
            MutableCoordinate(bottomLeft.x, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 2, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 2, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x + 2, bottomLeft.y + 2)
        )
    }

    class Shape3(bottomLeft: MutableCoordinate) : Rock(bottomLeft) {
        override val height = 4
        override val width = 1
        override fun coordinates() = setOf(
            MutableCoordinate(bottomLeft.x, bottomLeft.y),
            MutableCoordinate(bottomLeft.x, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x, bottomLeft.y + 2),
            MutableCoordinate(bottomLeft.x, bottomLeft.y + 3)
        )
    }

    class Shape4(bottomLeft: MutableCoordinate) : Rock(bottomLeft) {
        override val height = 2
        override val width = 2
        override fun coordinates() = setOf(
            MutableCoordinate(bottomLeft.x, bottomLeft.y),
            MutableCoordinate(bottomLeft.x, bottomLeft.y + 1),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y),
            MutableCoordinate(bottomLeft.x + 1, bottomLeft.y + 1)
        )
    }

}

fun main() {
    val testSolution = Solution17(readInput("Day17_test"))
    check(testSolution.part1() == 3068)
    //check(testSolution.part2() == 0)

    val solution = Solution17(readInput("Day17"))
    println(solution.part1())
    //println(solution.part2())
}
