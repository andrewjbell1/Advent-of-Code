import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInputOfLines(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().lines()
fun readInputOfCommaSeperated(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().split(",")

fun Any?.println() = println(this)

fun <E> Iterable<E>.indexesOf(predicate: (E) -> Boolean) = mapIndexedNotNull{ index, elem -> index.takeIf{ predicate(elem) } }


fun check(actual: Any, expected: Any) {
    if (actual != expected) {
        println("Failed: Actual: $actual, Expected: $expected")
        throw AssertionError("Failed: Actual: $actual, Expected: $expected")
    }
}

