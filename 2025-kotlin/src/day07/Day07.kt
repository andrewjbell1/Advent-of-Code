package day07

import benchmark
import check
import println
import readInputOfLines
import partitionOnEmptyLine
import rotateAnticlockwise
import toGrid

fun main() {

    fun part1(input: List<String>): Int {

        val startingPoint = input[0].indexOf("S")
        val lines = input.subList(1, input.size)

       return lines.fold(Pair(0, setOf(startingPoint))){acc, line ->
            val mutableSet = acc.second.toMutableSet()
            var split= acc.first
            acc.second.forEach { beamIndex ->
                if (line[beamIndex] == '^'){
                    mutableSet.add(beamIndex-1)
                    mutableSet.add(beamIndex+1)
                    mutableSet.remove(beamIndex)
                    split++

                }
            }
            Pair(split, mutableSet)

        }.first
    }

    fun recursive(count: Int, beamIndex: Int, inputList: List<String>, inputLineIndex: Int): Int{
        if (inputLineIndex>inputList.size-1){
            return count+1
        }
        val line = inputList[inputLineIndex]
        if (line[beamIndex] == '^'){
            return recursive(count, beamIndex-1, inputList, inputLineIndex+1)+ recursive(count, beamIndex+1, inputList, inputLineIndex+1, memo)
        }
        return recursive(count, beamIndex, inputList, inputLineIndex +1)
    }

    fun part2(input: List<String>): Int {
        val startingPoint = input[0].indexOf("S")
        val lines = input.subList(1, input.size)
        return recursive(0, startingPoint, lines, 0)
    }

    val example = readInputOfLines("day07/example")
    val input = readInputOfLines("day07/input")

    check(part1(example),21)
    "Part 1:".println()
    part1(input).println()
    benchmark(1000) { part1(input)} // 197.978us

    "Part 2:".println()
    check(part2(example),40)
//    part2(input).println()
 //   benchmark(1000) { part2(input)} // 16.126354ms

}