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


fun getFov(currentFov:Float,tickProgress:Float):Float{
    //println(tickProgress) for other interpolation
    return Zoom.zoom(currentFov)
}
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