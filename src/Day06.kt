private class Solution06(val input: String) {

    fun part1() = findMarker(4)

    fun part2() = findMarker(14)

    private fun findMarker(length: Int) =
        (length until input.length).first { input.subSequence(it - length, it).toSet().size == length }

}

fun main() {
    val testSolution1 = Solution06("mjqjpqmgbljsphdztnvjfqwrcgsmlb")
    check(testSolution1.part1() == 7)
    check(testSolution1.part2() == 19)

    val testSolution2 = Solution06("bvwbjplbgvbhsrlpgdmjqwftvncz")
    check(testSolution2.part1() == 5)
    check(testSolution2.part2() == 23)

    val testSolution3 = Solution06("nppdvjthqldpwncqszvftbrmjlhg")
    check(testSolution3.part1() == 6)
    check(testSolution3.part2() == 23)

    val testSolution4 = Solution06("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
    check(testSolution4.part1() == 10)
    check(testSolution4.part2() == 29)

    val testSolution5 = Solution06("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
    check(testSolution5.part1() == 11)
    check(testSolution5.part2() == 26)

    val solution = Solution06(readInput("Day06").first())
    println(solution.part1())
    println(solution.part2())
}
