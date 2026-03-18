package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.core.format.ColorLib
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import java.awt.Color

/**
 * Renders a sized icon tinted to the given color.
 *
 * @param texture The icon texture to render. Should be a png using white on a transparent background.
 * @param x Left coordinate to start rendering at.
 * @param y Top coordinate to start rendering at.
 * @param renderSize The size (width and height) to render the icon at.
 * @param textureSize The size of the source texture in pixels (default is 64).
 * @param color The color to tint the icon with.
 */
fun GuiGraphics.renderTintedIcon(
    texture: ResourceLocation,
    x: Int, y: Int,
    renderSize: Int,
    textureSize: Int = 64,
    color: Color = ColorLib.WHITE
) {
    RenderSystem.setShaderColor(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
    blit(texture, x, y, renderSize, renderSize, 0f, 0f, textureSize, textureSize, textureSize, textureSize)
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
}

fun GuiGraphics.renderIcon(
    texture: ResourceLocation,
    x: Int, y: Int,
    renderSize: Int,
    textureSize: Int,
) {
    blit(texture, x, y, renderSize, renderSize, 0f, 0f, textureSize, textureSize, textureSize, textureSize)
}