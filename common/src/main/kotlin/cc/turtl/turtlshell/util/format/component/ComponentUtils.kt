package cc.turtl.turtlshell.util.format.component

import cc.turtl.turtlshell.util.format.ColorLib
import cc.turtl.turtlshell.util.format.getGradient
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

val UNKNOWN: Component = Component.literal("???").withColor(ColorLib.DARK_GRAY.rgb)
val SPACE: Component = Component.literal(" ")

/**
 * Converts any value to a [Component] using toString(). Preserves [Component]s if passed.
 */
fun componentOf(value: Any): MutableComponent = when (value) {
    is Component -> value.copy()
    is String -> Component.literal(value)
    else -> Component.literal(value.toString())
}

/**
 * Joins a collection of items into a single [Component] with a separator.
 */
fun <E> joinIterableToComponent(
    items: Iterable<E?>?,
    separator: String = ", ",
    separatorColor: Int = ColorLib.DARK_GRAY.rgb,
    mapper: (E?) -> Component? = { e -> e?.let { componentOf(it) } }
): MutableComponent {
    val mapped = items?.mapNotNull { mapper(it) }?.takeIf { it.isNotEmpty() }
        ?: return UNKNOWN.copy()

    val sep = Component.literal(separator).withColor(separatorColor)
    return mapped.drop(1).fold(mapped.first().copy()) { acc, c ->
        acc.append(sep.copy()).append(c)
    }
}

fun <E> Iterable<E?>.joinToComponent(
    separator: String = ", ",
    separatorColor: Int = ColorLib.DARK_GRAY.rgb,
    mapper: (E?) -> Component? = { e -> e?.let { componentOf(it) } }
): MutableComponent {
    return joinIterableToComponent(this, separator, separatorColor, mapper)
}

/**
 * @param text The string to color.
 * @param colors The RGB color stops (0xRRGGBB).
 */
fun createGradientComponent(text: String, vararg colors: Int): MutableComponent {
    if (colors.isEmpty()) return Component.literal(text)
    if (colors.size == 1) return Component.literal(text).withColor(colors[0])

    val lastIndex = (text.length - 1).coerceAtLeast(1).toFloat()
    return text.foldIndexed(Component.empty()) { i, acc, char ->
        val ratio = i / lastIndex
        acc.append(Component.literal(char.toString()).withColor(getGradient(ratio, *colors)))
    }
}