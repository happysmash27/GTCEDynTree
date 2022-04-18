package dev.arisu.gtcedyntree.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.growthlogic.ConiferLogic;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenConiferTopper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import dev.arisu.gtcedyntree.GTCEDynTree;
import dev.arisu.gtcedyntree.GTCEDynTreeConfigs;
import dev.arisu.gtcedyntree.ModContent;
import dev.arisu.gtcedyntree.blocks.BlockBranchRubber;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.Objects;

public class TreeRubber extends TreeFamily {

    public static Block leavesBlock = MetaBlocks.RUBBER_LEAVES;
    public static Block logBlock = MetaBlocks.RUBBER_LOG;
    public static Block saplingBlock = MetaBlocks.RUBBER_SAPLING;

    public static BlockBranch rubberBranch = new BlockBranchRubber("branch");

    public class SpeciesRubber extends Species {

        SpeciesRubber(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.rubberLeavesProperties);

            setSoilLongevity(2);

            if (GTCEDynTreeConfigs.classicLookingRubberTree) {
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
                    GTCEDynTreeConfigs.classicLookingRubberTree ?
                            "assets/" + GTCEDynTree.MODID + "/trees/rubber_classic.txt" :
                            "assets/" + GTCEDynTree.MODID + "/trees/rubber.txt"
            );
        }
    }

    public TreeRubber() {
        super(new ResourceLocation(GTCEDynTree.MODID, "rubber"));

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
}
