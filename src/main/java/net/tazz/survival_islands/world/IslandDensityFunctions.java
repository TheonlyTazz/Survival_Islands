package net.tazz.survival_islands.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazz.survival_islands.world.density.IslandContinentalNoiseFunction;

public final class IslandDensityFunctions {
    public static void init() {
        Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, ResourceLocation.fromNamespaceAndPath("survival_islands", "islandcont"), IslandContinentalNoiseFunction.UCODEC);
    }
}
