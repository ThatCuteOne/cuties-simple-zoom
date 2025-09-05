package de.thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.MinecraftClient

lateinit var Zoom: ZoomController

object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {;
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick)
        KeyBindingHelper.registerKeyBinding(zoomKey)
        Zoom = ZoomController()
    }
    fun onTick(Minecraft: MinecraftClient){
        Zoom.tickInterpolate()
    }
}

fun getFov(currentFov:Float,tickProgress:Float):Float{
    val a = Zoom.zoom(currentFov,tickProgress)
    println(a)
    return a
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