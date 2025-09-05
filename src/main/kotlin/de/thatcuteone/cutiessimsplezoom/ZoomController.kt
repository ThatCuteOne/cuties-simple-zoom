package de.thatcuteone.cutiessimsplezoom

import net.minecraft.client.MinecraftClient
import kotlin.math.exp

class ZoomController {
    private var minecraftClient: MinecraftClient = MinecraftClient.getInstance()
    var isZooming: Boolean = false
    private var defaultSensitivity: Double = 0.0
    private var sensitivitySaved: Boolean = false
    private var targetMultiplier: Float = 1.0f
    private var currentMultiplier: Float = 1.0f
    private var previousMultiplier: Float = 1.0f
    private var ticks: Int = 0

    private var lastcalltime: Float = 0f

    fun tickInterpolate(){
        ticks += 1
        previousMultiplier = currentMultiplier
        currentMultiplier += (targetMultiplier - currentMultiplier) * (config.zoomSpeed.toFloat() /100)
    }

    fun scrollDown(){
        if (isZooming)  targetMultiplier =(targetMultiplier* config.zoomOutStep.toFloat()).coerceIn(0.02f,1.0f)
    }
    fun scrollUp(){
        if (isZooming)  targetMultiplier = (targetMultiplier * config.zoomInStep.toFloat()).coerceIn(0.02f,1.0f)
    }

    private fun setSensitivity(){
        if(!sensitivitySaved){
            this.defaultSensitivity = minecraftClient.options.mouseSensitivity.value
            this.sensitivitySaved = true
        }
        minecraftClient.options.mouseSensitivity.value = (defaultSensitivity * (currentMultiplier / config.sensitivityScalingFactor))
    }

    private fun resetSensitivity() {
        if (sensitivitySaved) {
            minecraftClient.options.mouseSensitivity.value = defaultSensitivity
            sensitivitySaved = false
        }
    }
    fun zoom(currentFov:Float,tickProgress: Float):Float{
        if (!zoomKey.isPressed) {
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
        val ticktime: Float = tickProgress + ticks
        val frameTime: Float = ticktime * (1000.0f / 20.0f)
        val diff: Float = lastcalltime - frameTime
        //println("progress: $tickProgress \n ticks:$ticks \n frametime: $frameTime \n Diff: $diff")
        lastcalltime = frameTime

        return (currentFov * (previousMultiplier + (currentMultiplier - previousMultiplier) * exp((config.zoomSpeed) * diff)))
        }
}