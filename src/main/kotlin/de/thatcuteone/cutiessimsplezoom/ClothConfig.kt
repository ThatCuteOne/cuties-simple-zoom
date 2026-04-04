package de.thatcuteone.cutiessimsplezoom

import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component


private var minecraftClient: Minecraft = Minecraft.getInstance()

object ClothConfig {
    fun buildConfigScreen(): Screen {
        val builder: ConfigBuilder = ConfigBuilder.create().setParentScreen(minecraftClient.screen).setTitle(Component.translatable("title.cuties-simple-zoom.config"))
        val general: ConfigCategory = builder.getOrCreateCategory(Component.translatable("category.cuties-simple-zoom.general"))
        val entryBuilder = builder.entryBuilder()
        general.addEntry(
            entryBuilder.startIntSlider(Component.translatable("option.cuties-simple-zoom.defaultZoomLevel"), config.defaultZoomLevel,1,100)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.defaultZoomLevel.description"))
                .setSaveConsumer { newValue ->
                    config.defaultZoomLevel = newValue
                }
                .setTextGetter { value ->
                    Component.literal(value.toString())
                }
                .build())
        general.addEntry(
            entryBuilder.startIntSlider(Component.translatable("option.cuties-simple-zoom.zoomSpeed"), config.zoomSpeed,1,26)
                .setDefaultValue(25)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.zoomSpeed.description"))
                .setSaveConsumer { newValue ->
                    config.zoomSpeed = newValue
                }.setTextGetter { value ->
                    if (value >= 26){
                        Component.translatable("option.cuties-simple-zoom.instantZoomLevel")
                    } else {Component.literal(value.toString());}

                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Component.translatable("option.cuties-simple-zoom.zoomInStep"), config.zoomInStep)
                .setDefaultValue(1.1)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.zoomInStep.description"))
                .setSaveConsumer { newValue ->
                    config.zoomInStep = newValue.coerceIn(1.0,50.0)
                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Component.translatable("option.cuties-simple-zoom.zoomOutStep"), config.zoomOutStep)
                .setDefaultValue(0.9)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.zoomOutStep.description"))
                .setSaveConsumer { newValue ->
                    config.zoomOutStep = newValue.coerceIn(0.0,1.0)
                }
                .build())
        general.addEntry(
            entryBuilder.startBooleanToggle(Component.translatable("option.cuties-simple-zoom.sensitivityScaling"), config.sensitivityScaling)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.sensitivityScaling.description"))
                .setSaveConsumer { newValue ->
                    config.sensitivityScaling = newValue
                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Component.translatable("option.cuties-simple-zoom.sensitivityScalingFactor"), config.sensitivityScalingFactor)
                .setDefaultValue(1.0)
                .setTooltip(Component.translatable("option.cuties-simple-zoom.sensitivityScalingFactor.description"))
                .setSaveConsumer { newValue ->
                    config.sensitivityScalingFactor = newValue.coerceAtLeast(0.01)
                }
                .build())
        builder.setSavingRunnable{
            config.save()


        }
        return builder.build()
    }
}