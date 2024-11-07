package net.racialgamer.totemtweaks;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.racialgamer.totemtweaks.config.Gui;

public class TotemTweaks implements ModInitializer {

	@Override
	public void onInitialize() {
		AutoConfig.register(Gui.class, GsonConfigSerializer::new);
		registerCommands();
	}

	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				CommandManager.literal("TotemTweaks")
						.executes(this::openConfigScreen)
		));
	}

	private int openConfigScreen(CommandContext<ServerCommandSource> context) {
		MinecraftClient client = MinecraftClient.getInstance();
			client.execute(() ->
					client.setScreen(AutoConfig.getConfigScreen(Gui.class, client.currentScreen).get())
			);
			return Command.SINGLE_SUCCESS;
	}
}

