package hallow.vessel.command;


import com.mojang.brigadier.CommandDispatcher;

import hallow.vessel.soul.SoulBoundPlayer;
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
            CommandManager.literal("unsoul")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();

                    ((SoulBoundPlayer) player).unBindSoul();

                    player.sendMessage(Text.literal("unbound soul"), false);

                    return 1;
                })
        );
    }
}