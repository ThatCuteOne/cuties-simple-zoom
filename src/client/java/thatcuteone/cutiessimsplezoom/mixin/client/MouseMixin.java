package thatcuteone.cutiessimsplezoom.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thatcuteone.cutiessimsplezoom.CutiesSimpleZoomModClientKt;


@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(at = @At("RETURN"),method = "onMouseScroll")
    private void OnMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        CutiesSimpleZoomModClientKt.onMouseScroll(vertical);
    };
    @WrapWithCondition(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V"),
            method = "onMouseScroll(JDD)V")
    private boolean stopMouseScroll(PlayerInventory inventory,int slot) {
      return !CutiesSimpleZoomModClientKt.getZoomKey().isPressed();
    };
}
