package cc.turtl.turtlshell.api.client.gui.widget.element

/** Implemented by widgets that communicate a preferred size to the layout engine. */
interface SizedElement {
    /** Preferred width for inline sizing. */
    val naturalW: Int

    /** Preferred height; used before the layout engine assigns a position. */
    val naturalH: Int
}
