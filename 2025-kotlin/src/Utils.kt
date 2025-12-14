import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.text.toCharArray
import kotlin.time.Duration
import kotlin.time.measureTime

typealias Grid = Array<CharArray>
typealias Pos = Pair<Int, Int>

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
fun List<String>.toGrid(): Grid {
    return Array(this.size) { this[it].toCharArray() }
}

fun Grid.get(pos: Pos): Char = this[pos.row()][pos.col()]


// I am sure there is a much nicer way of doing this
fun Grid.findLocationsOf(target: Char): List<Pos> {
    val locations = mutableListOf<Pos>()
    for (r in 0 until this.rowCount) {
        for (c in 0 until this.colCount) {
            if (this[r][c] == target) {
                locations.add(Pair(r, c))
            }
        }
    }
    return locations
}

fun Grid.updatePos(posToUpdate: Pos, newValue: Char) {
    this[posToUpdate.row()][posToUpdate.col()] = newValue
}

fun List<Pos>.printPosToGrid() {
    val posSet = this.toSet()
    (0..posSet.maxOf { it.row() }).forEach { r ->
        (0..posSet.maxOf { it.col() }).forEach { c ->
            if (posSet.contains(r to c)) print("x")
            else print('o')
        }
        println("")
    }
}

fun Grid.print() {
    this.forEach { row ->
        row.forEach { c -> if (c == ' ') print('-') else print(c)}
        println("")
    }
}

fun Grid.findAdjacentPosFor(pos: Pos): List<Pos> {
    return listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1, 0 to 1,
        1 to -1, 1 to 0, 1 to 1
    )
        .map { (x, y) -> (pos.row() + x) to pos.col() + y }
        .filter { pos -> this.validForGrid(pos) }
        .toList()
}

fun Pos.row(): Int = this.first
fun Pos.col(): Int = this.second
val Grid.rowCount: Int get() = size
val Grid.colCount: Int get() = firstOrNull()?.size ?: 0
fun Grid.validForGrid(pos: Pos): Boolean = pos.row() in 0 until rowCount && pos.col() in 0 until colCount
fun Grid.cols(): List<List<Char>> =  (0 until this.colCount).map { c -> (0 until this.rowCount).map { r -> this[r][c] } }
fun Grid.rotateAnticlockwise():Grid = this.cols().let { cols ->  Array(cols.size) { cols.reversed()[it].toCharArray() }

}
