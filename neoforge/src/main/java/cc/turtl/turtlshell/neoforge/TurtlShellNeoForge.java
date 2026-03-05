package cc.turtl.turtlshell.neoforge;

import cc.turtl.turtlshell.TurtlShell;
import cc.turtl.turtlshell.TurtlShellConstants;
import cc.turtl.turtlshell.config.TurtlShellConfig;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(TurtlShellConstants.MOD_ID)
public final class TurtlShellNeoForge {
    public TurtlShellNeoForge() {
        registerConfigScreen();
        TurtlShell.initClient();
    }

    private void registerConfigScreen() {
        ModList.get().getModContainerById(TurtlShellConstants.MOD_ID)
                .ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, this::createConfigScreen));
    }

    private Screen createConfigScreen(ModContainer container, Screen parent) {
        return TurtlShellConfig.createScreen(parent);
    }
}
