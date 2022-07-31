package dev.arisu.gtcedyntree.proxy;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import dev.arisu.gtcedyntree.GTCEDynTree;
import dev.arisu.gtcedyntree.GTCEDynTreeConfigs;
import dev.arisu.gtcedyntree.dropcreators.DropCreatorResin;
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
            ConfigHolder.worldgen.disableRubberTreeGeneration = true;
        }
    }

    public void init() {
        TreeRegistry.findSpecies(new ResourceLocation(GTCEDynTree.MODID, "rubber")).
                addDropCreator(new DropCreatorResin(MetaItems.STICKY_RESIN.getStackForm(), (float) GTCEDynTreeConfigs.rubberDropMultiplier));

        registerSaplingReplacement(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("gregtech", "rubber_sapling"))), "rubber");
    }

    public void postInit() {
    }

}
