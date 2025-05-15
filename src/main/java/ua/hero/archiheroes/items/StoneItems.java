package ua.hero.archiheroes.items;

import java.awt.Color;
import lucraft.mods.lucraftcore.infinity.render.ItemRendererInfinityStone;
import lucraft.mods.lucraftcore.util.helper.ItemHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ua.hero.archiheroes.infinity.items.*;

@EventBusSubscriber(
        modid = "alimercraft"
)
public class StoneItems {

    public static Item TIME_STONE = new ItemTimeStone("ah_time_stone");
    public static Item WINTER_STONE = new ItemWinterStone("winter_stone");
    //public static Item SPACE_STONE = new ItemSpaceStone("space_stone");

    public StoneItems() {
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> e) {

        e.getRegistry().register(TIME_STONE);
        e.getRegistry().register(WINTER_STONE);
        //e.getRegistry().register(SPACE_STONE);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent e) {
        OBJLoader.INSTANCE.addDomain("alimercraft");
        ItemHelper.registerItemModel(TIME_STONE, "alimercraft", "ah_time_stone");
        ItemHelper.registerItemModel(WINTER_STONE, "alimercraft", "winter_stone");
        //ItemHelper.registerItemModel(SPACE_STONE, "alimercraft", "space_stone");

        TIME_STONE.setTileEntityItemStackRenderer(new ItemRendererInfinityStone(Color.GREEN, Color.LIGHT_GRAY));
        WINTER_STONE.setTileEntityItemStackRenderer(new ItemRendererInfinityStone(Color.LIGHT_GRAY, Color.LIGHT_GRAY));
        //SPACE_STONE.setTileEntityItemStackRenderer(new ItemRendererInfinityStone(Color.CYAN, Color.BLUE));
    }

    @SubscribeEvent
    public static void onMissingRegistries(RegistryEvent.MissingMappings<Item> e) {
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent e) {
    }

    @SideOnly(Side.CLIENT)
    public static void processModel(IRegistry<ModelResourceLocation, IBakedModel> registry, ModelResourceLocation normal, ModelResourceLocation model3D) {

    }
}