package de.thatcuteone.cutiessimsplezoom.mixin;

import de.thatcuteone.cutiessimsplezoom.CutiesSimpleZoomModClientKt;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Inject(at = @At("RETURN"), method = "getFov",cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onGetFov(Camera camera, float tickProgress, boolean changingFov, CallbackInfoReturnable<Float> cir){
        float originalFov = cir.getReturnValue();
        cir.setReturnValue(CutiesSimpleZoomModClientKt.getFov(originalFov,tickProgress));
    };
}