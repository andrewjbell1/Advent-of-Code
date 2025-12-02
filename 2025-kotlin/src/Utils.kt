import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInputOfLines(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().lines()
fun readInputOfCommaSeperated(name: String) = Path("2025-kotlin/src/$name.txt").readText().trim().split(",")

fun Any?.println() = println(this)