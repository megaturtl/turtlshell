package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_WIDTH
import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.screen.AbstractModalScreen
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import cc.turtl.turtlshell.api.client.gui.widget.container.BodyContainer
import net.minecraft.network.chat.Component

class ExampleScreen : AbstractModalScreen(
    Component.translatable("ts.gui.examplescreen.title")) {

    override fun initContainers() {
        addPage("Home", SimpleIcons.HOME) {
            populateDummyContent(label = "Home", itemCount = 5)
        }
        addPage("Preview", SimpleIcons.EYE) {
            populateDummyContent(label = "Preview", itemCount = 10)
        }
        addPage("Search", SimpleIcons.SEARCH) {
            populateDummyContent(label = "Search", itemCount = 3)
        }
        addPage("Build", SimpleIcons.HAMMER) {
            populateDummyContent(label = "Build", itemCount = 20)
        }
        addPage("Blocklist", SimpleIcons.BLOCKED) {
            populateDummyContent(label = "Blocklist", itemCount = 8)
        }
        addPage("Settings", SimpleIcons.GEAR) {
            populateDummyContent(label = "Settings", itemCount = 6)
        }
    }

    /**
     * Helper to fill the body with a number of placeholder items.
     * Called inside the addPage lambda, so 'this' is the BodyContainer.
     */
    private fun BodyContainer.populateDummyContent(
        label: String,
        itemCount: Int,
    ) {
        val itemHeight = FONT_HEIGHT_PX + theme.paddingLG * 2
        val padding = theme.paddingMD

        repeat(itemCount) { i ->
            val itemY = y + padding + i * (itemHeight + padding)
            addContent(
                SidebarButton(
                    Component.literal("$label item ${i + 1}"),
                    x + padding,
                    itemY,
                    DEFAULT_GUI_SIDEBAR_WIDTH,
                    itemHeight,
                    SimpleIcons.MINUS,
                    theme,
                    onPress = {},
                )
            )
        }

        updateContentHeight(itemCount * (itemHeight + padding) + padding)
    }
}