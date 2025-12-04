package day04

import benchmark
import check
import findAdjacentPosFor
import findLocationsOf
import get
import println
import readInputOfLines
import toGrid
import updatePos

fun main() {
    fun findAccessibleRolls(grid: Array<CharArray>): List<Pair<Int, Int>> = grid.findLocationsOf('@')
        .filter { toiletRoll -> grid.findAdjacentPosFor(toiletRoll).count { grid.get(it) == '@' } < 4 }

    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        return findAccessibleRolls(grid).size
    }

    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        var sum = 0

        while (true){
            val accessibleRolls = findAccessibleRolls(grid)
            if (accessibleRolls.isEmpty()) break
            sum+=accessibleRolls.size
            accessibleRolls.forEach { grid.updatePos(it, '.') }
        }
        return sum
    }

    val example = readInputOfLines("day04/example")
    val input = readInputOfLines("day04/input")


    check(part1(example),13)
    "Part 1:".println()
    part1(input).println()
    benchmark(1000) { part1(input) } //2.66ms

    check(part2(example),43)
    println(part2(example))
    "Part 2:".println()
    part2(input).println()
    benchmark(1000) { part2(input) } //67.22ms

}