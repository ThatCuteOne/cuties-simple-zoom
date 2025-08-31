package thatcuteone.cutiessimsplezoom

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.gui.screen.Screen
import  thatcuteone.cutiessimsplezoom.ClothConfig
class ModMenu : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory {
        ClothConfig.buildConfigScreen()
    }
}
