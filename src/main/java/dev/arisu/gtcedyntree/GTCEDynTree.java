package dev.arisu.gtcedyntree;

import com.ferreusveritas.dynamictrees.ModConstants;
import dev.arisu.gtcedyntree.proxy.CommonProxy;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = GTCEDynTree.MODID,
        name = GTCEDynTree.NAME,
        dependencies = GTCEDynTree.DEPENDENCIES,
        updateJSON = GTCEDynTree.UPDATE_URL
)
public class GTCEDynTree {

    public static final String MODID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String DEPENDENCIES =
            "required-after:" + ModConstants.DYNAMICTREES_LATEST + ";" +
                    GTValues.MOD_VERSION_DEP;
    public static final String UPDATE_URL = "@MOD_UPDATE_URL@";

    @Mod.Instance
    public static GTCEDynTree instance;

    @SidedProxy(clientSide = "dev.arisu.gtcedyntree.proxy.ClientProxy", serverSide = "dev.arisu.gtcedyntree.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        proxy.postInit();
    }

}
