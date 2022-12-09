private class Solution07(input: List<String>) {

    private val root = Node("/")

    init {
        var currentNode = root
        input.forEach {line ->
            when (line.substring(0, 4).trim()) {
                "$ cd" -> currentNode = when (val dirname = line.substring(4).trim()) {
                    "/" -> root
                    ".." -> currentNode.parent!!
                    else -> currentNode.children.first { it.id == dirname }
                }

                "dir" -> {
                    val dirname = line.substring(4)
                    currentNode.children.add(Node(dirname, parent = currentNode))
                }

                else -> {
                    val split = line.split(" ")
                    val size = split[0].toIntOrNull()
                    if (size != null ) {
                        val filename = split[1]
                        currentNode.children.add(Node(filename, parent = currentNode, _size = size))
                    }
                }
            }
        }
    }

    private data class Node(val id: String, private var _size: Int? = null, val parent: Node? = null) {

        val children: MutableList<Node> = mutableListOf()

        val size: Int
            get() {
                if (_size == null) _size = children.sumOf { it.size }
                return _size!!
            }

        val allChildren: List<Node>
            get() {
                val allChildren = mutableListOf(this)
                allChildren.addAll(children.flatMap { it.allChildren })
                return allChildren
            }

        val isNoLeaf: Boolean
            get() = children.isNotEmpty()
    }


    fun part1() = root.allChildren.filter { it.isNoLeaf }.filter { it.size <= 100000 }.sumOf { it.size }

    fun part2() = root.allChildren.filter { it.isNoLeaf }.filter { it.size >= root.size - 40000000 }.minOf { it.size }

}

fun main() {
    val testSolution = Solution07(readInput("Day07_test"))
    check(testSolution.part1() == 95437)
    check(testSolution.part2() == 24933642)

    val solution = Solution07(readInput("Day07"))
    println(solution.part1())
    println(solution.part2())
}
