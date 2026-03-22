package cc.turtl.turtlshell.api.client.gui.widget.element

/** Implemented by widgets that need to recalculate internal layout after the layout engine assigns x/y/width. */
interface LayoutAware {
    fun applyLayout()
}
