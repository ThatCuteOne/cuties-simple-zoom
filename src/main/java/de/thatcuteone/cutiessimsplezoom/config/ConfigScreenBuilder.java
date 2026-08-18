package de.thatcuteone.cutiessimsplezoom.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class ConfigScreenBuilder {
    private static final Minecraft minecraftClient = Minecraft.getInstance();
    private static final ZoomConfig config = ZoomConfig.getInstance();
    private static final ZoomConfig defaultConfig = new ZoomConfig();


    private final ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(minecraftClient.gui.screen()).setTitle(Component.translatable("title.cuties-simple-zoom.config"));
    private final ConfigCategory generalCategory = configBuilder.getOrCreateCategory(Component.translatable("category.cuties-simple-zoom.general"));

    public Screen build() {
        configBuilder.setParentScreen(minecraftClient.gui.screen());
        generalCategory.addEntry(
                getDefaultZoomEntry()
        );
        generalCategory.addEntry(
                getZoomSpeedEntry()
        );
        generalCategory.addEntry(
                getZoomInStepEntry()
        );
        generalCategory.addEntry(
                getZoomOutStepEntry()
        );
        generalCategory.addEntry(
                getSensitivityScalingToggleEntry()
        );
        generalCategory.addEntry(
                getSensitivityFactorEntry()
        );
        configBuilder.setSavingRunnable(config::save);
        return configBuilder.build();
    }


    private IntSliderBuilder getSliderBuilder(Component text,Component tooltip, int currentValue, int minValue, int maxValue,int defaultValue) {
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        return entryBuilder.startIntSlider(text,currentValue,minValue,maxValue).setTooltip(tooltip).setDefaultValue(defaultValue);
    }
    private DoubleFieldBuilder getFieldBuilder(Component text,Component tooltip, double currentValue,double defaultValue) {
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        return entryBuilder.startDoubleField(text,currentValue).setTooltip(tooltip).setDefaultValue(defaultValue);
    }
    private BooleanToggleBuilder getBoolBuilder(Component text, Component tooltip, boolean currentValue,boolean defaultValue) {
        ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
        return entryBuilder.startBooleanToggle(text,currentValue).setTooltip(tooltip).setDefaultValue(defaultValue);
    }



    private IntegerSliderEntry getDefaultZoomEntry() {
        IntSliderBuilder sliderBuilder = getSliderBuilder(
            Component.translatable("option.cuties-simple-zoom.defaultZoomLevel"),
            Component.translatable("option.cuties-simple-zoom.defaultZoomLevel.description"),
            config.getDefaultZoomLevel(),1,100,
            defaultConfig.getDefaultZoomLevel()
        );
        sliderBuilder.setSaveConsumer(config::setDefaultZoomLevel);
        sliderBuilder.setTextGetter(
                val -> Component.literal(val.toString())
        );
        return sliderBuilder.build();
    }

    private IntegerSliderEntry getZoomSpeedEntry() {
        IntSliderBuilder sliderBuilder = getSliderBuilder(
                Component.translatable("option.cuties-simple-zoom.zoomSpeed"),
                Component.translatable("option.cuties-simple-zoom.zoomSpeed.description"),
                config.getZoomSpeed(),1,26,
                defaultConfig.getZoomSpeed()
        );
        sliderBuilder.setSaveConsumer(config::setZoomSpeed);
        sliderBuilder.setTextGetter(
                val -> {
                    if (val >= 26) return Component.translatable("option.cuties-simple-zoom.instantZoomLevel");
                    return Component.literal(val.toString());
                }
        );
        return sliderBuilder.build();

    }

    private DoubleListEntry getZoomInStepEntry() {
        DoubleFieldBuilder fieldBuilder = getFieldBuilder(
                Component.translatable("option.cuties-simple-zoom.zoomInStep"),
                Component.translatable("option.cuties-simple-zoom.zoomInStep.description"),
                config.getZoomInStep(),
                defaultConfig.getZoomInStep()
        );
        fieldBuilder.setSaveConsumer(val -> config.setZoomInStep(Math.clamp(val, 1.0, 50.0)));
        return fieldBuilder.build();
    }

    private DoubleListEntry getZoomOutStepEntry() {
        DoubleFieldBuilder fieldBuilder = getFieldBuilder(
                Component.translatable("option.cuties-simple-zoom.zoomOutStep"),
                Component.translatable("option.cuties-simple-zoom.zoomOutStep.description"),
                config.getZoomOutStep(),
                defaultConfig.getZoomOutStep()
        );
        fieldBuilder.setSaveConsumer(val -> config.setZoomOutStep(Math.clamp(val, 0.0, 1.0)));
        return fieldBuilder.build();
    }
    private BooleanListEntry getSensitivityScalingToggleEntry() {
        BooleanToggleBuilder builder = getBoolBuilder(
                Component.translatable("option.cuties-simple-zoom.sensitivityScaling"),
                Component.translatable("option.cuties-simple-zoom.sensitivityScaling.description"),
                config.sensitivityScalingEnabled(),
                defaultConfig.sensitivityScalingEnabled()
        );
        return builder.setSaveConsumer(config::setSensitivityScaling).build();
    }
    private DoubleListEntry getSensitivityFactorEntry() {
        DoubleFieldBuilder fieldBuilder = getFieldBuilder(
                Component.translatable("option.cuties-simple-zoom.sensitivityScalingFactor"),
                Component.translatable("option.cuties-simple-zoom.sensitivityScalingFactor.description"),
                config.getSensitivityScalingFactor(),
                defaultConfig.getSensitivityScalingFactor()
        );
        fieldBuilder.setSaveConsumer(val -> config.setSensitivityScalingFactor(Math.max(0.01,val)));
        return fieldBuilder.build();
    }




}
