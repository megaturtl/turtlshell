package cc.turtl.turtlshell.api.core.command

object CommandRegistry {

    data class CommandGroup(
        val aliases: List<String>,
        val commands: List<TurtlShellCommand>
    )

    private val clientGroups = mutableListOf<CommandGroup>()
    private val serverGroups = mutableListOf<CommandGroup>()

    /**
     * Register a group of client commands under one or more root aliases.
     * Externals mods should call this during mod init before platform registration.
     */
    fun registerClientGroup(
        aliases: List<String>,
        commands: List<TurtlShellCommand>
    ) {
        require(aliases.isNotEmpty()) { "Must provide at least one alias" }
        require(commands.isNotEmpty()) { "Must provide at least one command" }

        clientGroups += CommandGroup(aliases, commands)
    }

    /**
     * Register a group of server commands under one or more root aliases.
     * Externals mods should call this during mod init before platform registration.
     */
    fun registerServerGroup(
        aliases: List<String>,
        commands: List<TurtlShellCommand>
    ) {
        require(aliases.isNotEmpty()) { "Must provide at least one alias" }
        require(commands.isNotEmpty()) { "Must provide at least one command" }

        serverGroups += CommandGroup(aliases, commands)
    }

    fun getClientGroups(): List<CommandGroup> = clientGroups
    fun getServerGroups(): List<CommandGroup> = serverGroups
}