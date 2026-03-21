package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_WIDTH
import cc.turtl.turtlshell.api.client.gui.screen.AbstractModalScreen
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import net.minecraft.network.chat.Component

class ExampleScreen : AbstractModalScreen(
    Component.translatable("ts.gui.examplescreen.title")) {

    override fun initContainers() {
        populateDummySidebar()
        populateDummyContent()
    }

    private fun populateDummySidebar() {
        val navItems = listOf(
            "Home" to SimpleIcons.HOME,
            "Preview" to SimpleIcons.EYE,
            "Search" to SimpleIcons.SEARCH,
            "Build" to SimpleIcons.HAMMER,
            "Blocklist" to SimpleIcons.BLOCKED,
            "Settings" to SimpleIcons.GEAR
        )

        navItems.forEachIndexed { i, (label, icon) ->
            sidebar.addNavButton(label, icon) {}
        }
    }

    private fun populateDummyContent() {
        val itemHeight = 20
        val itemCount = 20
        val padding = theme.paddingMD

        repeat(itemCount) { i ->
            val itemY = body.y + padding + i * (itemHeight + padding)
            body.addContent(
                SidebarButton(
                    body.x + padding,
                    itemY,
                    DEFAULT_GUI_SIDEBAR_WIDTH,
                    DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
                    Component.literal("Item ${i + 1}"),

                    SimpleIcons.MINUS,
                    theme,
                    onPress = {},
                )
            )
        }

        body.updateContentHeight(itemCount * (itemHeight + padding) + padding)
    }
}