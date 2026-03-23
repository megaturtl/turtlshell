package cc.turtl.turtlshell.api.client.gui.widget.element

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

class ImageElement(
    val texture: ResourceLocation,
    val textureW: Int,
    val textureH: Int,
    val renderW: Int,
    val renderH: Int = aspectHeight(renderW, textureW, textureH),
    justify: Justify = Justify.LEFT,
) : BodyElement(renderW, renderH, true, justify) {

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val sourceH = (renderH.toFloat() / renderW * textureW).toInt().coerceAtMost(textureH)
        guiGraphics.blit(texture, x, y, renderW, renderH, 0f, 0f, textureW, sourceH, textureW, textureH)
    }

    companion object {
        fun aspectHeight(renderW: Int, textureW: Int, textureH: Int): Int =
            (renderW.toFloat() / textureW * textureH).toInt()
    }
}