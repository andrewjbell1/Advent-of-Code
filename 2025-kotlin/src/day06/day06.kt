package day06

import benchmark
import check
import println
import readInputOfLines
import partitionOnEmptyLine
import rotateAnticlockwise
import toGrid

fun main() {

    fun part1(input: List<String>): Long {
        val listOfList = input.map { it.trim().split("\\s+".toRegex()) }
        val modifierLine = listOfList[listOfList.size-1]
        val numList = listOfList.subList(0, listOfList.size-1)

       return (0..<numList[0].size).sumOf { charCount ->
            val digitsToAccumulate = (0..<numList.size).map { lineCount -> numList[lineCount][charCount]}
            val modifier = modifierLine[charCount]
            val initial = if (modifier=="*") 1L else 0L
           digitsToAccumulate.fold(initial) { acc, num ->
                if (modifier == "*") acc * num.toLong()
                else acc + num.toLong()
            }
        }
    }


    fun part2(input: List<String>): Long {
        val modifierLine = input[input.size-1].trim().split("\\s+".toRegex())
        val rotatedGrid = input.subList(0, input.size-1).toGrid().rotateAnticlockwise()
        val listOfRows = rotatedGrid.map { row -> String(row).trim() }

        var sum = 0L
        var rowsRemaining = listOfRows
        var count = 0
        while (rowsRemaining.isNotEmpty()){
            val partitioned = rowsRemaining.partitionOnEmptyLine()
            rowsRemaining = partitioned.second
            val modifier =modifierLine.reversed()[count]
            val startingAcc = if (modifier=="*") 1L else 0L
            val rowSum = partitioned.first.fold(startingAcc) { acc, num ->
                if (modifier == "*") acc * num.toLong()
                else acc + num.toLong()
            }
            sum+=rowSum
            count++
        }
        return sum
    }

    val example = readInputOfLines("day06/example")
    val input = readInputOfLines("day06/input")

    check(part1(example),4277556L)
    "Part 1:".println()
    part1(input).println()
    benchmark(1000) { part1(input)} // 370.691us

    "Part 2:".println()
    check(part2(example),3263827L)
    part2(input).println()
    benchmark(1000) { part2(input)} // 16.126354ms

}