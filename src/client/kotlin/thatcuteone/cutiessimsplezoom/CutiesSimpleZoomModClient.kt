package thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.option.SimpleOption
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

var Minecraft: MinecraftClient = MinecraftClient.getInstance()

object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {;
        KeyBindingHelper.registerKeyBinding(zoomKey)
    }
}
private var currentZoomLevel: Double = 0.0
private var defaultSensitivity: Double? = null

fun changeFov(fov: Float): Float{
    val sensitivitySetting: SimpleOption<Double> = Minecraft.options.mouseSensitivity

    if (zoomKey.isPressed){
        val res: Float = (fov * currentZoomLevel.toFloat()).coerceIn(1.0f,fov)
        if (config.sensitivityScaling){
            if(defaultSensitivity == null) defaultSensitivity = sensitivitySetting.getValue()
            // WTF IS THIS AHAHOASIHFJOIASHFIOH
            sensitivitySetting.setValue((defaultSensitivity?.times(currentZoomLevel))?.times(config.sensitivityScalingFactor)?.coerceIn(0.0,defaultSensitivity))
        }


        return res
    }
    if (defaultSensitivity != null) sensitivitySetting.setValue(defaultSensitivity); defaultSensitivity = null
    currentZoomLevel = config.defaultZoomLevel
    return fov
};

fun onMouseScroll(amount:Double) {
    if(!zoomKey.isPressed) {
        currentZoomLevel = config.defaultZoomLevel
        return;
    }

    if(amount < 0) currentZoomLevel *= config.zoomInStep
    else if (amount > 0) currentZoomLevel *= config.zoomOutStep

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