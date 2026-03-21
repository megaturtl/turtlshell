package cc.turtl.turtlshell.api.client.gui

import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.texture.SimpleIcons
import cc.turtl.turtlshell.api.client.gui.widget.container.HeaderContainer
import cc.turtl.turtlshell.api.client.gui.widget.container.SidebarContainer
import cc.turtl.turtlshell.api.client.gui.widget.button.SidebarButton
import cc.turtl.turtlshell.api.client.gui.widget.container.BodyContainer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.math.max

open class ModalScreen(
    title: Component = Component.translatable("ts.gui.base.title"),
    private val screenW: Int = DEFAULT_GUI_MODAL_WIDTH,
    private val screenH: Int = DEFAULT_GUI_MODAL_HEIGHT,
    private val theme: GuiTheme = GuiTheme.DEFAULT,
) : Screen(title) {

    /**
     * Size/positioning info for this (the parent screen) - Easy access for children widgets.
     */
    protected var screenX: Int = 0
    protected var screenY: Int = 0

    protected var headerDividerX: Int = 0
    protected var headerDividerY: Int = 0
    protected var sidebarDividerX: Int = 0
    protected var sidebarDividerY: Int = 0

    companion object {
        fun open() {
            Minecraft.getInstance().setScreen(ModalScreen())
        }
    }

    override fun init() {
        super.init()
        screenX = (width - screenW) / 2
        screenY = (height - screenH) / 2

        val headerX = screenX
        val headerY = screenY
        val headerW = screenW
        val headerH = max(theme.font.lineHeight - 2, IconSize.MD.px) + (theme.paddingMD * 2)
        val header = HeaderContainer(headerX, headerY, headerW, headerH, title, theme) { onClose() }

        val sidebarX = screenX
        val sidebarY = screenY + headerH + theme.dividerWidth
        val sidebarW = DEFAULT_GUI_SIDEBAR_WIDTH
        val sidebarH = screenH - headerH - theme.dividerWidth
        val sidebar = SidebarContainer(sidebarX, sidebarY, sidebarW, sidebarH, theme)

        val bodyX = screenX + sidebarW + theme.dividerWidth
        val bodyY = screenY + headerH + theme.dividerWidth
        val bodyW = screenW - sidebarW - theme.dividerWidth
        val bodyH = screenH - headerH - theme.dividerWidth
        val body = BodyContainer(bodyX, bodyY, bodyW, bodyH, theme)

        headerDividerX = screenX
        headerDividerY = screenY + headerH

        sidebarDividerX = screenX + sidebarW
        sidebarDividerY = sidebarY

        populateDummyContent(body)
        populateDummySidebar(sidebar)

        addRenderableWidget(header)
        addRenderableWidget(sidebar)
        addRenderableWidget(body)
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        // Fill parent panel bg
        context.fill(screenX, screenY, screenX + screenW, screenY + screenH, theme.darkColor.rgb)

        // Render container dividers
        context.fill(
            sidebarDividerX,
            sidebarDividerY,
            sidebarDividerX + theme.dividerWidth,
            screenY + screenH,
            theme.lightColor.rgb
        )
        context.fill(
            headerDividerX,
            headerDividerY,
            headerDividerX + screenW,
            headerDividerY + theme.dividerWidth,
            theme.lightColor.rgb
        )

        super.render(context, mouseX, mouseY, delta)
    }

    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}

    private fun populateDummySidebar(sidebar: SidebarContainer) {
        val navItems = listOf(
            "Dashboard" to SimpleIcons.HAMMER,
            "Settings" to SimpleIcons.HAMMER, // Replace with GEAR if available
            "Inventory" to SimpleIcons.HAMMER, // Replace with CHEST if available
            "History" to SimpleIcons.HAMMER,
            "Support" to SimpleIcons.HAMMER
        )

        navItems.forEachIndexed { i, (label, icon) ->
            sidebar.addNavButton(label, icon) {}
        }
    }

    private fun populateDummyContent(body: BodyContainer) {
        val itemHeight = 20
        val itemCount = 20
        val padding = theme.paddingMD

        repeat(itemCount) { i ->
            val itemY = body.y + padding + i * (itemHeight + padding)
            body.addContent(
                SidebarButton(
                    body.x + padding,
                    itemY,
                    DEFAULT_GUI_SIDEBAR_WIDTH,
                    DEFAULT_GUI_SIDEBAR_BUTTON_HEIGHT,
                    Component.literal("Item ${i + 1}"),

                    SimpleIcons.HAMMER,
                    theme,
                    onPress = {},
                )
            )
        }

        body.updateContentHeight(itemCount * (itemHeight + padding) + padding)
    }
}