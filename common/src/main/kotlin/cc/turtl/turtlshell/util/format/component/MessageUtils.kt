package cc.turtl.turtlshell.util.format.component

import cc.turtl.turtlshell.TurtlShellConstants.MESSAGE_PREFIX
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component

/**
 * Executes a command string as the player. Slash prefix isn't needed.
 */
fun LocalPlayer.executeCommand(command: String) = this.connection.sendCommand(command.removePrefix("/"))

fun LocalPlayer.send(message: Component) = sendSystemMessage(message)
fun LocalPlayer.sendEmptyLine() = send(Component.empty())
fun LocalPlayer.sendPrefixed(message: Component) = send(MESSAGE_PREFIX.append(message))
fun LocalPlayer.sendPrefixed(message: String) = sendPrefixed(componentOf(message))
fun LocalPlayer.sendSuccess(message: String) = sendPrefixed(componentOf(message).green())
fun LocalPlayer.sendWarning(message: String) = sendPrefixed(componentOf(message).yellow())
fun LocalPlayer.sendError(message: String) = sendPrefixed(componentOf(message).red())
fun LocalPlayer.sendLabelled(label: String, value: Component) = sendPrefixed(value.copy().labelled(label))
fun LocalPlayer.sendLabelled(label: String, value: String) = sendLabelled(label, componentOf(value))