package me.happysmash27.gtnedyntree;

import com.ferreusveritas.dynamictrees.ModConstants;
import me.happysmash27.gtnedyntree.proxy.CommonProxy;
import gregtech.api.GTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = GTNEDynTree.MODID,
        name = GTNEDynTree.NAME,
        dependencies = GTNEDynTree.DEPENDENCIES,
        updateJSON = GTNEDynTree.UPDATE_URL
)
public class GTNEDynTree {

    public static final String MODID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String DEPENDENCIES =
            "required-after:" + ModConstants.DYNAMICTREES_LATEST + ";required-after:gregtech";
    public static final String UPDATE_URL = "@MOD_UPDATE_URL@";

    @Mod.Instance
    public static GTNEDynTree instance;

    @SidedProxy(clientSide = "me.happysmash27.gtnedyntree.proxy.ClientProxy", serverSide = "me.happysmash27.gtnedyntree.proxy.CommonProxy")
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
