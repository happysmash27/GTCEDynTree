package me.happysmash27.gtcedyntree.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import me.happysmash27.gtcedyntree.GTCEDynTree;
import me.happysmash27.gtcedyntree.GTCEDynTreeConfigs;
import me.happysmash27.gtcedyntree.dropcreators.DropCreatorResin;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Objects;

public class CommonProxy {

    private static void registerSaplingReplacement(final Block saplingBlock, final String speciesName) {
        TreeRegistry.registerSaplingReplacer(saplingBlock.getDefaultState(), TreeRegistry.findSpecies(new ResourceLocation(GTCEDynTree.MODID, speciesName)));
    }

    public void preInit(FMLPreInitializationEvent event) {
        // Disable default rubber tree world gen.
        if (com.ferreusveritas.dynamictrees.ModConfigs.worldGen) {
            ConfigHolder.disableRubberTreeGeneration = true;
        }
    }

    public void init() {
        TreeRegistry.findSpecies(new ResourceLocation(GTCEDynTree.MODID, "rubber")).
                addDropCreator(new DropCreatorResin(MetaItems.RUBBER_DROP.getStackForm(), (float) GTCEDynTreeConfigs.rubberDropMultiplier));

        registerSaplingReplacement(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gregtech", "rubber_sapling"))), "rubber");
    }

    public void postInit() {
    }

}
