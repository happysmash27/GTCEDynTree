package dev.arisu.gtcedyntree;

import net.minecraftforge.common.config.Config;

@Config(modid = GTCEDynTree.MODID, category = "trees")
public class GTCEDynTreeConfigs {

    @Config.Name("classicLookingRubberTree")
    @Config.Comment("Makes rubber trees shorter and pointier, similar to the non-dynamic version.")
    @Config.RequiresMcRestart
    public static boolean classicLookingRubberTree = false;

    @Config.Name("rubberDropMultiplier")
    @Config.Comment("The multiplier for rubber dropped when chopping a Rubber tree down.")
    @Config.RangeDouble(min = 0.0, max = 128.0)
    @Config.RequiresMcRestart
    public static double rubberDropMultiplier = 0.8;
}
