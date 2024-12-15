package net.tazz.survival_islands;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.tazz.survival_islands.world.IslandDensityFunctions;
import org.slf4j.Logger;

@Mod(Survival_Islands.MODID)
public class Survival_Islands {
    public static final String MODID = "survival_islands";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Survival_Islands(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerFunctions);

        IslandDensityFunctions.REGISTER2.register(modEventBus);
        IslandDensityFunctions.init();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup completed!");
    }

    private void registerFunctions(RegisterEvent event) {
        if (event.getRegistry().equals(BuiltInRegistries.DENSITY_FUNCTION_TYPE)) {
            LOGGER.info("Registering density functions!");
        }
    }

}
