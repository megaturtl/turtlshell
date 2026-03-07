package cc.turtl.turtlshell.util.format

import cc.turtl.turtlshell.util.format.ColorUtils.getGradient
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import java.util.function.Function
import java.util.function.UnaryOperator

/**
 * Provides basic chat component formatting utils.
 */
object ComponentUtils {
    /**
     * Placeholder component for missing or null data.
     */
    val UNKNOWN: Component = createComponent("???", ColorUtils.DARK_GRAY.getRGB())
    val NONE: Component = createComponent("None", ColorUtils.DARK_GRAY.getRGB())
    val SPACE: Component = Component.literal(" ")
    val RESET: Component = Component.literal("").withStyle(ChatFormatting.RESET)

    /**
     * Creates a component with a specific color.
     *
     * Example: `literal("Lvl 50", ColorUtil.GOLD)`
     */
    @JvmOverloads
    fun createComponent(text: Any?, color: Int = ColorUtils.WHITE.getRGB(), bold: Boolean = false): MutableComponent {
        val content = if (text == null) "" else text.toString()

        val component = Component.literal(content)

        return component.withStyle(UnaryOperator { style: Style? ->
            var style = style
            style = style!!.withColor(color).withBold(bold)
            style
        })
    }

    /**
     * Creates a clickable URL component that opens the link in the browser on click.
     */
    fun clickableUrl(url: String): Component {
        return Component.literal(url)
            .withStyle(UnaryOperator { s: Style? ->
                s!!
                    .withClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, url))
                    .withColor(ColorUtils.PINK.getRGB())
                    .withUnderlined(true)
            })
    }

    /**
     * Creates a component from a label and value pair.
     *
     * Example: `label("Ability", "Intimidate")` -> "Ability: Intimidate"
     */
    fun labelled(label: Any, value: Any?): MutableComponent {
        val labelComp = (if (label is Component) label.copy() else Component.literal(label.toString()))
        // If we pass a component label, it gets overridden with gray for label consistency
        labelComp.withColor(ColorUtils.LIGHT_GRAY.getRGB())

        val valueComp = if (value == null) UNKNOWN else (if (value is Component) value else createComponent(
            value.toString(),
            ColorUtils.WHITE.getRGB()
        ))

        return labelComp.append(createComponent(": ", ColorUtils.LIGHT_GRAY.getRGB())).append(valueComp)
    }

    /**
     * Joins items into a single component with separators.
     *
     * Example: `join(list, ", ", item -> literal(item, ColorUtil.RED))`
     */
    fun <E> join(items: Iterable<E?>?, separator: String?, mapper: Function<E?, Component?>): Component {
        if (items == null || !items.iterator().hasNext()) return UNKNOWN

        val result = Component.empty()
        val it: Iterator<E?> = items.iterator()

        while (it.hasNext()) {
            val mapped = mapper.apply(it.next())
            if (mapped != null) {
                result.append(mapped)
                if (it.hasNext()) result.append(createComponent(separator, ColorUtils.DARK_GRAY.getRGB()))
            }
        }
        return result
    }

    /**
     * Creates a component where the text is colored with a multi-point gradient.
     * * @param text   The string to color.
     *
     * @param colors The RGB color stops (0xRRGGBB).
     * @return A MutableComponent containing the gradient text.
     */
    fun gradient(text: String?, vararg colors: Int): MutableComponent {
        if (text == null || text.isEmpty()) return Component.empty()
        if (colors.size == 0) return Component.literal(text)
        if (colors.size == 1) return createComponent(text, colors[0])

        val result = Component.empty()
        val length = text.length

        for (i in 0..<length) {
            // Calculate ratio (0.0 to 1.0) based on character index
            // If length is 1, ratio is 0.
            val ratio = if (length > 1) i.toFloat() / (length - 1) else 0.0f

            val color = getGradient(ratio, *colors)
            result.append(Component.literal(text.get(i).toString()).withColor(color))
        }

        return result
    }
}