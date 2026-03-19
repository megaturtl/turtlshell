package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.client.gui.widget.container.HeaderWidget
import cc.turtl.turtlshell.api.client.gui.widget.container.SidebarWidget
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

open class ModalScreen(
    title: Component = Component.translatable("ts.gui.base.title"),
    private val panelWidth: Int = DEFAULT_GUI_PANEL_WIDTH,
    private val panelHeight: Int = DEFAULT_GUI_PANEL_HEIGHT,
    private val bgRGB: Int = DEFAULT_GUI_DARK_COLOR.rgb,
    private val accentRGB: Int = DEFAULT_GUI_ACCENT_COLOR.rgb,
) : Screen(title) {

    /**
     * Top-left coordinates of the gui panel, updated automatically during [init].
     * Prefer the content-space properties below for positioning child widgets.
     */
    protected var guiX: Int = 0
    protected var guiY: Int = 0

    companion object {
        fun open() {
            Minecraft.getInstance().setScreen(ModalScreen())
        }
    }

    override fun init() {
        super.init()
        guiX = (this.width - panelWidth) / 2
        guiY = (this.height - panelHeight) / 2

        addRenderableWidget(HeaderWidget(guiX, guiY, panelWidth, title) { onClose() })
        addRenderableWidget(
            SidebarWidget(
                guiX,
                guiY + DEFAULT_GUI_HEADER_HEIGHT + DEFAULT_GUI_DIVIDER_WIDTH,
                panelHeight - DEFAULT_GUI_HEADER_HEIGHT - DEFAULT_GUI_DIVIDER_WIDTH
            )
        )
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        // Fill parent panel bg
        context.fill(guiX, guiY, guiX + panelWidth, guiY + panelHeight, bgRGB)
        super.render(context, mouseX, mouseY, delta)
    }

    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}