package cc.turtl.turtlshell.neoforge.services;

import cc.turtl.turtlshell.platform.IModChecker;
import net.neoforged.fml.ModList;

public class ModCheckerNeoForge implements IModChecker {
    @Override
    public boolean isLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}