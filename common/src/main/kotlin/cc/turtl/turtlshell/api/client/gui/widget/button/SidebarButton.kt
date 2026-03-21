package cc.turtl.turtlshell.api.client.gui.widget.button

import cc.turtl.turtlshell.api.client.gui.*
import cc.turtl.turtlshell.api.client.gui.texture.Icon
import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.texture.renderSimpleIcon
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

class SidebarButton(
    btnX: Int,
    btnY: Int,
    btnW: Int,
    btnH: Int = (theme.font.lineHeight - 2) * 2,
    message: Component,
    private val icon: Icon = SimpleIcons.MINUS,
    private val theme: GuiTheme,
    onPress: OnPress,
) : Button(
    btnX,
    btnY,
    btnW,
    btnH,
    message,
    onPress,
    DEFAULT_NARRATION
) {

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgColor = if (isHovered) theme.lightColor.rgb else theme.darkColor.rgb

        context.fill(x, y, x + width, y + height, bgColor)

        val iconX = x + theme.paddingMD // left aligned
        val iconY = y + (height - IconSize.MD.px) / 2 // vertically centred
        context.renderSimpleIcon(
            icon,
            iconX,
            iconY,
            IconSize.MD,
            rgb = theme.textColor.rgb,
        )

        val labelX = iconX + IconSize.MD.px + theme.paddingMD
        context.drawVerticallyCentredText(theme.font, message, labelX, y, height, theme.textColor.rgb, 0.75F)

        val chevronX = x + width - IconSize.SM.px - theme.paddingMD // right aligned
        val chevronY = y + (height - IconSize.SM.px) / 2 // vertically centred
        context.renderSimpleIcon(
            SimpleIcons.CHEVRON_RIGHT,
            chevronX, // right aligned
            chevronY, // vertically centred
            IconSize.SM,
            rgb = theme.textColor.rgb,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}