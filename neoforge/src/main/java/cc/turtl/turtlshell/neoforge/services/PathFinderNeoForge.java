package cc.turtl.turtlshell.neoforge.services;

import cc.turtl.turtlshell.platform.IPathFinder;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Optional;

public class PathFinderNeoForge implements IPathFinder {
    @Override
    public Optional<Path> getModPath(String modId, String path) {
        return Optional.ofNullable(ModList.get().getModFileById(modId))
                .map(info -> info.getFile().findResource(path));
    }

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
