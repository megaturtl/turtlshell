package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_DIVIDER_WIDTH
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_LIGHT_COLOR
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_PADDING
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_WIDTH
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * Sidebar element of the GUI. This usually contains nav links.
 */
class SidebarContainer(
    sidebarX: Int,
    sidebarY: Int,
    sidebarW: Int,
    sidebarH: Int,
) : AbstractContainerWidget(
    sidebarX,
    sidebarY,
    sidebarW,
    sidebarH,
    Component.empty()
) {

    private val testButton = SidebarButton(
        x,
        y + DEFAULT_GUI_PADDING,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    override fun children(): List<GuiEventListener> = listOf(testButton)

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {

        val dividerX = x + width

        guiGraphics.fill(
            dividerX,
            y,
            dividerX + DEFAULT_GUI_DIVIDER_WIDTH,
            y + height,
            DEFAULT_GUI_LIGHT_COLOR.rgb
        )

        testButton.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}