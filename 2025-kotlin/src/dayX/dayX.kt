package dayX

import check
import println
import readInputOfCommaSeparated

fun main() {
    fun part1(input: List<String>): Int {

        return 0
    }


    fun part2(input: List<String>): Int {
    return 0
    }

    val example = readInputOfCommaSeparated("dayX/example")
    val input = readInputOfCommaSeparated("dayX/input")


    check(part1(example),0)
    "Part 1:".println()
    part1(input).println()

    check(part2(example),0)
    println(part2(example))
    "Part 2:".println()
    part2(input).println()
}