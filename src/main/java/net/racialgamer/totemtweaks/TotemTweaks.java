package net.racialgamer.totemtweaks;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.racialgamer.totemtweaks.config.Gui;

public class TotemTweaks implements ModInitializer {

	@Override
	public void onInitialize() {
		AutoConfig.register(Gui.class, GsonConfigSerializer::new);
		registerCommands();
	}

	private void registerCommands() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(ClientCommandManager.literal("totemTweaks")
                        .executes(this::openConfigScreen)
        ));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> dispatcher.register(ClientCommandManager.literal("simulatePop")
                        .executes(context -> {
                            simulatePop();
                            return Command.SINGLE_SUCCESS;
                        })
        ));
	}

	private int openConfigScreen(CommandContext<?> context) {
		MinecraftClient client = MinecraftClient.getInstance();
		client.execute(() -> {
			if (client.currentScreen != null) {
				client.setScreen(AutoConfig.getConfigScreen(Gui.class, client.currentScreen).get());
			}
		});
		return Command.SINGLE_SUCCESS;
	}

	public static void simulatePop() {
		ClientPlayerEntity player = MinecraftClient.getInstance().player;

		if (player != null) {
			player.playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
			MinecraftClient.getInstance().gameRenderer.showFloatingItem(new ItemStack(Items.TOTEM_OF_UNDYING));
		}
	}
}

