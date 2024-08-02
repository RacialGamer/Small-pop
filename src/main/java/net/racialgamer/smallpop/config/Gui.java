package net.racialgamer.smallpop.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static me.shedaniel.autoconfig.AutoConfig.getConfigHolder;


@Config(name = "smallpop")
public class Gui implements ConfigData {

    @ConfigEntry.Gui.PrefixText
    public float totemSize = 1f;
    @ConfigEntry.Gui.PrefixText
    public float popSize = 0.3f;

    @ConfigEntry.Category("Totem Animation")
    @ConfigEntry.Gui.PrefixText
    public boolean enableTotemSizeChange = false;
    @ConfigEntry.Category("Totem Animation")
    public float totemSizeChangeSpeed = 1.0f;
    @ConfigEntry.Category("Totem Animation")
    public float minTotemSize = 0.5f;
    @ConfigEntry.Category("Totem Animation")
    public float maxTotemSize = 1.0f;


    @ConfigEntry.Category("Totem Pop Animation")
    @ConfigEntry.Gui.PrefixText
    public boolean disableTotemPopAnimation = false;
    @ConfigEntry.Category("Totem Pop Animation")
    public int animationSpeed = 40;
    @ConfigEntry.Category("Totem Pop Animation")
    public boolean enableTotemPopPositionChange = false;
    @ConfigEntry.Category("Totem Pop Animation")
    public float totemPopPositionX = 0f;
    @ConfigEntry.Category("Totem Pop Animation")
    public float totemPopPositionY = 0f;
    @ConfigEntry.Category("Totem Pop Animation")
    @ConfigEntry.Gui.PrefixText
    public boolean enableTotemPopSizeChange = false;
    @ConfigEntry.Category("Totem Pop Animation")
    public float totemPopSizeChangeSpeed = 2.0f;
    @ConfigEntry.Category("Totem Pop Animation")
    public float minTotemPopSize = 0.3f;
    @ConfigEntry.Category("Totem Pop Animation")
    public float maxTotemPopSize = 0.9f;


    public static Gui get() {
        return getConfigHolder(Gui.class).getConfig();
    }
}