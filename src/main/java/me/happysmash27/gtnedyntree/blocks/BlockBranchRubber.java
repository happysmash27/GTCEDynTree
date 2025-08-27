package me.happysmash27.gtnedyntree.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockBranchBasic;
import me.happysmash27.gtnedyntree.GTNEDynTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBranchRubber extends BlockBranchBasic {

    public BlockBranchRubber(String name) {
        super(new ResourceLocation(GTNEDynTree.MODID, "rubber" + name).toString());
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
        int radius = getRadius(blockState);
        return 2f * (radius * radius) / 64.0f * 8.0f;
    }

    @Override
    public int getMaxRadius() {
        return 7;
    }
}
