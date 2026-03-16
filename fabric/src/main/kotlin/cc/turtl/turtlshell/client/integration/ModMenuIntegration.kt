package cc.turtl.turtlshell.client.integration

import cc.turtl.turtlshell.example.client.config.ExampleConfigClient
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi

object ModMenuIntegration : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory { parent ->
            ExampleConfigClient.createScreen(parent)
        }
    }
}