package de.thatcuteone.cutiessimsplezoom

import net.minecraft.client.Minecraft
import kotlin.math.exp

class ZoomController {
    private var minecraftClient: Minecraft = Minecraft.getInstance()
    var isZooming: Boolean = false
    private var defaultSensitivity: Double = 0.0
    private var sensitivitySaved: Boolean = false
    private var targetMultiplier: Float = 1.0f
    private var currentMultiplier: Float = 1.0f
    private var ticks: Int = 0

    private var lastcalltime: Float = 0f

    fun tickInterpolate(){
        ticks += 1
    }

    fun scrollDown(){
        if (isZooming)  targetMultiplier =(targetMultiplier* config.zoomOutStep.toFloat()).coerceIn(0.02f,1.0f)
    }
    fun scrollUp(){
        if (isZooming)  targetMultiplier = (targetMultiplier * config.zoomInStep.toFloat()).coerceIn(0.02f,1.0f)
    }

    private fun setSensitivity(){
        if(!sensitivitySaved){
            this.defaultSensitivity = minecraftClient.options.sensitivity().get()
            this.sensitivitySaved = true
        }
        minecraftClient.options.sensitivity().set((defaultSensitivity * (currentMultiplier / config.sensitivityScalingFactor)).coerceIn(0.0, this.defaultSensitivity))
    }

    private fun resetSensitivity() {
        if (sensitivitySaved) {
            minecraftClient.options.sensitivity().set(defaultSensitivity)
            sensitivitySaved = false
        }
    }
    fun zoom(currentFov:Float,tickProgress: Float):Float{
        if (!zoomKey.isDown) {
            if (isZooming){
                resetSensitivity()
                targetMultiplier = 1.0f
                isZooming = false
            }
        }
        else{
            if (config.sensitivityScaling) setSensitivity()
            if (!isZooming) targetMultiplier = config.defaultZoomLevel.toFloat() / 100
            isZooming = true
        }
        val tickTime: Float = tickProgress + ticks
        val frameTime: Float = tickTime /  20.0f
        val diff: Float = lastcalltime - frameTime
        lastcalltime = frameTime
        // is instant check
        if (config.zoomSpeed == 26){
            currentMultiplier = targetMultiplier
            return currentFov * currentMultiplier
        }
        currentMultiplier = targetMultiplier + (currentMultiplier - targetMultiplier) * exp((config.zoomSpeed) * diff)
        return currentFov * currentMultiplier
        }
}