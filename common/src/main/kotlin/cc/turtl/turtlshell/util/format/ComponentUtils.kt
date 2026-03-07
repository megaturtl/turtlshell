package cc.turtl.turtlshell.util.format

import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style

val UNKNOWN: Component = Component.literal("???").withColor(ColorLib.DARK_GRAY.rgb)
val NONE: Component = Component.literal("None").withColor(ColorLib.DARK_GRAY.rgb)
val SPACE: Component = Component.literal(" ")

/**
 * Creates a clickable URL component.
 */
fun clickableUrl(url: String): MutableComponent =
    Component.literal(url).withStyle(
        Style.EMPTY
            .withClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, url))
            .withColor(ColorLib.PINK.rgb)
            .withUnderlined(true)
    )

fun labelled(
    label: Component,
    value: Component?,
    separatorColor: Int = ColorLib.LIGHT_GRAY.rgb,
    fallback: Component = UNKNOWN
): MutableComponent = label.copy()
    .append(Component.literal(": ").withColor(separatorColor))
    .append(value?.copy() ?: fallback.copy())

fun labelled(
    label: String,
    value: String?,
    labelColor: Int = ColorLib.LIGHT_GRAY.rgb,
    valueColor: Int = ColorLib.DARK_GRAY.rgb
): MutableComponent = labelled(
    Component.literal(label).withColor(labelColor),
    value?.let { Component.literal(it).withColor(valueColor) },
    labelColor
)

fun labelled(
    label: String,
    value: Component?,
    labelColor: Int = ColorLib.LIGHT_GRAY.rgb,
): MutableComponent = labelled(
    Component.literal(label).withColor(labelColor),
    value,
    labelColor
)

/**
 * Joins items into a single component with separators.
 *
 * Example: `joined(list, ", ") { literal(it, ColorUtil.RED) }`
 */
fun <E> joined(
    items: Iterable<E?>?,
    separator: String = ", ",
    separatorColor: Int = ColorLib.DARK_GRAY.rgb,
    mapper: (E?) -> Component?
): MutableComponent {
    val mapped = items?.mapNotNull { mapper(it) }?.takeIf { it.isNotEmpty() }
        ?: return UNKNOWN.copy()

    val sep = Component.literal(separator).withColor(separatorColor)
    return mapped.drop(1).fold(Component.empty().append(mapped.first())) { acc, c ->
        acc.append(sep.copy()).append(c)
    }
}

/**
 * Creates a component where the text is colored with a multipoint gradient.
 * @param text The string to color.
 * @param colors The RGB color stops (0xRRGGBB).
 * @return A MutableComponent with gradient-colored text.
 */
fun gradient(text: String?, vararg colors: Int): MutableComponent {
    if (text.isNullOrEmpty()) return Component.empty()
    if (colors.isEmpty()) return Component.literal(text)
    if (colors.size == 1) return Component.literal(text).withColor(colors[0])

    val result = Component.empty()

    text.forEachIndexed { i, char ->
        val ratio = i.toFloat() / (text.length - 1).coerceAtLeast(1)
        result.append(Component.literal(char.toString()).withColor(getGradient(ratio, *colors)))
    }

    return result
}