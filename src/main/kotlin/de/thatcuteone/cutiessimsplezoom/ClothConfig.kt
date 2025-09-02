package de.thatcuteone.cutiessimsplezoom

import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigCategory
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen

import net.minecraft.text.Text


private var minecraftClient: MinecraftClient = MinecraftClient.getInstance()

object ClothConfig {
    fun buildConfigScreen(): Screen {
        val builder: ConfigBuilder = ConfigBuilder.create().setParentScreen(minecraftClient.currentScreen).setTitle(Text.translatable("title.cuties-simple-zoom.config"))
        val general: ConfigCategory = builder.getOrCreateCategory(Text.translatable("category.cuties-simple-zoom.general"))
        val entryBuilder = builder.entryBuilder()
        general.addEntry(
            entryBuilder.startIntSlider(Text.translatable("option.cuties-simple-zoom.defaultZoomLevel"), config.defaultZoomLevel,1,100)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.defaultZoomLevel.description"))
                .setSaveConsumer { newValue ->
                    config.defaultZoomLevel = newValue
                }
                .build())
        general.addEntry(
            entryBuilder.startIntSlider(Text.translatable("option.cuties-simple-zoom.zoomSpeed"), config.zoomSpeed,1,100)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.zoomSpeed.description"))
                .setSaveConsumer { newValue ->
                    config.zoomSpeed = newValue
                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Text.translatable("option.cuties-simple-zoom.zoomInStep"), config.zoomInStep)
                .setDefaultValue(1.1)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.zoomInStep.description"))
                .setSaveConsumer { newValue ->
                    config.zoomInStep = newValue.coerceIn(1.0,50.0)
                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Text.translatable("option.cuties-simple-zoom.zoomOutStep"), config.zoomOutStep)
                .setDefaultValue(0.9)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.zoomOutStep.description"))
                .setSaveConsumer { newValue ->
                    config.zoomOutStep = newValue.coerceIn(0.0,1.0)
                }
                .build())
        general.addEntry(
            entryBuilder.startBooleanToggle(Text.translatable("option.cuties-simple-zoom.sensitivityScaling"), config.sensitivityScaling)
                .setDefaultValue(true)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.sensitivityScaling.description"))
                .setSaveConsumer { newValue ->
                    config.sensitivityScaling = newValue
                }
                .build())
        general.addEntry(
            entryBuilder.startDoubleField(Text.translatable("option.cuties-simple-zoom.sensitivityScalingFactor"), config.sensitivityScalingFactor)
                .setDefaultValue(1.0)
                .setTooltip(Text.translatable("option.cuties-simple-zoom.sensitivityScalingFactor.description"))
                .setSaveConsumer { newValue ->
                    config.sensitivityScalingFactor = newValue
                }
                .build())
        builder.setSavingRunnable{
            config.save()


        }
        return builder.build()
    }
}