import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.text.toCharArray
import kotlin.time.Duration
import kotlin.time.measureTime

fun readInputOfLines(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().lines()
fun readInputOfCommaSeparated(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().split(",")

fun List<String>.partitionOnEmptyLine(): Pair<List<String>, List<String>>  {
    val indexOfEmptyLine = this.indexOf("")
    if (indexOfEmptyLine==-1) return this to listOf()
    return this.subList(0,this.indexOf("")) to this.subList(this.indexOf("")+1, this.size)
}

fun Any?.println() = println(this)

fun <E> Iterable<E>.indexesOf(predicate: (E) -> Boolean) = mapIndexedNotNull { index, elem -> index.takeIf { predicate(elem) } }

fun benchmark(times: Int = 1000, body: () -> Unit): Duration {
    val duration = measureTime { repeat(times) { body() } }.div(times)
    println("Average Duration: $duration")
    return duration
}

fun check(actual: Any, expected: Any) {
    if (actual != expected) {
        println("Failed: Actual: $actual, Expected: $expected")
        throw AssertionError("Failed: Actual: $actual, Expected: $expected")
    }
}

// Grid functions
// grid[row][column]
fun List<String>.toGrid(): Array<CharArray> {
    return Array(this.size) { this[it].toCharArray() }
}

fun Array<CharArray>.get(pos: Pair<Int, Int>): Char = this[pos.row()][pos.col()]


// I am sure there is a much nicer way of doing this
fun Array<CharArray>.findLocationsOf(target: Char): List<Pair<Int, Int>> {
    val locations = mutableListOf<Pair<Int, Int>>()
    for (r in 0 until this.rowCount) {
        for (c in 0 until this.colCount) {
            if (this[r][c] == target) {
                locations.add(Pair(r, c))
            }
        }
    }
    return locations
}

fun Array<CharArray>.updatePos(posToUpdate: Pair<Int, Int>, newValue: Char) {
    this[posToUpdate.row()][posToUpdate.col()] = newValue
}

fun List<Pair<Int, Int>>.printPosToGrid() {
    val posSet = this.toSet()
    (0..posSet.maxOf { it.row() }).forEach { r ->
        (0..posSet.maxOf { it.col() }).forEach { c ->
            if (posSet.contains(r to c)) print("x")
            else print('o')
        }
        println("")
    }
}

fun Array<CharArray>.print() {
    this.forEach { row ->
        row.forEach { c -> if (c == ' ') print('-') else print(c)}
        println("")
    }
}

fun Array<CharArray>.findAdjacentPosFor(pos: Pair<Int, Int>): List<Pair<Int, Int>> {
    return listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )
        .map { (x, y) -> (pos.row() + x) to pos.col() + y }
        .filter { pos -> this.validForGrid(pos) }
        .toList()
}

fun Pair<Int, Int>.row(): Int = this.first
fun Pair<Int, Int>.col(): Int = this.second
val Array<CharArray>.rowCount: Int get() = size
val Array<CharArray>.colCount: Int get() = firstOrNull()?.size ?: 0
fun Array<CharArray>.validForGrid(pos: Pair<Int, Int>): Boolean = pos.row() in 0 until rowCount && pos.col() in 0 until colCount
fun Array<CharArray>.cols(): List<List<Char>> =  (0 until this.colCount).map { c -> (0 until this.rowCount).map { r -> this[r][c] } }
fun Array<CharArray>.rotateAnticlockwise():Array<CharArray> = this.cols().let { cols ->  Array(cols.size) { cols.reversed()[it].toCharArray() }

}
