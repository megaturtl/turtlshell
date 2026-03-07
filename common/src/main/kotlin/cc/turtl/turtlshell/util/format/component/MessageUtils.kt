package cc.turtl.turtlshell.util.format.component

import cc.turtl.turtlshell.TurtlShellConstants.MESSAGE_PREFIX
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player

fun Player.sendEmptyLine() = this.sendSystemMessage(Component.empty())
fun Player.sendPrefixed(message: Component) = this.sendSystemMessage(MESSAGE_PREFIX.append(message))
fun Player.sendPrefixed(message: String) = this.sendPrefixed(componentOf(message))
fun Player.sendSuccess(message: String) = this.sendPrefixed(componentOf(message).green())
fun Player.sendWarning(message: String) = this.sendPrefixed(componentOf(message).yellow())
fun Player.sendError(message: String) = this.sendPrefixed(componentOf(message).red())
fun Player.sendLabelled(label: String, value: Component) = this.sendPrefixed(value.copy().labelled(label))
fun Player.sendLabelled(label: String, value: String) = this.sendLabelled(label, componentOf(value))