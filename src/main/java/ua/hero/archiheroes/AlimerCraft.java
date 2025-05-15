package ua.hero.archiheroes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.hero.archiheroes.handlers.AHConfig;
import ua.hero.archiheroes.handlers.GenericEventHandler;
import ua.hero.archiheroes.items.AHTab;
import ua.hero.archiheroes.network.packets.ArchiPacket;
import ua.hero.archiheroes.proxy.CommonProxy;
import ua.hero.archiheroes.world.generate.GenerateOre;

@Mod(
        modid = AlimerCraft.MODID,
        version = AlimerCraft.VERSION,
        name = AlimerCraft.NAME,
        dependencies = AlimerCraft.DEPENDENCIES
)
public class AlimerCraft {
    public static final String MODID = "alimercraft";
    public static final String NAME = "Alimer Craft";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:lucraftcore@[1.12.2-2.4.4,);required-after:ebwizardry@[4.3.6,);required-after:heroesexpansion@[1.12.2-1.3.4,)";

    public static SimpleNetworkWrapper packetHandler;

    @Mod.Instance("alimercraft")

    public static AlimerCraft instance;
    public ElementsAlimerMod elements = new ElementsAlimerMod();

    @SidedProxy(
            clientSide = "ua.hero.archiheroes.proxy.ClientProxy",
            serverSide = "ua.hero.archiheroes.proxy.CommonProxy"
    )
    public static CommonProxy proxy;
    public static final CreativeTabs AHTAB = new AHTab();
    public static final Logger logger = LogManager.getLogger("ArchiHeroes");
    public static final int howCoolAmI = Integer.MAX_VALUE;

    @EventHandler
    public void load(FMLInitializationEvent event) {

        GameRegistry.registerWorldGenerator(new GenerateOre(), 0); //Класс генератора и его ID (для каждого генератора нужен уникальный ID)

        proxy.registerRenderers();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // NO-OP
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.registerWorldGenerator(this.elements, 5);
        GameRegistry.registerFuelHandler(this.elements);


        this.elements.preInit(event);
        MinecraftForge.EVENT_BUS.register(this.elements);
        this.elements.getElements().forEach((element) -> {
            element.preInit(event);
        });



        AHConfig.load(event);

        packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel("AlimerHeroesChannel");
        packetHandler.registerMessage(ArchiPacket.Handler.class, ArchiPacket.class, 1, Side.CLIENT);

        MinecraftForge.EVENT_BUS.register(new GenericEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);

    }


    static {
        packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel("alimercraft");
    }
}
