package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.client.gui.widget.container.HeaderContainer
import cc.turtl.turtlshell.api.client.gui.widget.container.SidebarContainer
import cc.turtl.turtlshell.api.client.gui.widget.container.VerticalScrollContainer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

open class ModalScreen(
    title: Component = Component.translatable("ts.gui.base.title"),
    private val screenW: Int = DEFAULT_GUI_MODAL_WIDTH,
    private val screenH: Int = DEFAULT_GUI_MODAL_HEIGHT,
    private val bgRGB: Int = DEFAULT_GUI_DARK_COLOR.rgb,
    private val accentRGB: Int = DEFAULT_GUI_ACCENT_COLOR.rgb,
) : Screen(title) {

    /**
     * Size/positioning info for this (the parent screen) - Easy access for children widgets.
     */
    protected var screenX: Int = 0
    protected var screenY: Int = 0

    companion object {
        fun open() {
            Minecraft.getInstance().setScreen(ModalScreen())
        }
    }

    override fun init() {
        super.init()
        screenX = (width - screenW) / 2
        screenY = (height - screenH) / 2

        val headerX = screenX
        val headerY = screenY
        val headerW = width
        val headerH = DEFAULT_GUI_HEADER_HEIGHT
        val header = HeaderContainer(headerX, headerY, headerW, headerH, title) { onClose() }

        val sidebarX = screenX
        val sidebarY = screenY + headerH
        val sidebarW = DEFAULT_GUI_SIDEBAR_WIDTH
        val sidebarH = screenH - headerH
        val sidebar = SidebarContainer(sidebarX, sidebarY, sidebarW, sidebarH)

        val bodyX = screenX + sidebarW
        val bodyY = screenY + headerH
        val bodyW = screenW - sidebarW
        val bodyH = screenH - headerH
        val body = VerticalScrollContainer(bodyX, bodyY, bodyW, bodyH)

        addRenderableWidget(header)
        addRenderableWidget(sidebar)
        addRenderableWidget(body)
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        // Fill parent panel bg
        context.fill(screenX, screenY, screenX + screenW, screenY + screenH, bgRGB)
        super.render(context, mouseX, mouseY, delta)
    }

    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}