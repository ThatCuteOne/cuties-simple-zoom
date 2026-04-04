package de.thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import com.mojang.blaze3d.platform.InputConstants
import org.lwjgl.glfw.GLFW
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.resources.Identifier

lateinit var Zoom: ZoomController

object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(this::onTick)
        KeyMappingHelper.registerKeyMapping(zoomKey)
        Zoom = ZoomController()
    }
    fun onTick(Minecraft: Minecraft){
        Zoom.tickInterpolate()
    }
}

fun getFov(currentFov:Float,tickProgress:Float):Float{
    val a = Zoom.zoom(currentFov,tickProgress)
    return a
}
fun onMouseScroll(amount:Double) {
    if(amount < 0) Zoom.scrollUp()
    else if (amount > 0) Zoom.scrollDown()
}



var zoomKey: KeyMapping = (
        KeyMapping(
            "key.cutiessimplezoom.zoom",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            KeyMapping.Category.register(Identifier.parse("key.category.cutiessimplezoom.zoom"))
    )
)