package net.racialgamer.totemtweaks;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
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

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
				CommandManager.literal("TotemSimulatePop")
						.executes(context -> {
							totemSimulatePop();
							return Command.SINGLE_SUCCESS;
						})
		));
	}

	private int openConfigScreen(CommandContext<ServerCommandSource> context) {
		MinecraftClient client = MinecraftClient.getInstance();
		client.execute(() ->
				client.setScreen(AutoConfig.getConfigScreen(Gui.class, client.currentScreen).get())
		);
		return Command.SINGLE_SUCCESS;
	}

	public static void totemSimulatePop() {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;

		if (player != null) {
			player.playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
			client.gameRenderer.showFloatingItem(new ItemStack(Items.TOTEM_OF_UNDYING));
		}
	}
}

