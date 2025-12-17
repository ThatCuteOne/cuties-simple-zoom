package de.thatcuteone.cutiessimsplezoom.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import de.thatcuteone.cutiessimsplezoom.CutiesSimpleZoomModClientKt;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(at = @At("RETURN"),method = "onScroll")
    private void OnMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        CutiesSimpleZoomModClientKt.onMouseScroll(vertical);
    };
    @WrapWithCondition(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V"),
            method = "onScroll(JDD)V")
    private boolean stopMouseScroll(Inventory inventory,int slot) {
      return !CutiesSimpleZoomModClientKt.getZoomKey().isDown();
    };
}
