package de.thatcuteone.cutiessimsplezoom;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;


public class CutiesSimpleZoomClient implements ClientModInitializer {

    private static CutiesSimpleZoomClient instance;

    private ZoomController zoomController ;
    private final KeyMapping keyMapping = getKeyBind();

    public CutiesSimpleZoomClient() {
        instance = this;
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(this::onTick);
        KeyMappingHelper.registerKeyMapping(keyMapping);
        zoomController = new ZoomController(Minecraft.getInstance());
    }
    private void onTick(Minecraft minecraft) {
        zoomController.tickInterpolate();
    }
    public float onGetFOV(float current,float tickProgress) {
        return zoomController.zoom(current,tickProgress, keyMapping.isDown());
    }
    public void onMouseScroll(double amount) {
        if(amount < 0) zoomController.scrollUp();
        else if (amount > 0) zoomController.scrollDown();
    }

    public static CutiesSimpleZoomClient getInstance() {
        return instance;
    }

    public boolean isKeyDown() {
        return keyMapping.isDown();
    }

    private KeyMapping getKeyBind() {
        KeyMapping.Category category = new KeyMapping.Category(
                Identifier.parse("key.category.cutiessimplezoom.zoom")
        );
        return new KeyMapping(
                "key.cutiessimplezoom.zoom",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                category
        );
    }

}
