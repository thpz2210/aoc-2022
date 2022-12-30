private class Solution19(input: List<String>) {

    val blueprints = input.map { Blueprint.of(it) }

    fun part1() = blueprints.sumOf { nextMinute(24, State.getInitialState(), it) * it.id }

    fun part2() = blueprints.take(3).map { nextMinute(32, State.getInitialState(), it) }.reduce(Int::times)

    fun nextMinute(minute: Int, stateList: Set<State>, blueprint: Blueprint): Int {
        if (minute == 0) return stateList.maxOf { it.geodes }

        val states = stateList.flatMap { resources ->
            val newStates = mutableSetOf(resources)
            val geodesRobot = buildGeodeRobot(resources, blueprint)
            if (geodesRobot != null) {
                newStates.clear()
                newStates.add(geodesRobot)
            } else {
                val obsidianRobot = buildObsidianRobot(resources, blueprint)
                if (obsidianRobot != null) newStates.add(obsidianRobot)
                val clayRobot = buildClayRobot(resources, blueprint)
                if (clayRobot != null) newStates.add(clayRobot)
                val oreRobot = buildOreRobot(resources, blueprint)
                if (oreRobot != null) newStates.add(oreRobot)
            }
            newStates.forEach {
                it.ore += resources.oreRobots
                it.clay += resources.clayRobots
                it.obsidian += resources.obsidianRobots
                it.geodes += resources.geodesRobots
            }
            newStates
        }.toSet()

        val maxGeodeRobots = states.maxOf { it.geodesRobots }
        return if (maxGeodeRobots > 0)
            nextMinute(minute - 1, states.filter { it.geodesRobots >= maxGeodeRobots - 1 }.toSet(), blueprint)
        else
            nextMinute(minute - 1, states, blueprint)
    }

    fun buildOreRobot(state: State, blueprint: Blueprint): State? {
        if (state.ore < blueprint.oreForOre) return null
        return state.copy(
            ore = state.ore - blueprint.oreForOre,
            oreRobots = state.oreRobots + 1
        )
    }

    fun buildClayRobot(state: State, blueprint: Blueprint): State? {
        if (state.ore < blueprint.oreForClay) return null
        return state.copy(
            ore = state.ore - blueprint.oreForClay,
            clayRobots = state.clayRobots + 1
        )
    }

    fun buildObsidianRobot(state: State, blueprint: Blueprint): State? {
        if (state.ore < blueprint.oreForObsidian || state.clay < blueprint.clayForObsidian) return null
        return state.copy(
            ore = state.ore - blueprint.oreForObsidian,
            clay = state.clay - blueprint.clayForObsidian,
            obsidianRobots = state.obsidianRobots + 1
        )
    }

    fun buildGeodeRobot(state: State, blueprint: Blueprint): State? {
        if (state.ore < blueprint.oreForGeode || state.obsidian < blueprint.obsidianForGeode) return null
        return state.copy(
            ore = state.ore - blueprint.oreForGeode,
            obsidian = state.obsidian - blueprint.obsidianForGeode,
            geodesRobots = state.geodesRobots + 1
        )
    }

    data class Blueprint(
        val id: Int,
        val oreForOre: Int,
        val oreForClay: Int,
        val oreForObsidian: Int,
        val clayForObsidian: Int,
        val oreForGeode: Int,
        val obsidianForGeode: Int
    ) {
        companion object {
            fun of(line: String): Blueprint {
                val split = line.split(" ", ":")
                return Blueprint(
                    split[1].toInt(),
                    split[7].toInt(),
                    split[13].toInt(),
                    split[19].toInt(),
                    split[22].toInt(),
                    split[28].toInt(),
                    split[31].toInt()
                )
            }
        }
    }

    data class State(
        var ore: Int = 0,
        var clay: Int = 0,
        var obsidian: Int = 0,
        var geodes: Int = 0,
        val oreRobots: Int = 0,
        val clayRobots: Int = 0,
        val obsidianRobots: Int = 0,
        val geodesRobots: Int = 0
    ) {
        companion object {
            fun getInitialState() = setOf(State(oreRobots = 1))
        }
    }

}

fun main() {
    val testSolution = Solution19(readInput("Day19_test"))
    check(testSolution.part1() == 33)
    println("a")
    check(testSolution.part2() == 3472)
    println("a")

    val solution = Solution19(readInput("Day19"))
    println(solution.part1())
    println(solution.part2())
}
