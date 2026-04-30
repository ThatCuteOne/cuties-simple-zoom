package de.thatcuteone.cutiessimsplezoom;

import net.minecraft.client.Minecraft;
import de.thatcuteone.cutiessimsplezoom.config.ZoomConfig;
import static java.lang.Math.exp;

public class ZoomController {
    private final Minecraft minecraftClient ;
    private final ZoomConfig config = ZoomConfig.getInstance();
    public boolean isZooming = false;
    private double defaultSensitivity = 0.0;
    private boolean sensitivitySaved = false;
    private float targetMultiplier = 1.0f;
    private float currentMultiplier = 1.0f;
    private int ticks = 0;
    private float lastcalltime = 0f;

    public ZoomController(Minecraft minecraftInstance)  {
        this.minecraftClient = minecraftInstance;
    }


    public void tickInterpolate() {
        ticks += 1;
    }

    public void scrollDown() {
        if (isZooming) {
            targetMultiplier = (float) Math.clamp(targetMultiplier * config.getZoomOutStep(), 0.02f, 1.0f);
        }
    }

    public void scrollUp() {
        if (isZooming) {
            targetMultiplier = (float) Math.clamp(targetMultiplier * config.getZoomInStep(), 0.02f, 1.0f);
        }
    }

    private void setSensitivity() {
        if (!sensitivitySaved) {
            this.defaultSensitivity = minecraftClient.options.sensitivity().get();
            this.sensitivitySaved = true;
        }
        double newSensitivity = defaultSensitivity * (currentMultiplier / config.getSensitivityScalingFactor());
        newSensitivity = Math.clamp(newSensitivity, 0.0, this.defaultSensitivity);
        minecraftClient.options.sensitivity().set(newSensitivity);
    }

    private void resetSensitivity() {
        if (sensitivitySaved) {
            minecraftClient.options.sensitivity().set(defaultSensitivity);
            sensitivitySaved = false;
        }
    }

    public float zoom(float currentFov, float tickProgress,boolean isDown) {
        if (!isDown) {
            if (isZooming) {
                resetSensitivity();
                targetMultiplier = 1.0f;
                isZooming = false;
            }
        } else {
            if (config.sensitivityScalingEnabled()) {
                setSensitivity();
            }
            if (!isZooming) {
                targetMultiplier = (float) config.getDefaultZoomLevel() / 100;
            }
            isZooming = true;
        }

        float tickTime = tickProgress + ticks;
        float frameTime = tickTime / 20.0f;
        float diff = lastcalltime - frameTime;
        lastcalltime = frameTime;

        // is instant check
        if (config.getZoomSpeed() == 26) {
            currentMultiplier = targetMultiplier;
            return currentFov * currentMultiplier;
        }

        currentMultiplier = (float)(targetMultiplier + (currentMultiplier - targetMultiplier) * exp(config.getZoomSpeed() * diff));
        return currentFov * currentMultiplier;
    }
}