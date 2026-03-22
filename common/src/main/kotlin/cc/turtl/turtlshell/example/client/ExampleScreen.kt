package cc.turtl.turtlshell.example.client

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_WIDTH
import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.screen.AbstractModalScreen
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import cc.turtl.turtlshell.api.client.gui.widget.button.TextButton
import cc.turtl.turtlshell.api.client.gui.widget.container.BodyContainer
import cc.turtl.turtlshell.api.client.gui.widget.element.LabelElement
import cc.turtl.turtlshell.api.client.gui.widget.option.TextInputOption
import cc.turtl.turtlshell.api.client.gui.widget.option.ToggleOption
import cc.turtl.turtlshell.example.client.config.ExampleConfigClient
import net.minecraft.network.chat.Component

class ExampleScreen : AbstractModalScreen(
    Component.translatable("ts.gui.examplescreen.title"),
    debug = ExampleConfigClient.get().general.debug) {

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
            addBlock(LabelElement(Component.literal("Display"), theme))
            addBlock(ToggleOption(Component.literal("Show HUD"), true, theme) { })
            addBlock(TextInputOption(Component.literal("Name"), "Steve", theme) { })
            addBlock(LabelElement(Component.literal("Actions"), theme))
            addInline(TextButton(Component.literal("Reset"), theme) { })
            addInline(TextButton(Component.literal("Apply"), theme) { })
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

        repeat(itemCount) { i ->
            addBlock(
                SidebarButton(
                    Component.literal("$label item ${i + 1}"),
                    x, y,
                    DEFAULT_GUI_SIDEBAR_WIDTH,
                    itemHeight,
                    SimpleIcons.MINUS,
                    theme,
                    onPress = {},
                )
            )
        }
    }
}
