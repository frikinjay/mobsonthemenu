package com.frikinjay.mobsonthemenu.mixin;

import com.frikinjay.mobsonthemenu.MobsOnTheMenu;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void mobsOnTheMenu_resetEntity(CallbackInfo ci) {
        MobsOnTheMenu.livingEntity = null;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void mobsOnTheMenu_render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TitleScreen sc = (TitleScreen) (Object) this;
        if (MinecraftClient.getInstance() != null) {
            int entityY = sc.height / 4 + 132;
            int entityX = sc.width / 2 - 160;
            LivingEntity livingEntity = MobsOnTheMenu.livingEntity;
            if (livingEntity != null) {
                try {
                    MobsOnTheMenu.drawEntity(entityX, entityY, 30, -mouseX + entityX, -mouseY + entityY - 30, livingEntity);
                } catch (Exception e) {
                    MobsOnTheMenu.livingEntity = null;
                }
            }
        }
    }

}
