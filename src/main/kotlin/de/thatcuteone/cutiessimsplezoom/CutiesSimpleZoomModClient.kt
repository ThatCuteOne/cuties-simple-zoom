package de.thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

lateinit var Zoom: ZoomController

object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {;
        KeyBindingHelper.registerKeyBinding(zoomKey)
        Zoom = ZoomController()
    }
}


fun getFov(currentFov:Float):Float{
    return Zoom.zoom(currentFov)
}




//fun changeFov(fov: Float): Float{
//    val sensitivitySetting: SimpleOption<Double> = Minecraft.options.mouseSensitivity
//
//    if (zoomKey.isPressed){
//        val res: Float = (fov * currentZoomLevel.toFloat()).coerceIn(1.0f,fov)
//        if (config.sensitivityScaling){
//            if(defaultSensitivity == null) defaultSensitivity = sensitivitySetting.getValue()
//            // WTF IS THIS AHAHOASIHFJOIASHFIOH
//            sensitivitySetting.setValue((defaultSensitivity?.times(currentZoomLevel))?.times(config.sensitivityScalingFactor)?.coerceIn(0.0,defaultSensitivity))
//        }
//
//
//        return res
//    }
//    if (defaultSensitivity != null) sensitivitySetting.setValue(defaultSensitivity); defaultSensitivity = null
//    currentZoomLevel = config.defaultZoomLevel
//    return fov
//};

fun onMouseScroll(amount:Double) {
    if(amount < 0) Zoom.scrollUp()
    else if (amount > 0) Zoom.scrollDown()


};



var zoomKey: KeyBinding = (
        KeyBinding(
            "key.cutiessimplezoom.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.category.cutiessimplezoom.zoom"
    )
)