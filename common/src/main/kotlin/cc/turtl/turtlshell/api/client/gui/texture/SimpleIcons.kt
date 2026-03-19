package cc.turtl.turtlshell.api.client.gui.texture

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.format.ColorLib
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import java.awt.Color

private const val TEXTURE_SIZE = 16

data class Icon(val texture: ResourceLocation)

/**
 * Render sizes for icons. Each value is a power of 2.
 */
enum class IconSize(val px: Int) {
    XS(2),
    SM(4),
    MD(8),
    LG(16),
    XL(32)
}

object SimpleIcons {
    val CROSS = Icon(iconResource("simple_icon_cross.png"))
    val HAMMER = Icon(iconResource("simple_icon_hammer.png"))

    val CHEVRON_RIGHT = Icon(iconResource("simple_icon_chevron_right.png"))
}

/**
 * Renders a sized icon tinted to the given color.
 *
 * @param icon The simple icon to render.
 * @param x Left coordinate to start rendering at.
 * @param y Top coordinate to start rendering at.
 * @param size The size to render the icon at.
 * @param color The color to tint the icon with.
 */
fun GuiGraphics.renderSimpleIcon(
    icon: Icon,
    x: Int, y: Int,
    size: IconSize = IconSize.MD,
    color: Color = ColorLib.WHITE
) {
    RenderSystem.setShaderColor(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
    blit(icon.texture, x, y, size.px, size.px, 0f, 0f, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE)
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
}

private fun iconResource(fileName: String): ResourceLocation =
    ResourceLocation.fromNamespaceAndPath(BuildDetails.MOD_ID, "textures/gui/icon/simple/$fileName")
