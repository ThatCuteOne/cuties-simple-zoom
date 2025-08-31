package de.thatcuteone.cutiessimsplezoom.mixin;

import de.thatcuteone.cutiessimsplezoom.CutiesSimpleZoomModClientKt;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Inject(at = @At("RETURN"), method = "getFov",cancellable = true)
    private void onGetFov(CallbackInfoReturnable<Float> r){
        float originalFov = r.getReturnValue();
        r.setReturnValue(CutiesSimpleZoomModClientKt.changeFov(originalFov));
    };
}