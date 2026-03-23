package cc.turtl.turtlshell.api.client.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

/** This counts pixel height per 'GUI scale' level
 *
 * For example at GUI Scale 2 default font is 14 pixels tall
 * and unifont is 10 pixels tall (2 pixels gap above and below so it's centred)
 */
const val FONT_HEIGHT_PX = 7

private fun constrainedExtraScale(textWidth: Int, maxWidth: Int) =
    if (textWidth <= maxWidth) 1f else maxWidth / textWidth.toFloat()

/**
 * Draws text at the given screen position.
 *
 * @param centered Whether to centre the text horizontally around [x].
 * @param pMouseX Mouse x, used to trigger hover tooltips attached to the component.
 * @param pMouseY Mouse y, used to trigger hover tooltips attached to the component.
 * @return True if the text is hovered.
 */
fun GuiGraphics.drawText(
    text: Component,
    x: Int,
    y: Int,
    rgb: Int,
    shadow: Boolean = false,
    centered: Boolean = false,
    pMouseX: Int? = null,
    pMouseY: Int? = null,
): Boolean {
    val fontRenderer = Minecraft.getInstance().font
    val drawX = if (centered) x - fontRenderer.width(text) / 2 else x
    drawString(fontRenderer, text, drawX, y, rgb, shadow)

    if (pMouseX == null || pMouseY == null) return false

    val hovered = pMouseX in drawX until (drawX + fontRenderer.width(text))
            && pMouseY in y until (y + FONT_HEIGHT_PX)
    if (hovered) renderComponentHoverEffect(fontRenderer, text.style, pMouseX, pMouseY)
    return hovered
}

/**
 * Draws text at the given screen position with a uniform scale applied.
 *
 * @param maxCharacterWidth If the rendered text is wider than this, it will be scaled down to fit.
 * @param centered Whether to centre the text horizontally around [x].
 * @param pMouseX Mouse x, used to trigger hover tooltips attached to the component.
 * @param pMouseY Mouse y, used to trigger hover tooltips attached to the component.
 */
fun GuiGraphics.drawScaledText(
    text: Component,
    x: Int,
    y: Int,
    scale: Float,
    rgb: Int,
    shadow: Boolean = false,
    centered: Boolean = false,
    maxCharacterWidth: Int = Int.MAX_VALUE,
    pMouseX: Int? = null,
    pMouseY: Int? = null,
) {
    val extraScale = constrainedExtraScale(Minecraft.getInstance().font.width(text), maxCharacterWidth)
    val totalScale = scale * extraScale

    pose().pushPose()
    pose().scale(totalScale, totalScale, 1f)
    drawText(
        text = text,
        x = (x / totalScale).toInt(),
        y = (y / totalScale).toInt(),
        rgb = rgb,
        shadow = shadow,
        centered = centered,
        pMouseX = pMouseX,
        pMouseY = pMouseY,
    )
    pose().popPose()
}

/**
 * Draws text vertically centred within a container of [containerH] pixels.
 *
 * [x] should be the left edge of the text and [y] should be the top of the container.
 */
fun GuiGraphics.drawVerticallyCentredText(
    text: Component,
    x: Int,
    y: Int,
    containerH: Int,
    rgb: Int,
    scale: Float = 1f,
    shadow: Boolean = false,
    maxCharacterWidth: Int = Int.MAX_VALUE
) {
    val shadowOffset = if (shadow) 1 else 0
    val scaledLineHeight = ((FONT_HEIGHT_PX + shadowOffset) * scale).toInt()
    val centredY = y + (containerH - scaledLineHeight) / 2

    drawScaledText(text, x, centredY, scale, rgb, shadow, maxCharacterWidth = maxCharacterWidth)
}