package net.racialgamer.totemtweaks.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import static me.shedaniel.autoconfig.AutoConfig.getConfigHolder;


@Config(name = "totemtweaks")
public class Gui implements ConfigData {
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    public float totemSize = 1f;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    public float popSize = 0.3f;

    @ConfigEntry.Category("Totem")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    public boolean enableTotemSizeChange = false;

    @ConfigEntry.Category("Totem")
    public float totemSizeChangeSpeed = 1.0f;

    @ConfigEntry.Category("Totem")
    public float minTotemSize = 0.5f;

    @ConfigEntry.Category("Totem")
    public float maxTotemSize = 1.0f;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.PrefixText
    public boolean TotemPopAnimation = true;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    public boolean showTotemCount = false;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    public boolean showOverlay = false;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.ColorPicker
    public int overlayColor = 0xFF0000;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int overlayOpacity = 128;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int animationSpeed = 40;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    public boolean lockRotationPosition = false;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    public boolean disableRotations = false;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    public boolean staticSize = false;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int xPosition = 50;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public int yPosition = 50;

    @ConfigEntry.Category("Totem Pop")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    public boolean enableTotemPopSizeChange = false;

    @ConfigEntry.Category("Totem Pop")
    public float totemPopSizeChangeSpeed = 1.0f;

    @ConfigEntry.Category("Totem Pop")
    public float minTotemPopSize = 0.3f;

    @ConfigEntry.Category("Totem Pop")
    public float maxTotemPopSize = 0.9f;

    public static Gui get() {
        return getConfigHolder(Gui.class).getConfig();
    }
}