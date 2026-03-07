package cc.turtl.turtlshell.util.format

import cc.turtl.turtlshell.TurtlShellConstants.MESSAGE_PREFIX
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component

/**
 * Executes a command string as the player. Slash prefix isn't needed.
 */
fun LocalPlayer.executeCommand(command: String) = this.connection.sendCommand(command.removePrefix("/"))

fun LocalPlayer.send(message: Component) = sendSystemMessage(message)
fun LocalPlayer.sendEmptyLine(message: Component) = send(Component.empty())
fun LocalPlayer.sendPrefixed(message: Component) = send(Component.empty().append(MESSAGE_PREFIX).append(message))
fun LocalPlayer.sendPrefixed(message: String) = sendPrefixed(Component.literal(message))
fun LocalPlayer.sendSuccess(message: String) = sendPrefixed(Component.literal(message).withColor(ColorLib.GREEN.rgb))
fun LocalPlayer.sendWarning(message: String) = sendPrefixed(Component.literal(message).withColor(ColorLib.YELLOW.rgb))
fun LocalPlayer.sendEmptyLine(message: String) = sendPrefixed(Component.literal(message).withColor(ColorLib.YELLOW.rgb))
fun LocalPlayer.sendError(message: String) = sendPrefixed(Component.literal(message).withColor(ColorLib.RED.rgb))
fun LocalPlayer.sendLabelled(label: String, value: Component?) = sendPrefixed(labelled(label, value))