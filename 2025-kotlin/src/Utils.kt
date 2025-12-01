import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().lines()

fun Any?.println() = println(this)