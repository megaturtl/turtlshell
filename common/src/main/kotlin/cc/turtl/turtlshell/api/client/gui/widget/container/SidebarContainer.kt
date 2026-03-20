package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_PADDING
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT
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

    val button1Y = y + DEFAULT_GUI_PADDING * 2
    private val button1 = SidebarButton(
        x,
        button1Y,
        width,
        DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    val button2Y = button1Y + DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT + DEFAULT_GUI_PADDING
    private val button2 = SidebarButton(
        x,
        button2Y,
        width,
        DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    val button3Y = button2Y + DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT + DEFAULT_GUI_PADDING
    private val button3 = SidebarButton(
        x,
        button3Y,
        width,
        DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    val button4Y = button3Y + DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT + DEFAULT_GUI_PADDING
    private val button4 = SidebarButton(
        x,
        button4Y,
        width,
        DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    val button5Y = button4Y + DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT + DEFAULT_GUI_PADDING
    private val button5 = SidebarButton(
        x,
        button5Y,
        width,
        DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
        Component.translatable("ts.gui.tab.build"),
        onPress = {},
        SimpleIcons.HAMMER
    )

    override fun children(): List<GuiEventListener> = listOf(button1, button2, button3, button4, button5)

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {
        button1.render(guiGraphics, mouseX, mouseY, partialTick)
        button2.render(guiGraphics, mouseX, mouseY, partialTick)
        button3.render(guiGraphics, mouseX, mouseY, partialTick)
        button4.render(guiGraphics, mouseX, mouseY, partialTick)
        button5.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {}
}