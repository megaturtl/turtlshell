package cc.turtl.turtlshell.api.client.gui.texture

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.format.ColorLib
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import java.awt.Color

private const val TEXTURE_SIZE = 14

data class Icon(val texture: ResourceLocation)

enum class IconSize(val px: Int) {
    SM(7),
    TST(11),
    MD(14),
    LG(21),
    XL(28)
}

object SimpleIcons {
    val CROSS = Icon(iconResource("simple_icon_cross.png"))
    val HAMMER = Icon(iconResource("simple_icon_hammer.png"))
    val HOME = Icon(iconResource("simple_icon_home.png"))
    val BLOCKED = Icon(iconResource("simple_icon_block.png"))
    val EYE = Icon(iconResource("simple_icon_eye.png"))
    val SEARCH = Icon(iconResource("simple_icon_search.png"))
    val HEART = Icon(iconResource("simple_icon_heart.png"))
    val STAR = Icon(iconResource("simple_icon_star.png"))
    val GEAR = Icon(iconResource("simple_icon_gear.png"))

    val MINUS = Icon(iconResource("simple_icon_minus.png"))
    val PLUS = Icon(iconResource("simple_icon_plus.png"))

    val CHEVRON_RIGHT = Icon(iconResource("simple_icon_chevron_right.png"))
}

/**
 * Renders a sized icon tinted to the given color.
 *
 * @param icon The simple icon to render.
 * @param x Left coordinate to start rendering at.
 * @param y Top coordinate to start rendering at.
 * @param size The size to render the icon at.
 * @param rgb The color to tint the icon with.
 */
fun GuiGraphics.renderSimpleIcon(
    icon: Icon,
    x: Int, y: Int,
    size: IconSize = IconSize.MD,
    rgb: Int = ColorLib.WHITE.rgb
) {
    val color = Color(rgb)
    RenderSystem.setShaderColor(color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
    blit(icon.texture, x, y, size.px, size.px, 0f, 0f, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE)
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
}

private fun iconResource(fileName: String): ResourceLocation =
    ResourceLocation.fromNamespaceAndPath(BuildDetails.MOD_ID, "textures/gui/icon/simple/$fileName")
