package cc.turtl.turtlshell;


import cc.turtl.turtlshell.platform.PlatformServices;
import cc.turtl.turtlshell.util.format.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

import static cc.turtl.turtlshell.util.format.ComponentUtils.createComponent;

public class TurtlShellConstants {
    public static final String MOD_ID = "turtlshell";
    public static final String MOD_DISPLAY_NAME = "TurtlShell";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR = "megaturtl";

    public static final Path CONFIG_PATH = PlatformServices.getPathFinder().getConfigDir().resolve(MOD_ID);

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /**
     * Prefix for chat messages (e.g. command feedback for users)
     */
    public static final Component MESSAGE_PREFIX = Component.empty()
            .append(createComponent("[", ColorUtils.DARK_GRAY.getRGB()))
            .append(Component.literal("\uD83D\uDEE0")
                    .withColor(ColorUtils.MINT.getRGB())
                    .withStyle(ChatFormatting.BOLD))
            .append(createComponent("] ", ColorUtils.DARK_GRAY.getRGB()))
            .withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    createComponent(MOD_DISPLAY_NAME, ColorUtils.MINT.getRGB())))
            );
}
