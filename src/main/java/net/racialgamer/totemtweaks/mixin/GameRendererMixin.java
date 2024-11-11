package net.racialgamer.totemtweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.racialgamer.totemtweaks.config.Gui;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    private ItemStack floatingItem;

    @Shadow
    private int floatingItemTimeLeft;

    @Shadow
    private float floatingItemWidth;

    @Shadow
    private float floatingItemHeight;

    @Inject(method = "showFloatingItem", at = @At("TAIL"))
    public void InjectShowFloatingItem(ItemStack floatingItem, CallbackInfo ci) {
        this.floatingItem = floatingItem;
        if (Gui.get().TotemPopAnimation) {
            this.floatingItemTimeLeft = 0;
        } else {
            this.floatingItemTimeLeft = Gui.get().animationSpeed;
        }
        if (Gui.get().lockRotationPosition) {
            this.floatingItemWidth = 0;
            this.floatingItemHeight = 0;
        }
    }

    @Inject(method = "renderFloatingItem", at = @At("TAIL"))
    public void renderFloatingItemWithTotemCount(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (Gui.get().showTotemCount && floatingItem != null && floatingItem.getItem() == Items.TOTEM_OF_UNDYING) {
            int totemCount = getTotemCount();
            String countText = "Totems: " + totemCount;
            int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(countText);
            int x = (screenWidth - textWidth) / 2;
            int y = screenHeight / 2 + 20;
            context.drawText(MinecraftClient.getInstance().textRenderer, countText, x, y, 0xFFFFFF, true);
        }
    }

    private int getTotemCount() {
        int count = 0;
        for (ItemStack stack : MinecraftClient.getInstance().player.getInventory().main) {
            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                count += stack.getCount();
            }
        }
        return count;
    }

    @ModifyVariable(method = "renderFloatingItem", at = @At("STORE"), ordinal = 0)
    private int modifyTickRenderfloatingItem(int i) {
        return Gui.get().animationSpeed - floatingItemTimeLeft;
    }

    @ModifyVariable(method = "renderFloatingItem", at = @At("STORE"), ordinal = 1)
    private float modifyFloatRenderfloatingItem(float f) {
        return f * 40 / Gui.get().animationSpeed;
    }

    @ModifyVariable(method = "renderFloatingItem", at = @At("STORE"), ordinal = 8)
    private float modifySizeRenderfloatingItem(float n) {
        if (Gui.get().enableTotemPopSizeChange) {
            float sizeRange = Gui.get().maxTotemPopSize - Gui.get().minTotemPopSize;
            float size = (float) (Math.sin(System.currentTimeMillis() / 1000.0 * Gui.get().totemPopSizeChangeSpeed) / 2 + 0.5) * sizeRange + Gui.get().minTotemPopSize;
            return n * size;
        }
        if (Gui.get().staticSize) {
            return 225 * Gui.get().popSize;
        }
        return n * Gui.get().popSize;
    }

    @ModifyArgs(method = "renderFloatingItem(Lnet/minecraft/client/gui/DrawContext;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"))
    private void modifyTranslateArgs(Args args) {
        float originalX = args.get(0);
        float originalY = args.get(1);
        float screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        float screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        float adjustedX = originalX + ((Gui.get().xPosition - 50) / 100.0f * screenWidth);
        float adjustedY = originalY + ((Gui.get().yPosition - 50) / 100.0f * screenHeight);
        args.set(0, adjustedX);
        args.set(1, adjustedY);
    }


    @WrapWithCondition(method = "renderFloatingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V", ordinal = 0))
    private boolean wrapRotationY(MatrixStack matrixStack, Quaternionf rotation) {
        return !Gui.get().disableRotations;
    }

    @WrapWithCondition(method = "renderFloatingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V", ordinal = 1))
    private boolean wrapRotationX(MatrixStack matrixStack, Quaternionf rotation) {
        return !Gui.get().disableRotations;
    }

    @WrapWithCondition(method = "renderFloatingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionf;)V", ordinal = 2))
    private boolean wrapRotationZ(MatrixStack matrixStack, Quaternionf rotation) {
        return !Gui.get().disableRotations;
    }
}




