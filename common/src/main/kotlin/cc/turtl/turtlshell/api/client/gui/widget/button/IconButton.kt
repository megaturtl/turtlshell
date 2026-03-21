package cc.turtl.turtlshell.api.client.gui.widget.button

import cc.turtl.turtlshell.api.client.gui.texture.Icon
import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.texture.renderSimpleIcon
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

class IconButton(
    btnX: Int,
    btnY: Int,
    btnSize: Int,
    private val idleIconRGB: Int,
    private val hoverIconRGB: Int = idleIconRGB,
    private val idleBgRGB: Int,
    private val hoverBgRGB: Int = idleBgRGB,
    onPress: OnPress,
    private val icon: Icon,
    private val iconSize: IconSize = IconSize.MD,
) : Button(btnX, btnY, btnSize, btnSize, Component.empty(), onPress, DEFAULT_NARRATION) {

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgRGB = if (isHovered) hoverBgRGB else idleBgRGB
        val iconRGB = if (isHovered) hoverIconRGB else idleIconRGB

        context.fill(x, y, x + width, y + height, bgRGB)

        context.renderSimpleIcon(
            icon,
            x + (width - iconSize.px) / 2,
            y + (height - iconSize.px) / 2,
            iconSize,
            rgb = iconRGB,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}