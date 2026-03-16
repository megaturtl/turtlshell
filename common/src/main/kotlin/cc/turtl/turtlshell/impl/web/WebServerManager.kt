package cc.turtl.turtlshell.impl.web

import cc.turtl.turtlshell.BuildDetails
import cc.turtl.turtlshell.api.core.web.WebRegistry
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

internal object WebServerManager {
    private var server: HttpServer? = null

    /**
     * Initializes and starts the background HTTP server.
     * It binds all routes currently stored in [WebRegistry].
     */
    fun start(port: Int) {
        if (server != null) return

        val s = HttpServer.create(InetSocketAddress(port), 0)

        // Pull the data from the API registry
        WebRegistry.getRegisteredRoutes().forEach { route ->
            val cleanPath = if (route.path.startsWith("/")) route.path else "/${route.path}"
            s.createContext(cleanPath, route.handler)
        }

        s.executor = Executors.newFixedThreadPool(1) { r ->
            Thread(r, "${BuildDetails.MOD_DISPLAY_NAME}-Web-Thread").apply { isDaemon = true }
        }

        s.start()
        server = s
    }

    fun stop() {
        server?.stop(0)
        server = null
    }
}