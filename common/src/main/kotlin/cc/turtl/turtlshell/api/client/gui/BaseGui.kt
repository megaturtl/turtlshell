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

    /**
     * Top-left corner of the padded content area, below the header divider.
     * Use these to position child widgets.
     */
    protected val contentX get() = guiX + GUI_PADDING
    protected val contentY get() = guiY + HEADER_HEIGHT + GUI_PADDING

    /**
     * Usable width of the content area after padding is applied on both sides.
     */
    protected val contentWidth get() = baseWidth - GUI_PADDING * 2

    companion object {
        /** Inset applied to content and header elements within the panel. */
        const val GUI_PADDING = 2

        /** Height of the header section (includes title and close button). */
        const val HEADER_HEIGHT = 18

        private val COLOR_TITLE = ColorLib.OFF_WHITE
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

        super.render(context, mouseX, mouseY, delta)
    }

    private fun renderHeader(context: GuiGraphics) {
        val titleX = guiX + GUI_PADDING + 2 // compensate for button padding
        val titleY = guiY + (HEADER_HEIGHT - font.lineHeight) / 2 + 2 // compensate for button padding

        context.drawString(
            font,
            title,
            titleX,
            titleY,
            COLOR_TITLE.rgb,
            false,
        )

        val dividerY = guiY + HEADER_HEIGHT
        context.fill(guiX, dividerY, guiX + baseWidth, dividerY + 1, COLOR_DIVIDER.rgb)
    }

    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}