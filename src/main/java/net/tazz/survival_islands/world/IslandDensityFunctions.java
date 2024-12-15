package net.tazz.survival_islands.world;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazz.survival_islands.world.density.IslandContinentalNoiseFunction;


public final class IslandDensityFunctions {
    public static final DeferredRegister<MapCodec<? extends DensityFunction>> REGISTER2 = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, "survival_islands");

    public static void init() {
        REGISTER2.register("islandcont", () -> IslandContinentalNoiseFunction.UCODEC);
    }
}
