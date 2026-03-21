package cc.turtl.turtlshell.api.client.gui

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.Font
import net.minecraft.network.chat.Component

/**
 * Draws text at the given screen position with a scale factor applied.
 */
fun GuiGraphics.drawScaledText(
    font: Font,
    text: Component,
    x: Int,
    y: Int,
    scale: Float,
    rgb: Int,
    shadow: Boolean = false,
) {
    pose().pushPose()
    pose().scale(scale, scale, 1f)
    drawString(font, text, (x / scale).toInt(), (y / scale).toInt(), rgb, shadow)
    pose().popPose()
}

/**
 * Draws text vertically centred at the given screen position and optionally scaled.
 *
 * Pass [containerH] to vertically centre the text within a known height,
 * such as a button or row. In this case x should be the leftmost point of the text
 * and [y] should be the top of the container.
 */
fun GuiGraphics.drawVerticallyCentredText(
    font: Font,
    text: Component,
    x: Int,
    y: Int,
    containerH: Int,
    rgb: Int,
    scale: Float = 1.0F,
    shadow: Boolean = false,
) {
    val shadowOffset = if (shadow) 0 else 1
    val scaledLineHeight = ((font.lineHeight - 1 - shadowOffset) * scale).toInt()
    val scaledY = y + (containerH - scaledLineHeight) / 2

    drawScaledText(font, text, x, scaledY, scale, rgb, shadow)
}