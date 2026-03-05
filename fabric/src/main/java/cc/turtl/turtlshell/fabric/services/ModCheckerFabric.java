package cc.turtl.turtlshell.fabric.services;

import cc.turtl.turtlshell.platform.IModChecker;
import net.fabricmc.loader.api.FabricLoader;

public class ModCheckerFabric implements IModChecker {
    @Override
    public boolean isLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}