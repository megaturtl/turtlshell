package cc.turtl.turtlshell;

import cc.turtl.turtlshell.command.InfoCommand;
import cc.turtl.turtlshell.command.TurtlShellCommand;
import cc.turtl.turtlshell.util.MessageUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.util.List;

public class TurtlShellCommands {
    private static final List<TurtlShellCommand> COMMANDS = List.of(
            new InfoCommand()
    );

    public static <S> void register(CommandDispatcher<S> dispatcher) {
        LiteralArgumentBuilder<S> root = LiteralArgumentBuilder.<S>literal(TurtlShellConstants.MOD_ID)
                .executes(TurtlShellCommands::showHelp);

        LiteralArgumentBuilder<S> rootAlias = LiteralArgumentBuilder.<S>literal("ts")
                .executes(TurtlShellCommands::showHelp);

        COMMANDS.forEach(cmd -> root.then(cmd.build()));
        COMMANDS.forEach(cmd -> rootAlias.then(cmd.build()));

        dispatcher.register(root);
        dispatcher.register(rootAlias);
    }

    public static <S> int showHelp(CommandContext<S> context) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return 0;

        // Gets the alias used
        String alias = context.getNodes().getFirst().getNode().getName();

        MessageUtils.sendEmptyLine(player);
        MessageUtils.sendSuccess(player, TurtlShellConstants.MOD_DISPLAY_NAME + " Commands");
        COMMANDS.forEach(cmd ->
                MessageUtils.sendPrefixed(player, "  /" + alias + " " + cmd.getName() + " - " + cmd.getDescription())
        );
        return Command.SINGLE_SUCCESS;
    }
}