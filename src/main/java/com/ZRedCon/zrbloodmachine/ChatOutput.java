package com.ZRedCon.zrbloodmachine;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ChatOutput {
    public static void debugOut(Level level, String output) {
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            server.getPlayerList().broadcastSystemMessage(Component.literal(output), false);
        }
    }
}
