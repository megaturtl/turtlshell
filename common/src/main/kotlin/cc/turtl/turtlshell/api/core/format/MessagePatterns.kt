package cc.turtl.turtlshell.api.core.format

import cc.turtl.turtlshell.impl.buildComponent
import net.minecraft.network.chat.MutableComponent

object MessagePatterns {
    fun success(messageKey: String) = buildComponent {
        prefix()
        component { translatable(messageKey); color(ColorLib.GREEN.rgb) }
    }

    fun warning(messageKey: String) = buildComponent {
        prefix()
        component { translatable(messageKey); color(ColorLib.YELLOW.rgb) }
    }

    fun error(messageKey: String) = buildComponent {
        prefix()
        component { translatable(messageKey); color(ColorLib.RED.rgb) }
    }

    fun title(titleKey: String, subtitleKey: String? = null) = buildComponent {
        newline()
        prefix()
        component { translatable(titleKey); color(ColorLib.MINT.rgb) }
        if (subtitleKey != null) {
            component { text(" - "); color(ColorLib.LIGHT_GRAY.rgb) }
            component { translatable(subtitleKey); color(ColorLib.LIGHT_GRAY.rgb) }
        }
    }

    fun fieldLine(labelKey: String, value: Any, depth: Int = 1) = buildComponent {
        newline()
        prefix()
        space(depth * 2)
        field(labelKey, value)
    }

    fun <T> rankedList(
        items: List<T>,
        name: (T) -> String,
        value: (T) -> String,
        depth: Int = 1
    ): MutableComponent = buildComponent {
        items.forEachIndexed { i, item ->
            append(rankedLine(i + 1, name(item), value(item), depth))
        }
    }

    private fun rankedLine(rank: Int, name: String, value: String, depth: Int) = buildComponent {
        newline()
        prefix()
        space(depth * 2)
        component { text("#$rank"); color(ColorLib.AQUA.rgb) }
        component { text(" » "); color(ColorLib.DARK_GRAY.rgb) }
        component { text(name); color(ColorLib.MINT.rgb) }
        component { text(" - "); color(ColorLib.DARK_GRAY.rgb) }
        component { text(value) }
    }

    fun helpList(alias: String, commands: List<String>, depth: Int = 1): MutableComponent = buildComponent {
        commands.forEach { cmd ->
            append(helpLine(alias, cmd, "ts.command.$cmd.desc", depth))
        }
    }
    private fun helpLine(alias: String, command: String, descKey: String, depth: Int) = buildComponent {
        newline()
        prefix()
        space(depth * 2)
        text("/$alias $command - ")
        translatable(descKey)
    }
}