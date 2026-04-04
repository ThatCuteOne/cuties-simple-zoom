package de.thatcuteone.cutiessimsplezoom.mixin;

import de.thatcuteone.cutiessimsplezoom.CutiesSimpleZoomModClientKt;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Camera.class)
public abstract class CameraMixin {
	@Inject(at = @At("RETURN"), method = "calculateFov", cancellable = true)
    private void onGetFov(float partialTicks, CallbackInfoReturnable<Float> cir){
        float originalFov = cir.getReturnValue();
        cir.setReturnValue(CutiesSimpleZoomModClientKt.getFov(originalFov,partialTicks));
    }
}