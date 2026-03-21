package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.texture.Icon
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

class SidebarContainer(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    val theme: GuiTheme,
) : AbstractContainerWidget(x, y, width, height, Component.empty()) {

    private val navButtons = mutableListOf<SidebarButton>()

    /**
     * Adds a button and automatically calculates its vertical position
     * based on the current number of elements.
     */
    fun addNavButton(label: String, icon: Icon, onClick: () -> Unit) {
        val buttonY = this.y + theme.paddingMD + (navButtons.size * (DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT + theme.paddingSM))

        val button = SidebarButton(
            this.x,
            buttonY,
            this.width,
            DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
            Component.literal(label),
            icon,
            theme,
            onPress = { onClick() }
        )

        navButtons.add(button)
    }

    override fun children(): List<GuiEventListener> = navButtons

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        navButtons.forEach { it.render(guiGraphics, mouseX, mouseY, partialTick) }
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        navButtons.forEach { it.updateWidgetNarration(output) }
    }
}