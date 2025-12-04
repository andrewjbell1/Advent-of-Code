package day3

import benchmark
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

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            var remainingDigits = line.trim().split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }.toList()

            var digitsRequiredCount = 11
            var maxFromDigits: List<Int>
            var joltage = ""
            while (digitsRequiredCount >= 0) {
                maxFromDigits = remainingDigits.subList(0, remainingDigits.size - digitsRequiredCount)
                var maxIndex = 0
                for (i in maxFromDigits.indices) {
                    if (maxFromDigits[i] > maxFromDigits[maxIndex]) {
                        maxIndex = i
                    }
                }
                joltage += maxFromDigits[maxIndex].toString()
                remainingDigits = remainingDigits.subList(maxIndex + 1, remainingDigits.size)
                digitsRequiredCount--
            }
            joltage.toLong()
        }

    }

    val example = readInputOfLines("day3/example")
    val input = readInputOfLines("day3/input")

    check(part1(example), 357)
    "Part 1:".println()
    part1(input).println()
    benchmark(10) { part1(input) } //5.17ms

    check(part2(example), 3121910778619)
    println(part2(example))
    "Part 2:".println()
    part2(input).println()
    benchmark(10) { part2(input) } //3.43ms
}