package cc.turtl.turtlshell.util.format

import kotlin.time.Duration.Companion.milliseconds

fun String.capitalizeFirst(): String = this.lowercase().replaceFirstChar { it.uppercaseChar() }

fun formatPercentage(value: Double): String = "%.2f%%".format(value * 100.0)
fun formatDecimal(value: Double, places: Int = 2): String = "%.${places}f".format(value)

fun formatDuration(milliseconds: Long): String =
    milliseconds.milliseconds.toComponents { hours, minutes, seconds, _ ->
        buildString {
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            append("${seconds}s")
        }
    }

fun bytesSize(bytes: Long): String {
    if (bytes < 0) return "N/A"

    val kb = 1024L
    val mb = kb * 1024
    val gb = mb * 1024

    return when {
        bytes < kb -> "$bytes B"
        bytes < mb -> "%.1f KB".format(bytes / kb.toDouble())
        bytes < gb -> "%.1f MB".format(bytes / mb.toDouble())
        else -> "%.2f GB".format(bytes / gb.toDouble())
    }
}

/**
 * Converts snake_case or SCREAMING_SNAKE_CASE to Title Case.
 * Example: "water_type" -> "Water Type"
 */
fun cleanSnakeCase(snakeCase: String): String =
    snakeCase
        .split("_")
        .filter { it.isNotEmpty() }
        .joinToString(" ") { word -> word.capitalizeFirst() }