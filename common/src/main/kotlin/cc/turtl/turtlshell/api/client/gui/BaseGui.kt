package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.core.format.ColorLib
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

open class BaseGui(
    title: Component = Component.translatable("chiselport.gui.turtlbase.title"),
    private val baseWidth: Int = 380,
    private val baseHeight: Int = 200,
    private val bgRGB: Int = ColorLib.DARK_SLATE.rgb
) : Screen(title) {

    companion object {
        const val CLOSE_BUTTON_MARGIN = 2

        fun open() {
            Minecraft.getInstance().setScreen(BaseGui())
        }
    }

    /**
     * Top-left coordinates of the gui panel, updated automatically during [init].
     * Use to position child widgets.
     */
    protected var guiX: Int = 0
    protected var guiY: Int = 0

    override fun init() {
        super.init()
        // Centre the gui on the screen
        guiX = (this.width - baseWidth) / 2
        guiY = (this.height - baseHeight) / 2

        addCloseButton()
    }

    private fun addCloseButton() {

        val btnX = guiX + baseWidth - CloseButton.SIZE - CLOSE_BUTTON_MARGIN
        val btnY = guiY + CLOSE_BUTTON_MARGIN
        addRenderableWidget(CloseButton(btnX, btnY) { onClose() })
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        // Draw the centered gui panel
        context.fill(guiX, guiY, guiX + baseWidth, guiY + baseHeight, bgRGB)

        // Render all child widgets (buttons, edit boxes, etc.)
        super.render(context, mouseX, mouseY, delta)
    }

    // Prevent MC's default blurred background overlapping the custom panel
    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}