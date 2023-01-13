private class Solution22(val input: List<String>) {

    companion object {
        fun wireAsBoard(board: Board) {

            fun wireRight(tile: Tile, map: Map<Pair<Int, Int>, Tile>): Tile {
                val newRight = map.getOrDefault(
                    Pair(tile.x + 1, tile.y),
                    board.tiles.filter { it.y == tile.y }.minBy { it.x })
                return if (newRight.c == '#') tile else newRight
            }

            fun wireDown(tile: Tile, map: Map<Pair<Int, Int>, Tile>): Tile {
                val newDown = map.getOrDefault(
                    Pair(tile.x, tile.y + 1),
                    board.tiles.filter { it.x == tile.x }.minBy { it.y })
                return if (newDown.c == '#') tile else newDown
            }

            fun wireLeft(tile: Tile, map: Map<Pair<Int, Int>, Tile>): Tile {
                val newLeft = map.getOrDefault(
                    Pair(tile.x - 1, tile.y),
                    board.tiles.filter { it.y == tile.y }.maxBy { it.x })
                return if (newLeft.c == '#') tile else newLeft
            }

            fun wireUp(tile: Tile, map: Map<Pair<Int, Int>, Tile>): Tile {
                val newUp = map.getOrDefault(
                    Pair(tile.x, tile.y - 1),
                    board.tiles.filter { it.x == tile.x }.maxBy { it.y })
                return if (newUp.c == '#') tile else newUp
            }

            val map = board.tiles.associateBy { Pair(it.x, it.y) }
            board.tiles.forEach { tile ->
                tile.right = wireRight(tile, map)
                tile.down = wireDown(tile, map)
                tile.left = wireLeft(tile, map)
                tile.up = wireUp(tile, map)
            }
        }

        fun wireAsCube(board: Board) {

        }
    }

    fun solve(wire: (Board) -> Unit): Int {
        val steps = Steps(input.last())
        val board = Board(input.takeWhile { it.isNotBlank() })
        wire(board)
        do {
            val step = steps.next() ?: break
            when (step) {
                "R" -> board.turnRight()
                "L" -> board.turnLeft()
                else -> board.goForward(step.toInt())
            }
        } while (true)
        return 1000 * (board.position.y + 1) + 4 * (board.position.x + 1) + board.direction
    }

    class Board(input: List<String>) {

        val right = 0
        val down = 1
        val left = 2
        val up = 3

        val tiles = input.flatMapIndexed { y, l -> l.mapIndexed { x, c -> Tile(x, y, c)} }.filterNot { it.c == ' ' }

        var position = tiles.filter { it.y == 0 }.minBy { it.x }
        var direction = right

        fun turnRight() {
            direction = when (direction) {
                right -> down
                down -> left
                left -> up
                up -> right
                else -> throw IllegalArgumentException()
            }
        }

        fun turnLeft() {
            direction = when (direction) {
                right -> up
                down -> right
                left -> down
                up -> left
                else -> throw IllegalArgumentException()
            }
        }

        fun goForward(times: Int) {
            repeat(times) {when (direction) {
                    right -> position = position.right!!
                    down -> position = position.down!!
                    left -> position = position.left!!
                    up -> position = position.up!!
                }
            }
        }
    }

    class Tile(var x: Int, var y: Int, var c: Char) {
        var right: Tile? = null
        var down: Tile? = null
        var left: Tile? = null
        var up: Tile? = null
    }

    class Steps(input: String) {

        private val steps = mutableListOf<String>()
        private var count = 0

        init {
            var actualNumber = ""
            for (c in input) {
                if (c == 'R' || c == 'L') {
                    if (actualNumber.isNotBlank()) {
                        steps.add(actualNumber)
                        actualNumber = ""
                    }
                    steps.add(c.toString())
                } else {
                    actualNumber += c
                }
            }
            if (actualNumber.isNotBlank()) steps.add(actualNumber)
        }

        fun next() = if (count < steps.size) steps[count++] else null

    }

}

fun main() {
    val testSolution = Solution22(readInput("Day22_test"))
    check(testSolution.solve(Solution22::wireAsBoard) == 6032)
    // check(testSolution.solve(Solution22::wireAsCube) == 0)

    val solution = Solution22(readInput("Day22"))
    println(solution.solve(Solution22::wireAsBoard))
    // println(solution.solve(Solution22::wireAsCube))
}
