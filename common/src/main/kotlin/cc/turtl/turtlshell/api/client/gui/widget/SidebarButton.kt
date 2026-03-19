package cc.turtl.turtlshell.api.client.gui.widget

import cc.turtl.turtlshell.api.client.gui.*
import cc.turtl.turtlshell.api.client.gui.texture.Icon
import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.texture.renderSimpleIcon
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

class SidebarButton(
    btnX: Int,
    btnY: Int,
    message: Component,
    onPress: OnPress,
    private val icon: Icon? = null,
) : Button(
    btnX,
    btnY,
    DEFAULT_GUI_SIDEBAR_WIDTH,
    DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
    message,
    onPress,
    DEFAULT_NARRATION
) {

    private val font = Minecraft.getInstance().font

    companion object {
        private val BG_COLOR_IDLE = DEFAULT_GUI_DARK_COLOR
        private val BG_COLOR_HOVER = DEFAULT_GUI_MED_COLOR
        private val BG_COLOR_ACTIVE = DEFAULT_GUI_ACCENT_COLOR
        private val ICON_COLOR = DEFAULT_GUI_TEXT_COLOR
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgColor = if (isHovered) BG_COLOR_HOVER.rgb else BG_COLOR_IDLE.rgb

        context.fill(x, y, x + width, y + height, bgColor)

        icon?.let {
            context.renderSimpleIcon(
                it,
                x + DEFAULT_GUI_PADDING * 2, // left aligned
                y + (height - IconSize.MD.px) / 2, // vertically centred
                IconSize.MD,
                color = ICON_COLOR,
            )
        }

        val fontScale = 0.75F
        val scaledX = (x + DEFAULT_GUI_PADDING * 4 + IconSize.MD.px + DEFAULT_GUI_PADDING) / fontScale
        val scaledY = (y + (height + 2 - font.lineHeight * fontScale) / 2) / fontScale

        context.pose().pushPose()
        context.pose().scale(fontScale, fontScale, 1F)
        context.drawString(
            font,
            message,
            scaledX.toInt(),
            scaledY.toInt(),
            DEFAULT_GUI_TEXT_COLOR.rgb,
            false
        )
        context.pose().popPose()

        context.renderSimpleIcon(
            SimpleIcons.CHEVRON_RIGHT,
            x + width - IconSize.SM.px - DEFAULT_GUI_PADDING, // right aligned
            y + (height - IconSize.SM.px) / 2, // vertically centred
            IconSize.SM,
            color = ICON_COLOR,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}