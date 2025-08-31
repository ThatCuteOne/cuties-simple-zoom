package de.thatcuteone.cutiessimsplezoom

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

private val configFile: File = File("config/cutiessimplezoom.json")


@Serializable
class ZoomConfig {
    var defaultZoomLevel: Double = 0.5
    var zoomInStep : Double = 1.1
    var zoomOutStep : Double = 0.9
    var sensitivityScaling: Boolean = true
    var sensitivityScalingFactor: Double = 1.0
     fun save() {
         val json = Json.encodeToString(this)
         configFile.writeText(json)
     }
    fun load(): ZoomConfig {
        if (!configFile.exists()){
            val default = ZoomConfig()
            default.save()
            return default
        }
        val json = Json {
            ignoreUnknownKeys = true
        }
        val jsonString = configFile.readText()
        return json.decodeFromString(jsonString)
    }
}


var config: ZoomConfig = ZoomConfig().load()
