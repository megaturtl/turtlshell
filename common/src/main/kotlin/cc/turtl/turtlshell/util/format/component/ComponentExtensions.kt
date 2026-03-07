package cc.turtl.turtlshell.util.format.component

import cc.turtl.turtlshell.util.format.ColorLib
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStack
import java.awt.Color

fun MutableComponent.labelled(
    label: Component,
    separatorColor: Int = ColorLib.LIGHT_GRAY.rgb
): MutableComponent = label.copy()
    .append(Component.literal(": ").withColor(separatorColor))
    .append(copy())

fun MutableComponent.labelled(
    label: String,
    labelColor: Int = ColorLib.LIGHT_GRAY.rgb
): MutableComponent = labelled(
    componentOf(label).withColor(labelColor),
    labelColor
)

fun MutableComponent.withGradient(): MutableComponent =
    createGradientComponent(this.string, ColorLib.RED.rgb, ColorLib.YELLOW.rgb, ColorLib.GREEN.rgb)

fun MutableComponent.underline(): MutableComponent = also { it.style = it.style.withUnderlined(true) }
fun MutableComponent.bold(): MutableComponent = also { it.style = it.style.withBold(true) }
fun MutableComponent.italic(): MutableComponent = also { it.style = it.style.withItalic(true) }
fun MutableComponent.strikethrough(): MutableComponent = also { it.style = it.style.withStrikethrough(true) }
fun MutableComponent.obfuscate(): MutableComponent = also { it.style = it.style.withObfuscated(true) }
fun MutableComponent.colored(color: Int): MutableComponent = this.withColor(color)
fun MutableComponent.colored(color: Color): MutableComponent = this.withColor(color.rgb)

fun MutableComponent.white(): MutableComponent = colored(ColorLib.WHITE)
fun MutableComponent.black(): MutableComponent = colored(ColorLib.BLACK)
fun MutableComponent.lightGray(): MutableComponent = colored(ColorLib.LIGHT_GRAY)
fun MutableComponent.darkGray(): MutableComponent = colored(ColorLib.DARK_GRAY)
fun MutableComponent.red(): MutableComponent = colored(ColorLib.RED)
fun MutableComponent.orange(): MutableComponent = colored(ColorLib.ORANGE)
fun MutableComponent.yellow(): MutableComponent = colored(ColorLib.YELLOW)
fun MutableComponent.green(): MutableComponent = colored(ColorLib.GREEN)
fun MutableComponent.blue(): MutableComponent = colored(ColorLib.BLUE)
fun MutableComponent.purple(): MutableComponent = colored(ColorLib.PURPLE)
fun MutableComponent.pink(): MutableComponent = colored(ColorLib.PINK)

fun MutableComponent.clickCommand(command: String): MutableComponent =
    also { it.style = it.style.withClickEvent(ClickEvent(ClickEvent.Action.RUN_COMMAND, command)) }

fun MutableComponent.clickUrl(url: String): MutableComponent =
    also { it.style = it.style.withClickEvent(ClickEvent(ClickEvent.Action.OPEN_URL, url)) }

fun MutableComponent.hoverText(string: String): MutableComponent =
    also { it.style = it.style.withHoverEvent(hoverEvent(string)) }

fun MutableComponent.hoverText(comp: Component): MutableComponent =
    also { it.style = it.style.withHoverEvent(hoverEvent(comp)) }

fun MutableComponent.hoverItem(item: ItemStack): MutableComponent =
    also { it.style = it.style.withHoverEvent(hoverEvent(item)) }

private fun hoverEvent(text: Component): HoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, text)
private fun hoverEvent(text: String): HoverEvent = hoverEvent(componentOf(text))
private fun hoverEvent(item: ItemStack): HoverEvent =
    HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackInfo(item))