package net.racialgamer.smallpop.mixin;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import net.racialgamer.smallpop.config.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if(Gui.get().disableTotemPopAnimation) {
            this.floatingItemTimeLeft = 0;
        } else {
            this.floatingItemTimeLeft = Gui.get().animationSpeed;
        }
        if (Gui.get().enableTotemPopPositionChange) {
           this.floatingItemWidth = Gui.get().totemPopPositionX;
           this.floatingItemHeight = Gui.get().totemPopPositionY;
        }
    }

    @ModifyVariable(method = "renderFloatingItem", at = @At("STORE"), ordinal = 8)
    private float modifyRenderfloatingItem(float n) {
        if (Gui.get().enableTotemPopSizeChange) {
            float sizeRange = Gui.get().maxTotemPopSize - Gui.get().minTotemPopSize;
            float size = (float) (Math.sin(System.currentTimeMillis() / 1000.0 * Gui.get().totemPopSizeChangeSpeed) / 2 + 0.5) * sizeRange + Gui.get().minTotemPopSize;
            return n * size;
        } else {
            return n * Gui.get().popSize;
        }
    }
}



