package cc.turtl.turtlshell.api.client.gui.widget.element

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents

class ToggleElement(
    val label: Component,
    val theme: GuiTheme,
    initialValue: Boolean = false,
    minW: Int = 60,
    inlineable: Boolean = true,
    val onToggle: (Boolean) -> Unit = {},
) : InteractiveBodyElement(minW, MIN_H, inlineable) {

    var value: Boolean = initialValue
        private set

    private var hovered = false

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        hovered = isMouseOver(mouseX.toDouble(), mouseY.toDouble())

        val bgColor = when {
            value -> theme.accentColor.rgb
            hovered -> theme.lightColor.rgb
            else -> theme.medColor.rgb
        }
        guiGraphics.fill(x, y, x + width, y + height, bgColor)

        guiGraphics.drawVerticallyCentredText(
            text = label,
            x = x + theme.paddingSM,
            y = y,
            containerH = height,
            rgb = if (value) theme.darkColor.rgb else theme.textColor.rgb,
            maxCharacterWidth = width - theme.paddingSM * 2,
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            Minecraft.getInstance().soundManager.play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f)
            )
            value = !value
            onToggle(value)
            return true
        }
        return false
    }

    companion object {
        const val MIN_H = 16
    }
}