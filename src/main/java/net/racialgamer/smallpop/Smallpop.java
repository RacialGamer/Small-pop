package net.racialgamer.smallpop;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.racialgamer.smallpop.config.Gui;

public class Smallpop implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(Gui.class, GsonConfigSerializer::new);
    }
}
