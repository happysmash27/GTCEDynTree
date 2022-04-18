package dev.arisu.gtcedyntree;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion.DendroPotionType;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import dev.arisu.gtcedyntree.blocks.BlockBranchRubber;
import dev.arisu.gtcedyntree.trees.TreeRubber;
import dev.arisu.gtcedyntree.worldgen.BiomeDataBasePopulator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = GTCEDynTree.MODID)
@ObjectHolder(GTCEDynTree.MODID)
public class ModContent {

    public static ILeavesProperties rubberLeavesProperties;
    public static ArrayList<TreeFamily> trees = new ArrayList<TreeFamily>();

    @SubscribeEvent
    public static void registerDataBasePopulators(final BiomeDataBasePopulatorRegistryEvent event) {
        event.register(new BiomeDataBasePopulator());
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        if (GTCEDynTreeConfigs.classicLookingRubberTree)
            rubberLeavesProperties = setUpLeaves(TreeRubber.leavesBlock, "conifer", 6, 13);
        else
            rubberLeavesProperties = setUpLeaves(TreeRubber.leavesBlock, "deciduous", 3, 13);

        LeavesPaging.getLeavesBlockForSequence(GTCEDynTree.MODID, 0, rubberLeavesProperties);

        TreeFamily rubberTree = new TreeRubber();
        Collections.addAll(trees, rubberTree);

        trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));
        ArrayList<Block> treeBlocks = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
        treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(GTCEDynTree.MODID).values());
        registry.registerAll(treeBlocks.toArray(new Block[0]));
    }

    public static ILeavesProperties setUpLeaves(Block leavesBlock, String cellKit, int smother, int light) {
        return new LeavesProperties(
                leavesBlock.getDefaultState(),
                new ItemStack(leavesBlock, 1, 0),
                TreeRegistry.findCellKit(cellKit)) {
            @Override
            public int getSmotherLeavesMax() {
                return smother; //Default: 4
            }

            @Override
            public int getLightRequirement() {
                return light; //Default: 13
            }

            @Override
            public ItemStack getPrimitiveLeavesItemStack() {
                return new ItemStack(leavesBlock, 1, 0);
            }
        };
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        ArrayList<Item> treeItems = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableItems(treeItems));
        registry.registerAll(treeItems.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        setUpSeedRecipes("rubber", new ItemStack(TreeRubber.saplingBlock));
    }

    public static void setUpSeedRecipes(String name, ItemStack treeSapling) {
        Species treeSpecies = TreeRegistry.findSpecies(new ResourceLocation(GTCEDynTree.MODID, name));
        ItemStack treeSeed = treeSpecies.getSeedStack(1);
        ItemStack treeTransformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSpecies.getFamily());
        BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotionType.TRANSFORM.getIndex()), treeSeed, treeTransformationPotion);
        ModRecipes.createDirtBucketExchangeRecipes(treeSapling, treeSeed, true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (TreeFamily tree : trees) {
            ModelHelper.regModel(tree.getDynamicBranch());
            ModelHelper.regModel(tree.getCommonSpecies().getSeed());
            ModelHelper.regModel(tree);
        }
        LeavesPaging.getLeavesMapForModId(GTCEDynTree.MODID).forEach((key, leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));
    }
}
