package de.thatcuteone.cutiessimsplezoom.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ZoomConfig {

    private static ZoomConfig instance;

    private static final File configFile = new File("config/cutiessimplezoom.json");
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final Gson gsonLenient = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.LENIENT)
            .create();




    @SerializedName("defaultZoomLevel")
    private int defaultZoomLevel = 50;

    @SerializedName("zoomInStep")
    private double zoomInStep = 1.1;

    @SerializedName("zoomOutStep")
    private double zoomOutStep = 0.9;

    @SerializedName("sensitivityScaling")
    private boolean sensitivityScaling = true;

    @SerializedName("sensitivityScalingFactor")
    private double sensitivityScalingFactor = 0.5;

    @SerializedName("zoomSpeed")
    private int zoomSpeed = 25;

    // Getters and Setters
    public int getDefaultZoomLevel() {
        return defaultZoomLevel;
    }

    public void setDefaultZoomLevel(int defaultZoomLevel) {
        this.defaultZoomLevel = defaultZoomLevel;
    }

    public double getZoomInStep() {
        return zoomInStep;
    }

    public void setZoomInStep(double zoomInStep) {
        this.zoomInStep = zoomInStep;
    }

    public double getZoomOutStep() {
        return zoomOutStep;
    }

    public void setZoomOutStep(double zoomOutStep) {
        this.zoomOutStep = zoomOutStep;
    }

    public boolean sensitivityScalingEnabled() {
        return sensitivityScaling;
    }

    public void setSensitivityScaling(boolean sensitivityScaling) {
        this.sensitivityScaling = sensitivityScaling;
    }

    public double getSensitivityScalingFactor() {
        return sensitivityScalingFactor;
    }

    public void setSensitivityScalingFactor(double sensitivityScalingFactor) {
        this.sensitivityScalingFactor = sensitivityScalingFactor;
    }

    public int getZoomSpeed() {
        return zoomSpeed;
    }

    public void setZoomSpeed(int zoomSpeed) {
        this.zoomSpeed = zoomSpeed;
    }

    public void save() {
        try {
            // Create parent directories if they don't exist
            Path parentDir = configFile.getParentFile().toPath();
            Files.createDirectories(parentDir);

            String json = gson.toJson(this);
            Files.write(configFile.toPath(), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ZoomConfig load() {
        if (!configFile.exists()) {
            ZoomConfig defaultConfig = new ZoomConfig();
            defaultConfig.save();
            return defaultConfig;
        }

        try {
            String jsonString = new String(Files.readAllBytes(configFile.toPath()));
            return gsonLenient.fromJson(jsonString, ZoomConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Return default config if loading fails
            return new ZoomConfig();
        }
    }

    public static ZoomConfig getInstance() {
        if (instance == null) {
            instance = ZoomConfig.load();
        }
        return instance;
    }
}