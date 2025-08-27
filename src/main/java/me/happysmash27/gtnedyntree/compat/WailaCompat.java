package me.happysmash27.gtnedyntree.compat;

import me.happysmash27.gtnedyntree.blocks.BlockBranchRubber;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class WailaCompat implements IWailaPlugin {

    @Override
    public void register(IWailaRegistrar registrar) {
        WailaBranchHandlerRubber branchHandler = new WailaBranchHandlerRubber();

        registrar.registerBodyProvider(branchHandler, BlockBranchRubber.class);
        registrar.registerNBTProvider(branchHandler, BlockBranchRubber.class);
    }

}
