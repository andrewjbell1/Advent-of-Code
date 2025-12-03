package day3

import check
import indexesOf
import println
import readInputOfLines

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val digitList = line.trim().split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }.toList()

            val max = digitList.subList(0, digitList.size - 1).max()

            digitList.indexesOf { it == max }
                .flatMap { iMax -> digitList.subList(iMax + 1, digitList.size) }
                .map { "$max$it".toInt() }
                .max()

        }.sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val example = readInputOfLines("day3/example")
    val input = readInputOfLines("day3/input")


    check(part1(example), 357)
    "Part 1:".println()
    part1(input).println()

//    check(part2(example),0)
//    println(part2(example))
//    "Part 2:".println()
//    part2(input).println()
}