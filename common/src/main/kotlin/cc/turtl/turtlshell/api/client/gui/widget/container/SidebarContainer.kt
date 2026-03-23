package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
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

    val buttonCount: Int get() = navButtons.size

    /**
     * Adds a nav button and automatically calculates its vertical position.
     */
    fun addNavButton(label: Component, icon: Icon, onPress: () -> Unit) {
        val index = navButtons.size
        val buttonH = FONT_HEIGHT_PX + theme.paddingLG * 2
        val buttonY = this.y + theme.paddingMD + (index * buttonH)

        val button = SidebarButton(
            label,
            this.x,
            buttonY,
            this.width,
            buttonH,

            icon,
            theme,
            onPress = {
                setActiveButton(index)
                onPress()
            }
        )

        navButtons.add(button)
    }

    /** Sets the button at [index] to active and deactivates all others. */
    fun setActiveButton(index: Int) {
        navButtons.forEachIndexed { i, btn -> btn.buttonActive = i == index }
    }

    override fun children(): List<GuiEventListener> = navButtons

    override fun renderWidget(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        navButtons.forEach { it.render(guiGraphics, mouseX, mouseY, partialTick) }
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        navButtons.forEach { it.updateWidgetNarration(output) }
    }
}