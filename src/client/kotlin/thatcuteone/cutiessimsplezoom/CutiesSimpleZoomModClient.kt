package thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW



object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {;
        KeyBindingHelper.registerKeyBinding(zoomKey)
    }
}

fun changeFov(fov: Float): Float{
    if (zoomKey.isPressed){
        val res: Float = fov * currentZoomLevel.toFloat()
        return (res.coerceIn(1.0f,fov))
    }
    currentZoomLevel = defaultZoomLevel
    return fov

};
var currentZoomLevel: Double = 0.0;
var defaultZoomLevel: Double = 0.5;

var zoomInStep: Double = 1.1;
var zoomOutStep: Double = 0.9;

fun onMouseScroll(amount:Double) {
    if(!zoomKey.isPressed) {
        currentZoomLevel = defaultZoomLevel
        return;
    }

    if(amount < 0) currentZoomLevel *= zoomInStep;
    else if (amount > 0) currentZoomLevel *= zoomOutStep

    if (currentZoomLevel > 1.0) currentZoomLevel = 1.0


};



var zoomKey: KeyBinding = (
        KeyBinding(
            "key.cutiessimplezoom.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.category.cutiessimplezoom.zoom"
    )
)