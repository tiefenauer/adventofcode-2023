import java.nio.file.Paths
import kotlin.io.path.readLines

fun String.readLines() = Paths.get("src/main/resources/$this").readLines()