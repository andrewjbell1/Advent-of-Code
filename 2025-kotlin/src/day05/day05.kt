package day05

import check
import println
import readInputOfLines
import partitionOnEmptyLine

fun main() {
    fun Long.inRange(ranges: List<LongRange>): Boolean{
        ranges.forEach { range->
            if (range.contains(this)) return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val (rangeStrings, ingredientIds) = input.partitionOnEmptyLine()

        val ranges = rangeStrings.map {
            val rangeString = it.split("-")
            LongRange(rangeString[0].toLong(), rangeString[1].toLong())
        }

       return ingredientIds.map { it.toLong() }.count { it.inRange(ranges) }
    }


    fun part2(input: List<String>): Long {
        val (rangeStrings) =input.partitionOnEmptyLine()

        val ranges = rangeStrings.map {
            val rangeString = it.split("-")
            LongRange(rangeString[0].toLong(), rangeString[1].toLong())
        }

        val rangesSorted = ranges.sortedBy { it.first }

        val rangesNoOverlaps = rangesSorted.zipWithNext{a,b->
            if (b.first<= a.last ) LongRange(a.first, b.first-1)
            else a
        } + listOf(rangesSorted.last())

       return rangesNoOverlaps.filter { it.first <= it.last }
            .sumOf {
            (it.last+1)-it.first
        }
    }

    val example = readInputOfLines("day05/example")
    val input = readInputOfLines("day05/input")

    check(part1(example),3)
    "Part 1:".println()
    part1(input).println()

    check(part2(example),14L)
    println(part2(example))
    "Part 2:".println()
    part2(input).println() // 312456880321056 //
    // 312456880321071 too low

}