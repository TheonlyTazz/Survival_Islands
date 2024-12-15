package net.tazz.survival_islands;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.tazz.survival_islands.world.IslandDensityFunctions;
import org.slf4j.Logger;

@Mod(Survival_islands.MODID)
public class Survival_islands {
    public static final String MODID = "survival_islands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Survival_islands(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerFunctions);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup completed!");
    }

    private void registerFunctions(RegisterEvent event) {
        if (event.getRegistry().equals(BuiltInRegistries.DENSITY_FUNCTION_TYPE)) {
            LOGGER.info("Registering density functions!");

            IslandDensityFunctions.init();
            
        }
    }
}
