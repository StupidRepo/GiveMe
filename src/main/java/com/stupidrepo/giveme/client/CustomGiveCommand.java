package com.stupidrepo.giveme.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.stupidrepo.giveme.client.GiveMeClient.LOGGER;
import static dev.xpple.clientarguments.arguments.CItemStackArgumentType.getCItemStackArgument;
import static dev.xpple.clientarguments.arguments.CItemStackArgumentType.itemStack;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CustomGiveCommand {
    private static final SimpleCommandExceptionType NOT_CREATIVE_EXCEPTION = new SimpleCommandExceptionType(Component.translatable("commands.cgive.not_in_creative"));
    private static final SimpleCommandExceptionType NOT_ENOUGH_SPACE = new SimpleCommandExceptionType(Component.translatable("commands.cgive.not_enough_space"));

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext context) {
        dispatcher.register(literal("giveme")
                .then(argument("item", itemStack(context)).executes(ctx -> give(ctx.getSource(), getCItemStackArgument(ctx, "item"), 1))
                        .then(argument("count", integer(1)).executes(ctx -> give(ctx.getSource(), getCItemStackArgument(ctx, "item"), getInteger(ctx, "count"))))));
        LOGGER.info("Registered /giveme command");
    }

    private static int give(FabricClientCommandSource source, ItemInput itemInput, int count) throws CommandSyntaxException {
        if (!source.getPlayer().getAbilities().instabuild) throw NOT_CREATIVE_EXCEPTION.create();
        if(count > itemInput.getItem().getMaxStackSize()) throw new SimpleCommandExceptionType(Component.translatable("commands.cgive.too_many_items", itemInput.getItem().getMaxStackSize())).create();

        var i = getSpace(source.getPlayer());
        if(i == -1) throw NOT_ENOUGH_SPACE.create();

        ItemStack stack = itemInput.createItemStack(Math.min(count, itemInput.getItem().getMaxStackSize()), false);
        Objects.requireNonNull(source.getClient().gameMode).handleCreativeModeItemAdd(stack, 36 + i);

        source.getPlayer().inventoryMenu.broadcastChanges();
        source.sendFeedback(Component.translatable("commands.give.success.single", count, stack.getDisplayName(), source.getPlayer().getDisplayName()));

        return Command.SINGLE_SUCCESS;
    }

    private static int getSpace(Player player) {
        for(int i = 0; i < 9; i++)
        {
            if(!player.getInventory().getItem(i).isEmpty()) continue;
            return i;
        }

        return -1;
    }
}
