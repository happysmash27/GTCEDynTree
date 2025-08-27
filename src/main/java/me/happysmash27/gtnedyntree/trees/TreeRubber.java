package me.happysmash27.gtnedyntree.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.growthlogic.ConiferLogic;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenConiferTopper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import me.happysmash27.gtnedyntree.GTNEDynTree;
import me.happysmash27.gtnedyntree.GTNEDynTreeConfigs;
import me.happysmash27.gtnedyntree.ModContent;
import me.happysmash27.gtnedyntree.blocks.BlockBranchRubber;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Objects;

public class TreeRubber extends TreeFamily {

    public static Block leavesBlock = MetaBlocks.LEAVES;
    public static Block logBlock = MetaBlocks.LOG;
    public static Block saplingBlock = MetaBlocks.SAPLING;

    public static BlockBranch rubberBranch = new BlockBranchRubber("branch");

    public TreeRubber() {
        super(new ResourceLocation(GTNEDynTree.MODID, "rubber"));

        //Activates the conifer tops
        hasConiferVariants = true;

        setPrimitiveLog(logBlock.getDefaultState());

        ModContent.rubberLeavesProperties.setTree(this);

        addConnectableVanillaLeaves((state) -> state.getBlock() == leavesBlock);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        ItemStack stack = new ItemStack(Objects.requireNonNull(logBlock), 1, 0);
        stack.setCount(MathHelper.clamp(qty, 0, 64));
        return stack;
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesRubber(this));
    }

    @Override
    public BlockBranch createBranch() {
        return rubberBranch;
    }

    public class SpeciesRubber extends Species {

        SpeciesRubber(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.rubberLeavesProperties);

            setSoilLongevity(2);

            if (GTNEDynTreeConfigs.classicLookingRubberTree) {
                setBasicGrowingParameters(0.9f, 10.0f, 6, 4, 0.8f);
                this.setGrowthLogicKit(new ConiferLogic(4f).setHeightVariation(2));
                this.addGenFeature(new FeatureGenConiferTopper(ModContent.rubberLeavesProperties));
            } else {
                setBasicGrowingParameters(0.2f, 14.0f, 10, 8, 1.25f);
                this.addGenFeature(new FeatureGenClearVolume(12));
            }

            envFactor(BiomeDictionary.Type.COLD, 0.75f);
            envFactor(BiomeDictionary.Type.WET, 1.5f);
            envFactor(BiomeDictionary.Type.DRY, 0.5f);
            envFactor(BiomeDictionary.Type.FOREST, 1.1f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean useDefaultWailaBody() {
            return false;
        }

        @Override
        public void addJoCodes() {
            joCodeStore.addCodesFromFile(this,
                    GTNEDynTreeConfigs.classicLookingRubberTree ?
                            "assets/" + GTNEDynTree.MODID + "/trees/rubber_classic.txt" :
                            "assets/" + GTNEDynTree.MODID + "/trees/rubber.txt"
            );
        }
    }
}
