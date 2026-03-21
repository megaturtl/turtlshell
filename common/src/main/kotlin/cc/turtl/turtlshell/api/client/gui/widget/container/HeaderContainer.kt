package cc.turtl.turtlshell.api.client.gui.widget.container

import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.drawText
import cc.turtl.turtlshell.api.client.gui.drawVerticallyCentredText
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.button.IconButton
import cc.turtl.turtlshell.api.core.format.withBold
import cc.turtl.turtlshell.api.core.format.withUnifont
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractContainerWidget
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component

/**
 * Header element of the GUI. This usually contains title text and the close button.
 */
class HeaderContainer(
    headerX: Int,
    headerY: Int,
    headerW: Int,
    headerH: Int,
    title: Component,
    val theme: GuiTheme,
    onClose: Button.OnPress,
) : AbstractContainerWidget(
    headerX,
    headerY, headerW, headerH, title
) {

    val btnSize = (height - theme.paddingSM * 2)
    val btnX = x + width - btnSize - theme.paddingSM
    val btnY = y + theme.paddingSM
    private val closeButton = IconButton(
        btnX, btnY, btnSize,
        theme.textColor.rgb,
        theme.textColor.rgb,
        theme.darkColor.rgb,
        theme.lightColor.rgb,
        onClose,
        SimpleIcons.CROSS
    )

    override fun children(): List<GuiEventListener> = listOf(closeButton)

    override fun renderWidget(
        guiGraphics: GuiGraphics,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float,
    ) {

        val titleX = x + theme.paddingMD
        guiGraphics.drawVerticallyCentredText(message.withBold(), titleX, y, height, theme.textColor.rgb, 1.5F)

        closeButton.render(guiGraphics, mouseX, mouseY, partialTick)
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput) {
        closeButton.updateNarration(narrationElementOutput)
    }
}