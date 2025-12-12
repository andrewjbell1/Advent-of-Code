package day05

import benchmark
import check
import println
import readInputOfLines
import partitionOnEmptyLine
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun Long.inRange(ranges: List<LongRange>): Boolean{
        ranges.forEach { range-> if (range.contains(this)) return true }
        return false
    }

    fun part1(input: List<String>): Int {
        val (rangeStrings, ingredientIds) = input.partitionOnEmptyLine()
        val ranges = rangeStrings.map { it.split("-").let { split -> LongRange(split[0].toLong(), split[1].toLong()) } }
        return ingredientIds.map { it.toLong() }.count { it.inRange(ranges) }
    }

    fun LongRange.overlaps(next : LongRange): Boolean = ((next.first <= this.last && next.first >= this.first) || (this.first <= next.last && this.first >= next.first))
    fun LongRange.merge(next : LongRange) = LongRange(min(this.first, next.first), max(this.last, next.last))
    fun LongRange.sizeOfRange() = (this.last + 1) - this.first

    fun List<LongRange>.removeOverlaps(): List<LongRange> {
        val sorted = this.sortedBy { it.first }
        val initial = sorted.first()
        return sorted.drop(1).fold(listOf(initial)){ acc, range ->
            if (acc.last().overlaps(range)) acc.dropLast(1) + listOf(acc.last().merge(range))
            else acc + listOf(range)
        }
    }

    /*
      rangesSorted.flatMap { it.toSet() }.toSet().size
      will return the correct answer for a small input but takes up too much heap for a larger input. Useful for checking edge cases
     */
    fun part2(input: List<String>) = input.partitionOnEmptyLine().first.map {
        val rangeString = it.split("-")
        LongRange(rangeString[0].toLong(), rangeString[1].toLong())
    }.removeOverlaps().sumOf {it.sizeOfRange()}

    val example = readInputOfLines("day05/example")
    val input = readInputOfLines("day05/input")

    check(part1(example),3)
    "Part 1:".println()
    part1(input).println()
    benchmark(10) { part1(input) } //489.42us

    check(part2(example),14L)
    println(part2(example))
    "Part 2:".println()
    part2(input).println()
    benchmark(10) { part2(input) } //389ms
    // 312456880321071 too low

}