package thatcuteone.cutiessimsplezoom

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW



object CutiesSimpleZoomModClient : ClientModInitializer {
	override fun onInitializeClient() {;
        KeyBindingHelper.registerKeyBinding(zoomKey)
    }


}

fun changeFov(fov: Float): Float{
    if (zoomKey.isPressed){
        return (fov * 0.5f)
    }
    return fov

};

var zoomKey: KeyBinding = (
        KeyBinding(
            "key.cutiessimplezoom.zoom",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.category.cutiessimplezoom.zoom"
        ))