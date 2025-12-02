package day01

import println
import readInputOfLines
import kotlin.collections.forEach

fun main() {
    fun part1(input: List<String>): Int {
        var currentSpot = 50
        var zeros = 0
        input.forEach {
            val next = it.drop(1).toInt()
            val relativeNext = next.mod(100)
            currentSpot = when (it[0]) {
                'L' -> currentSpot - relativeNext
                else -> currentSpot + relativeNext
            }

            if (currentSpot < 0) currentSpot += 100
            if (currentSpot >= 100) currentSpot -= 100
            if (currentSpot == 0) zeros++
        }
        return zeros;
    }

    fun part2(input: List<String>): Int {
        var currentSpot = 50
        var zeros = 0
        input.forEach {
            val next = it.drop(1).toInt()
            val relativeNext = next.mod(100)
            val rotations = next.floorDiv(100)
            val startOfRotationSpot = currentSpot

            currentSpot = when (it[0]) {
                'L' -> currentSpot - relativeNext
                else -> currentSpot + relativeNext
            }

            if (currentSpot < 0) {
                currentSpot += 100
                if (startOfRotationSpot != 0) zeros++
            } else if (currentSpot >= 100) {
                currentSpot -= 100
                if (currentSpot != 0) zeros++
            }

            if (currentSpot == 0) zeros++
            if (rotations > 0) zeros += rotations
        }
        return zeros;
    }

    val example = readInputOfLines("day01/example")
    val input = readInputOfLines("day01/input")

    check(part1(example) == 3)
    "Part 1:".println()
    part1(input).println()

    check(part2(example) == 6)
    "Part 2:".println()
    part2(input).println()
}