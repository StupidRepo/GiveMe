package com.stupidrepo.giveme.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import org.slf4j.Logger;

public class GiveMeClient implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final int MAX_MESSAGE_LENGTH = 256;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(GiveMeClient::registerCommands);
    }

    private static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext context) {
        GiveMeCommands.register(dispatcher, context);
    }
}
