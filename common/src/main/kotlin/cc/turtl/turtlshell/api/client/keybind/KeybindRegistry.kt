package cc.turtl.turtlshell.api.client.keybind

import net.minecraft.client.KeyMapping

object KeybindRegistry {

    private val groups = mutableListOf<KeybindGroup>()

    data class KeybindGroup(
        val category: String,
        val keybinds: List<KeyMapping>
    )

    /**
     * Register a group of keybinds under a category name.
     * Call this during client mod initialization before platform registration.
     */
    fun registerGroup(category: String, keybinds: List<KeyMapping>) {
        require(keybinds.isNotEmpty()) { "Must provide at least one keybind" }
        groups += KeybindGroup(category, keybinds)
    }

    /**
     * Returns all registered keybinds across all groups, for use by platform-specific impls
     */
    fun all(): List<KeyMapping> = groups.flatMap { it.keybinds }
}