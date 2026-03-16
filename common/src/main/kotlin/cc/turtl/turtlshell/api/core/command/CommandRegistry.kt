package cc.turtl.turtlshell.api.core.command

object CommandRegistry {

    data class CommandGroup(
        val aliases: List<String>,
        val commands: List<TurtlShellCommand>
    )

    private val groups = mutableListOf<CommandGroup>()

    /**
     * Register a group of commands under one or more root aliases.
     * Externals mods should call this during mod init before platform registration.
     */
    fun registerGroup(aliases: List<String>, commands: List<TurtlShellCommand>) {
        require(aliases.isNotEmpty()) { "Must provide at least one alias" }
        require(commands.isNotEmpty()) { "Must provide at least one command" }
        groups += CommandGroup(aliases, commands)
    }

    fun getGroups(): List<CommandGroup> = groups
}