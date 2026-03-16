package cc.turtl.turtlshell

import cc.turtl.turtlshell.impl.web.WebServerManager

object TurtlShellCommon {
    const val DEFAULT_PORT = 7890
    fun init() {
        WebServerManager.start(DEFAULT_PORT)
    }
}