package cc.turtl.turtlshell.api.event;

import net.minecraft.client.Minecraft;

public record ClientPostTickEvent(Minecraft mc) {
}
