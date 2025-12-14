package day07

import Pos
import benchmark
import check
import col
import println
import readInputOfLines
import row

fun main() {

    fun part1(input: List<String>): Int {

        val startingPoint = input[0].indexOf("S")
        val lines = input.subList(1, input.size)

        return lines.fold(Pair(0, setOf(startingPoint))) { acc, line ->
            val mutableSet = acc.second.toMutableSet()
            var split = acc.first
            acc.second.forEach { beamIndex ->
                if (line[beamIndex] == '^') {
                    mutableSet.add(beamIndex - 1)
                    mutableSet.add(beamIndex + 1)
                    mutableSet.remove(beamIndex)
                    split++

                }
            }
            Pair(split, mutableSet)

        }.first
    }

    fun searchForPath(
        count: Long,
        currentPos: Pos,
        inputList: List<String>,
        cache: MutableMap<Pos, Long>
    ): Long {
        if (currentPos.row() > inputList.size - 1) return count + 1L

        if (cache.contains(currentPos)) {
            val hit = cache[currentPos]
            return hit!!
        }

        val line = inputList[currentPos.row()]
        if (line[currentPos.col()] == '^') {
            val result = searchForPath(count, Pos(currentPos.row() + 1 , currentPos.col() - 1), inputList, cache) +
                    searchForPath(count, Pos(currentPos.row() + 1 , currentPos.col() + 1), inputList, cache)
            cache[currentPos] = result
            return result
        }

        val result = searchForPath(count, Pos(currentPos.row() + 1, currentPos.col()), inputList, cache)
        cache[currentPos] = result
        return result
    }

    fun part2(input: List<String>): Long {
        val startingPoint = input[0].indexOf("S")
        val lines = input.subList(1, input.size)
        return searchForPath(0L, Pos(0, startingPoint), lines, mutableMapOf())
    }

    val example = readInputOfLines("day07/example")
    val input = readInputOfLines("day07/input")

    check(part1(example), 21)
    "Part 1:".println()
    part1(input).println()
    benchmark(1000) { part1(input) } // 362.56us

    "Part 2:".println()
    check(part2(example), 40L)
    part2(input).println()
    benchmark(1000) { part2(input)} // 446.725us

}