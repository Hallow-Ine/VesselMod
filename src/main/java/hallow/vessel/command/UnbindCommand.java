package hallow.vessel.command;


import com.mojang.brigadier.CommandDispatcher;

import hallow.vessel.network.ModNetworking;
import hallow.vessel.soul.SoulManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class UnbindCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(UnbindCommand::build);
    }

    private static void build(CommandDispatcher<ServerCommandSource> dispatcher, net.minecraft.command.CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {

        dispatcher.register(
            CommandManager.literal("soul")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("unbind")
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();

                    SoulManager.setSoulBound(player, false);

                    player.sendMessage(Text.literal("unbound soul"), false);

                    return 1;
                }))
                .then(CommandManager.literal("bind").executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();

                    SoulManager.setSoulBound(player, true);

                    player.sendMessage(Text.literal("bound soul"), false);

                    return 1;
                }))
                .then(CommandManager.literal("isBound").executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();

                    player.sendMessage(Text.literal(String.format("The server says the soul is %s", SoulManager.isSoulBound(player) ? "bound" : "unbound")), false);

                    return 1;
                }))
                .then(CommandManager.literal("sync").executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();

                    ModNetworking.syncClientSoul(player);

                    player.sendMessage(Text.literal(String.format("The server and client have been synced to: %s", SoulManager.isSoulBound(player) ? "bound" : "unbound")), false);

                    return 1;
                }))
                
        );
    }
}