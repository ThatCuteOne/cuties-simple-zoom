package de.thatcuteone.cutiessimsplezoom

import net.minecraft.client.MinecraftClient
import kotlin.math.abs


class ZoomController {
    private var minecraftClient: MinecraftClient = MinecraftClient.getInstance()
    var isZooming: Boolean = false
    private var defaultSensitivity: Double = 0.0
    private var sensitivitySaved: Boolean = false
    private var fov: Float = config.defaultZoomLevel
    private var targetFov = config.defaultZoomLevel

    fun scrollDown(){
        this.targetFov = (targetFov * config.zoomOutStep.toFloat()).coerceIn(1.0f,110.0f)
    }
    fun scrollUp(){
        this.targetFov = (targetFov * config.zoomInStep.toFloat()).coerceIn(1.0f,110.0f)
    }
    private fun applyEasing():Float{
        val result = fov + (targetFov - fov) * (config.zoomSpeed / 100f)
        fov = result
        return result
    }

    private fun setSensitivity(){
        if(!sensitivitySaved){
            this.defaultSensitivity = minecraftClient.options.mouseSensitivity.value
            this.sensitivitySaved = true
        }
        val zoomPercent:Float = (this.fov * minecraftClient.options.fov.value) / 10000
        minecraftClient.options.mouseSensitivity.value = (defaultSensitivity * zoomPercent) * config.sensitivityScalingFactor.coerceIn(0.0,1.0)
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
                targetFov = config.defaultZoomLevel
            }
            isZooming = false
            return currentFov
        }
        else{
            isZooming = true
            if (config.sensitivityScaling) setSensitivity()
            return applyEasing().coerceIn(1.0f,currentFov)
        }
    }
}