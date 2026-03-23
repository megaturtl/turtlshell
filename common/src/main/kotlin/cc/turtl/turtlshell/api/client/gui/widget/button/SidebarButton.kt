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
    message: Component,
    btnX: Int,
    btnY: Int,
    btnW: Int,
    btnH: Int,
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

    /** True when this button's page is currently displayed. */
    var buttonActive: Boolean = false

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val bgRGB = when {
            buttonActive -> theme.navActiveBg.rgb
            isHovered -> theme.navHoverBg.rgb
            else -> theme.navBg.rgb
        }

        val textRGB = if (buttonActive) theme.navActiveText.rgb else theme.textColor.rgb

        context.fill(x, y, x + width, y + height, bgRGB)

        val iconX = x + theme.paddingMD
        val iconY = y + (height - IconSize.MD.px) / 2
        context.renderSimpleIcon(
            icon,
            iconX,
            iconY,
            IconSize.MD,
            rgb = textRGB,
        )

        val labelX = iconX + IconSize.MD.px + theme.paddingMD
        context.drawVerticallyCentredText(message, labelX, y, height, textRGB)

        val chevronX = x + width - IconSize.SM.px - theme.paddingMD
        val chevronY = y + (height - IconSize.SM.px) / 2
        context.renderSimpleIcon(
            SimpleIcons.CHEVRON_RIGHT,
            chevronX,
            chevronY,
            IconSize.SM,
            rgb = textRGB,
        )
    }

    override fun updateWidgetNarration(output: NarrationElementOutput) {
        defaultButtonNarrationText(output)
    }
}