package cc.turtl.turtlshell.impl

import cc.turtl.turtlshell.BuildDetails.MOD_DISPLAY_NAME
import cc.turtl.turtlshell.api.core.format.ColorLib
import cc.turtl.turtlshell.api.core.format.ColorLib.Gradients
import cc.turtl.turtlshell.api.core.util.getRatioColor
import net.minecraft.network.chat.*
import net.minecraft.world.item.ItemStack
import java.awt.Color

@DslMarker
annotation class ComponentDsl

val UNKNOWN: Component = Component.literal("???").withColor(ColorLib.DARK_GRAY.rgb)
val SPACE: Component = Component.literal(" ")
val NEW_LINE: Component = Component.literal("\n")

val MOD_PREFIX: Component = buildComponent {
    component { text("["); color(ColorLib.DARK_GRAY.rgb) }
    component { text("\uD83D\uDEE0"); color(ColorLib.MINT.rgb); bold }
    component { text("]"); color(ColorLib.DARK_GRAY.rgb) }
    hoverComponent { text(MOD_DISPLAY_NAME); color(ColorLib.MINT.rgb) }
    space()
}

@ComponentDsl
class ComponentBuilder(private val indentDepth: Int = 0) {
    private val children = mutableListOf<MutableComponent>()

    var color: Int = 0xFFFFFF
    var isBold = false
    var isItalic = false
    var isUnderlined = false
    var isStrikethrough = false
    var isObfuscated = false
    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent? = null

    fun text(content: Any) {
        when (content) {
            is Component -> append(content)
            else -> children.add(Component.literal(content.toString()))
        }
    }

    fun translatable(key: String, vararg args: Any) {
        children.add(Component.translatable(key, *args))
    }

    fun append(component: Component) = children.add(component as MutableComponent)
    fun component(block: ComponentBuilder.() -> Unit) = children.add(buildComponent(indentDepth, block))

    // Layout
    fun prefix() = children.add(MOD_PREFIX.copy())
    fun newline(count: Int = 1) = repeat(count) { children.add(NEW_LINE.copy()) }
    fun space(count: Int = 1) = repeat(count) { children.add(SPACE.copy()) }

    // Fields - label uses a translation key, value can be a Component or anything else
    fun field(
        labelKey: String,
        value: Any,
        labelColor: Int = ColorLib.LIGHT_GRAY.rgb,
        valueColor: Int = ColorLib.WHITE.rgb
    ) {
        component { translatable(labelKey); color(labelColor) }
        component { text(": "); color(labelColor) }
        when (value) {
            is Component -> append(value)
            else -> component { text(value); color(valueColor) }
        }
    }

    // Styling
    val bold get() = run { isBold = true }
    val italic get() = run { isItalic = true }
    val underlined get() = run { isUnderlined = true }
    val strikethrough get() = run { isStrikethrough = true }
    val obfuscated get() = run { isObfuscated = true }

    // Colors
    fun color(value: Color) {
        color = value.rgb
    }

    fun color(value: Int) {
        color = value
    }

    fun ratioColor(ratio: Float, vararg colors: Int = Gradients.POSITIVE.rgb()) {
        color(getRatioColor(ratio, *colors))
    }

    fun textGradient(text: String, vararg colors: Int) {
        text.forEachIndexed { i, char ->
            val ratio = if (text.length <= 1) 0f else i.toFloat() / (text.length - 1)
            children.add(Component.literal(char.toString()).withColor(getRatioColor(ratio, *colors)))
        }
    }

    // Hover
    fun hoverItem(item: ItemStack) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackInfo(item))
    }

    fun hoverComponent(block: ComponentBuilder.() -> Unit) {
        hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, buildComponent(indentDepth, block))
    }

    // Click
    fun clickCommand(command: String) {
        clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
    }

    fun clickUrl(url: String) {
        clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, url)
    }

    fun clickSuggest(text: String) {
        clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text)
    }

    fun clickCopy(text: String) {
        clickEvent = ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text)
    }

    fun <E> joinIterable(
        items: Iterable<E?>?,
        separator: String = ", ",
        separatorColor: Int = ColorLib.DARK_GRAY.rgb,
        mapper: ComponentBuilder.(E?) -> Unit
    ) {
        val components = items
            ?.map { buildComponent(indentDepth) { mapper(it) } }
            ?.filter { it.string.isNotEmpty() }
            ?.takeIf { it.isNotEmpty() }
            ?: return

        val sep = buildComponent { text(separator); color(separatorColor) }
        components.forEachIndexed { i, c ->
            children.add(c)
            if (i < components.lastIndex) children.add(sep.copy())
        }
    }

    internal fun buildStyle(): Style = Style.EMPTY
        .withColor(color)
        .withBold(isBold.orNull())
        .withItalic(isItalic.orNull())
        .withUnderlined(isUnderlined.orNull())
        .withStrikethrough(isStrikethrough.orNull())
        .withObfuscated(isObfuscated.orNull())
        .withClickEvent(clickEvent)
        .withHoverEvent(hoverEvent)

    fun build(): MutableComponent =
        Component.empty().setStyle(buildStyle()).also { c -> children.forEach(c::append) }
}

fun buildComponent(indentDepth: Int = 0, block: ComponentBuilder.() -> Unit): MutableComponent =
    ComponentBuilder(indentDepth).apply(block).build()

private fun Boolean.orNull(): Boolean? = takeIf { it }