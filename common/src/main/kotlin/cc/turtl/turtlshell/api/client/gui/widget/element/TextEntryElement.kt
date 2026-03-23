package cc.turtl.turtlshell.api.client.gui.widget.element

import cc.turtl.turtlshell.api.client.gui.FONT_HEIGHT_PX
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

class TextEntryElement(
    val theme: GuiTheme,
    val placeholder: Component = Component.literal("Enter text"),
    val maxLength: Int = 256,
    minW: Int = 60,
    inlineable: Boolean = false,
    val onChange: (String) -> Unit = {},
) : InteractiveBodyElement(minW, MIN_H, inlineable) {

    private val editBox = EditBox(
        Minecraft.getInstance().font,
        0, 0, minW, MIN_H,
        Component.empty()
    ).also {
        it.setMaxLength(maxLength)
        it.setHint(placeholder)
        it.isBordered = false
        it.setResponder(onChange)
        it.setTextColor(theme.textColor.rgb)
        it.setTextColorUneditable(theme.subtleTextColor.rgb)
    }

    val value: String get() = editBox.value

    private fun syncEditBox() {
        editBox.x = x + theme.paddingSM
        editBox.y = y + (height - FONT_HEIGHT_PX) / 2
        editBox.width = width - theme.paddingSM * 2
        editBox.height = FONT_HEIGHT_PX
    }

    override fun isFocused(): Boolean = editBox.isFocused
    override fun setFocused(focused: Boolean) {
        editBox.isFocused = focused
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (!isMouseOver(mouseX, mouseY)) return false
        // forward click to editBox using its own y so it registers correctly
        return editBox.mouseClicked(mouseX, editBox.y.toDouble(), button)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean =
        editBox.keyPressed(keyCode, scanCode, modifiers)

    override fun charTyped(codePoint: Char, modifiers: Int): Boolean =
        editBox.charTyped(codePoint, modifiers)

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        syncEditBox()
        guiGraphics.fill(x, y, x + width, y + height, theme.medColor.rgb)
        guiGraphics.renderOutline(x, y, width, height,
            if (editBox.isFocused) theme.lightColor.rgb else theme.medColor.rgb)

        editBox.render(guiGraphics, mouseX, mouseY, 0f)
    }

    companion object {
        const val MIN_H = 16
    }
}