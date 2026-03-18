package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.core.format.ColorLib
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

open class BaseGui(
    title: Component = Component.translatable("ts.gui.base.title"),
    private val baseWidth: Int = 380,
    private val baseHeight: Int = 200,
    private val bgRGB: Int = ColorLib.DARK_SLATE.rgb,
) : Screen(title) {

    /**
     * Top-left coordinates of the gui panel, updated automatically during [init].
     * Prefer the content-space properties below for positioning child widgets.
     */
    protected var guiX: Int = 0
    protected var guiY: Int = 0

    /** Top-left corner and width of the sidebar panel. */
    protected val sidebarX get() = guiX + GUI_PADDING
    protected val sidebarY get() = guiY + HEADER_HEIGHT + GUI_PADDING
    protected val sidebarWidth get() = SIDEBAR_WIDTH

    /** Top-left corner and width of the main content panel. */
    protected val contentX get() = guiX + GUI_PADDING + SIDEBAR_WIDTH + DIVIDER_WIDTH + GUI_PADDING
    protected val contentY get() = guiY + HEADER_HEIGHT + GUI_PADDING
    protected val contentWidth get() = baseWidth - SIDEBAR_WIDTH - DIVIDER_WIDTH - GUI_PADDING * 3

    companion object {
        /** Inset applied to content and header elements within the panel. */
        const val GUI_PADDING = 2

        /** Height of the header section (includes title and close button). */
        const val HEADER_HEIGHT = 18

        /** Width of the sidebar panel. */
        const val SIDEBAR_WIDTH = 80

        /** Width of the vertical divider between sidebar and content. */
        const val DIVIDER_WIDTH = 1

        private val COLOR_TITLE   = ColorLib.OFF_WHITE
        private val COLOR_DIVIDER = ColorLib.LIGHT_SLATE

        fun open() {
            Minecraft.getInstance().setScreen(BaseGui())
        }
    }

    override fun init() {
        super.init()
        guiX = (this.width - baseWidth) / 2
        guiY = (this.height - baseHeight) / 2

        addCloseButton()
    }

    private fun addCloseButton() {
        val btnX = guiX + baseWidth - CloseButton.SIZE - GUI_PADDING
        val btnY = guiY + GUI_PADDING
        addRenderableWidget(CloseButton(btnX, btnY) { onClose() })
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        context.fill(guiX, guiY, guiX + baseWidth, guiY + baseHeight, bgRGB)

        renderHeader(context)
        renderDividers(context)

        super.render(context, mouseX, mouseY, delta)
    }

    private fun renderHeader(context: GuiGraphics) {
        val titleX = guiX + GUI_PADDING + 2
        val titleY = guiY + (HEADER_HEIGHT - font.lineHeight) / 2 + 2

        context.drawString(
            font,
            title,
            titleX,
            titleY,
            COLOR_TITLE.rgb,
            false,
        )

        val dividerY = guiY + HEADER_HEIGHT
        context.fill(guiX, dividerY, guiX + baseWidth, dividerY + DIVIDER_WIDTH, COLOR_DIVIDER.rgb)
    }

    private fun renderDividers(context: GuiGraphics) {
        val dividerX = guiX + GUI_PADDING + SIDEBAR_WIDTH + GUI_PADDING
        val dividerTop = guiY + HEADER_HEIGHT
        val dividerBottom = guiY + baseHeight
        context.fill(dividerX, dividerTop, dividerX + DIVIDER_WIDTH, dividerBottom, COLOR_DIVIDER.rgb)
    }

    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}