package de.thatcuteone.cutiessimsplezoom

import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.SimpleOption


class ZoomController {
    private var minecraftClient: MinecraftClient = MinecraftClient.getInstance()
    var isZooming: Boolean = false
    private var defaultSensitivity: Double = 0.0
    private var sensitivitySaved: Boolean = false
    private var fov: Float = config.defaultZoomLevel
    fun scrollDown(){
        this.fov = (fov * config.zoomOutStep.toFloat()).coerceIn(1.0f,110.0f)
    }
    fun scrollUp(){
        this.fov = (fov * config.zoomInStep.toFloat()).coerceIn(1.0f,110.0f)
    }

    private fun setSensitivity(){
        if(!sensitivitySaved){
            this.defaultSensitivity = minecraftClient.options.mouseSensitivity.value
            this.sensitivitySaved = true
        }
        val zoomPercent:Float = (this.fov * minecraftClient.options.fov.value) / 10000
        minecraftClient.options.mouseSensitivity.value = (defaultSensitivity * zoomPercent)
    }

    private fun resetSensitivity() {
        if (sensitivitySaved) {
            minecraftClient.options.mouseSensitivity.value = defaultSensitivity
            sensitivitySaved = false
        }
    }
    fun zoom(currentFov:Float):Float{

        if (!zoomKey.isPressed) {
            if (isZooming){
                resetSensitivity()
                this.fov = config.defaultZoomLevel
            }
            isZooming = false
            return currentFov
        }
        else{
            isZooming = true
            if (config.sensitivityScaling) setSensitivity()
            return fov
        }
    }
}