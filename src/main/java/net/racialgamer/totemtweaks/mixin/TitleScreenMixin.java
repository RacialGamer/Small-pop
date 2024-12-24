package net.racialgamer.totemtweaks.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.racialgamer.totemtweaks.version.TTVersion;
import net.racialgamer.totemtweaks.version.VersionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.concurrent.CompletableFuture;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Unique
    Identifier texture = Identifier.of("minecraft", "textures/item/totem_of_undying.png");
    @Unique
    private String updateMessage = "Checking for updates...";
    @Unique
    private boolean updateCheckAttempted = false;


    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void renderTotem(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int cardWidth = 80;
        int cardHeight = 100;
        int textureWidth = 64;
        int textureHeight = 64;
        int x = (int) ((this.width - cardWidth) / 1.4);
        int y = (int) ((this.height - cardHeight) / 2.5);
        int textureX = x + (cardWidth - textureWidth) / 2;
        int textureY = y + (cardHeight - textureHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, texture, textureX, textureY, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        drawTextBalloon(context, x + 70, y + 20);
        checkForUpdate();
    }

    @Unique
    private void drawTextBalloon(DrawContext context, int x, int y) {
        int textWidth = this.textRenderer.getWidth(updateMessage);
        int balloonWidth = textWidth + 10; // Add some padding
        int balloonHeight = 30;

        context.fill(x, y, x + balloonWidth, y + balloonHeight, 0x80000000); // Rounded rectangle

        // Draw the text
        int textX = x + 5;
        int textY = y + (balloonHeight - this.textRenderer.fontHeight) / 2;
        context.drawText(this.textRenderer, updateMessage, textX, textY, 0xFFFFFF, true);
    }

    @Unique
    private void checkForUpdate() {
        if (updateCheckAttempted) {
            return;
        }
        updateCheckAttempted = true;

        CompletableFuture.runAsync(() -> {
            try {
                TTVersion localVersion = VersionUtil.getLocalVersion();
                TTVersion latestVersion = VersionUtil.fetchLatestGitHubVersion();

                if (latestVersion != null) {
                    assert localVersion != null;
                    if (localVersion.isOlderThan(latestVersion)) {
                        updateMessage = "Update available: " + latestVersion;
                    } else if (localVersion.isNewerThan(latestVersion)) {
                        updateMessage = "You are on a dev version.";
                    } else {
                        updateMessage = "You are on the latest version.";
                    }
                } else {
                    updateMessage = "Unable to fetch the latest version.";
                }
            } catch (Exception ex) {
                updateMessage = "Failed to check for updates.";
            }
        });
    }
}
