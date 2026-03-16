package cc.turtl.turtlshell.api.core.web

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.util.Collections

/**
 * Central registry for web-based dashboards and menus.
 *
 * Other mods should use this to register custom web interfaces during the mod init phase.
 */
object WebRegistry {
    /**
     * Represents a registered web endpoint.
     * @property path The URL path (e.g., "/config" or "/stats")
     * @property handler The [HttpHandler] that processes requests to this path.
     */
    data class WebRoute(val path: String, val handler: HttpHandler)

    private val routes = mutableListOf<WebRoute>()

    /**
     * Registers a new web route.
     * * @param path The path to serve (e.g., "my-mod/dashboard"). Leading slashes are optional.
     * @param handler The [HttpHandler] implementation to process the request.
     */
    @JvmStatic
    fun register(path: String, handler: HttpHandler) {
        routes += WebRoute(path, handler)
    }

    /**
     * Registers a new web route using a Kotlin lambda.
     * * @param path The path to serve.
     * @param action A lambda receiving the [HttpExchange].
     */
    fun register(path: String, action: (HttpExchange) -> Unit) {
        register(path, HttpHandler { exchange -> action(exchange) })
    }

    /**
     * Returns an unmodifiable list of all registered routes.
     */
    fun getRegisteredRoutes(): List<WebRoute> = Collections.unmodifiableList(routes)
}