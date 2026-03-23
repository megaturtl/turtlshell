package cc.turtl.turtlshell.api.client.gui.screen

import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_MODAL_HEIGHT
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_MODAL_WIDTH
import cc.turtl.turtlshell.api.client.gui.DEFAULT_GUI_SIDEBAR_WIDTH
import cc.turtl.turtlshell.api.client.gui.GuiTheme
import cc.turtl.turtlshell.api.client.gui.texture.Icon
import cc.turtl.turtlshell.api.client.gui.texture.IconSize
import cc.turtl.turtlshell.api.client.gui.widget.container.BodyContainer
import cc.turtl.turtlshell.api.client.gui.widget.container.HeaderContainer
import cc.turtl.turtlshell.api.client.gui.widget.container.SidebarContainer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

abstract class AbstractModalScreen(
    title: Component,
    protected val screenW: Int = DEFAULT_GUI_MODAL_WIDTH,
    protected val screenH: Int = DEFAULT_GUI_MODAL_HEIGHT,
    protected val theme: GuiTheme = GuiTheme.DEFAULT,
    val debug: Boolean = false,
) : Screen(title) {

    protected var screenX: Int = 0
    protected var screenY: Int = 0

    protected var headerDividerX: Int = 0
    protected var headerDividerY: Int = 0
    protected var sidebarDividerX: Int = 0
    protected var sidebarDividerY: Int = 0

    protected lateinit var header: HeaderContainer
    protected lateinit var sidebar: SidebarContainer
    protected lateinit var body: BodyContainer

    override fun init() {
        super.init()
        screenX = (width - screenW) / 2
        screenY = (height - screenH) / 2

        val headerX = screenX
        val headerY = screenY
        val headerW = screenW
        val headerH = IconSize.MD.px + theme.paddingMD * 2 + theme.paddingSM * 2

        val sidebarX = screenX
        val sidebarY = screenY + headerH + theme.dividerWidth
        val sidebarW = DEFAULT_GUI_SIDEBAR_WIDTH
        val sidebarH = screenH - headerH - theme.dividerWidth

        val bodyX = screenX + sidebarW + theme.dividerWidth
        val bodyY = screenY + headerH + theme.dividerWidth
        val bodyW = screenW - sidebarW - theme.dividerWidth
        val bodyH = screenH - headerH - theme.dividerWidth

        headerDividerX = screenX
        headerDividerY = screenY + headerH
        sidebarDividerX = screenX + sidebarW
        sidebarDividerY = sidebarY

        header = HeaderContainer(headerX, headerY, headerW, headerH, title, theme) { onClose() }
        sidebar = SidebarContainer(sidebarX, sidebarY, sidebarW, sidebarH, theme)
        body = BodyContainer(bodyX, bodyY, bodyW, bodyH, theme, debug)

        addRenderableWidget(header)
        addRenderableWidget(sidebar)
        addRenderableWidget(body)

        initContainers()
    }

    /**
     * Called after containers are initialised and registered.
     * Override this in subclasses to populate the sidebar and body via [addPage].
     */
    protected open fun initContainers() {}

    /**
     * Registers a nav button that loads a page into the body area when clicked.
     *
     * The [init] lambda runs on the [BodyContainer] receiver, so you can call
     * addContent and updateContentHeight directly inside it. The first page
     * added is shown immediately and its nav button is marked active.
     *
     * Example:
     * ```
     * addPage("Home", SimpleIcons.HOME) {
     *     addContent(someWidget)
     *     updateContentHeight(totalHeight)
     * }
     * ```
     */
    protected fun addPage(label: String, icon: Icon, init: () -> Unit) {
        val isFirstPage = sidebar.buttonCount == 0

        sidebar.addNavButton(label, icon) {
            body.clearElements()
            init()
            body.positionElements()
        }

        if (isFirstPage) {
            sidebar.setActiveButton(0)
            init()
            body.positionElements()
        }
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        context.fill(screenX, screenY, screenX + screenW, screenY + screenH, theme.darkColor.rgb)

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

    override fun isPauseScreen(): Boolean = false
    override fun renderBlurredBackground(delta: Float) {}
    override fun renderMenuBackground(context: GuiGraphics) {}
}