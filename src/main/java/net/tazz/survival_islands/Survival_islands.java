package net.tazz.survival_islands;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import net.neoforged.neoforge.registries.RegisterEvent;
import net.tazz.survival_islands.world.IslandDensityFunctions;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Survival_islands.MODID)
public class Survival_islands {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "survival_islands";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    public Survival_islands(IEventBus modEventBus, ModContainer modContainer) {
        // Register lifecycle events
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerFunctions);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Initialize custom features
        IslandDensityFunctions.REGISTER2.register(modEventBus);
        IslandDensityFunctions.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup completed!");
    }

    private void registerFunctions(RegisterEvent event) {
        LOGGER.info("Registering functions!");
    }



}
