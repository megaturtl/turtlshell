package cc.turtl.turtlshell.client.integration

import cc.turtl.turtlshell.client.config.TurtlShellConfigClient
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

class ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent ->
            TurtlShellConfigClient.createScreen(parent)
        }
    }
}