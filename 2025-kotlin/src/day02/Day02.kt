package day02

import benchmark
import println
import readInputOfCommaSeparated

fun main() {
    fun part1(input: List<String>): Long {
        var total = 0L
        input.forEach {
            val rangeString = it.split("-")
            LongRange(rangeString[0].toLong(), rangeString[1].toLong())
                .filter { it.toString().length.mod(2) == 0 }
                .forEach {
                    val left = it.toString().subSequence(0, it.toString().length.div(2)).toString().toLong()
                    val right = it.toString().subSequence(it.toString().length.div(2), it.toString().length).toString().toLong()
                    if (left == right) total += it
                }
        }
        return total
    }

    fun Long.hasDuplicates(): Boolean  {
        val rangeItemString = this.toString()
        1.rangeTo(rangeItemString.length).map { possibleSequenceLength ->
            val possibleSequence = rangeItemString.substring(0, possibleSequenceLength)
            val maxNumberOfSequencesThisLength = rangeItemString.length.div(possibleSequenceLength)

            if (maxNumberOfSequencesThisLength <= 1) return false
            val shouldMatchOriginalString = possibleSequence.repeat(maxNumberOfSequencesThisLength)

            if (shouldMatchOriginalString == rangeItemString) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Long {
        return input.flatMap {
            val (start, end) = it.split("-").map { it.toLong() }
            LongRange(start, end)
        }.filter { it.hasDuplicates()}.sum()
    }

    val example = readInputOfCommaSeparated("day02/example")
    val input = readInputOfCommaSeparated("day02/input")

    check(part1(example) == 1227775554L)
    "Part 1:".println()
    part1(input).println()

    benchmark (1000){ part1(input) } //220.45ms

    check(part2(example) == 4174379265L)
    println(part2(example))

    "Part 2:".println()
    part2(input).println()
    benchmark(1000) { part2(input) } //367.96ms

}