package net.racialgamer.totemtweaks.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.racialgamer.totemtweaks.config.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRenderMixin {

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (Gui.get().enableTotemSizeChange && item.getItem() == Items.TOTEM_OF_UNDYING) {
            float sizeRange = Gui.get().maxTotemSize - Gui.get().minTotemSize;
            float size = (float) (Math.sin(System.currentTimeMillis() / 1000.0 * Gui.get().totemSizeChangeSpeed) / 2 + 0.5) * sizeRange + Gui.get().minTotemSize;
            matrices.scale(size, size, size);
        } else if (hand == Hand.MAIN_HAND && item.getItem() == Items.TOTEM_OF_UNDYING) {
            matrices.scale(Gui.get().totemSize, Gui.get().totemSize, Gui.get().totemSize);
        } else if (hand == Hand.OFF_HAND && item.getItem() == Items.TOTEM_OF_UNDYING) {
            matrices.scale(Gui.get().totemSize, Gui.get().totemSize, Gui.get().totemSize);
        }
    }
}