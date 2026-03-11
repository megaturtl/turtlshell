package cc.turtl.turtlshell.api.client.util

import net.minecraft.client.player.LocalPlayer

fun LocalPlayer.executeCommand(command: String) = this.connection.sendCommand(command.removePrefix("/"))