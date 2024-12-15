package net.tazz.survival_islands.world.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.tazz.survival_islands.Config;
import net.tazz.survival_islands.noise.IslandContinentalNoise;
import net.tazz.survival_islands.noise.OctaveNoise;
import net.tazz.survival_islands.util.LinkedHashCache;
import net.tazz.survival_islands.world.util.SeedStealer;

import java.util.Objects;

public class IslandContinentalNoiseFunction implements DensityFunction {
    public static final MapCodec<IslandContinentalNoiseFunction> UCODEC = MapCodec.unit(IslandContinentalNoiseFunction::new);
    public static final KeyDispatchDataCodec<IslandContinentalNoiseFunction> CODEC = KeyDispatchDataCodec.of(UCODEC);

    private static final LinkedHashCache<IslandContinentalNoise, IslandContinentalNoise> ISLAND_CONTINENTAL_NOISE_INSTANCE_CACHE =
            new LinkedHashCache<>(1, Integer.MAX_VALUE, 512);

    private final IslandContinentalNoise islandContinentalNoise;

    public IslandContinentalNoiseFunction() {
        this(0);
    }

    public IslandContinentalNoiseFunction(long seed) {
        RandomSource random = new XoroshiroRandomSource(seed);
        PositionalRandomFactory positionalRandomFactory = random.forkPositional();

        OctaveNoise domainWarpNoise = Config.domainWarpNoise.makeLive(positionalRandomFactory.fromHashOf("domain_warp_noise"));
        OctaveNoise rangeVariationNoise = Config.rangeVariationNose.makeLive(positionalRandomFactory.fromHashOf("range_variation_noise"));
        IslandContinentalNoise islandContinentalNoise = new IslandContinentalNoise(seed,
                Config.islandSize, Config.islandSeparation,
                Config.continentalTargetRangeAMin, Config.continentalTargetRangeAMax,
                Config.continentalTargetRangeBMin, Config.continentalTargetRangeBMax,
                Config.islandUnderwaterFalloffDistanceMultiplier,
                domainWarpNoise, rangeVariationNoise
        );
        this.islandContinentalNoise = ISLAND_CONTINENTAL_NOISE_INSTANCE_CACHE.computeIfAbsent(islandContinentalNoise, (k) -> islandContinentalNoise);
    }

    @Override
    public double compute(FunctionContext ctx) {
        return islandContinentalNoise.compute(ctx.blockX(), ctx.blockZ());
    }

    @Override
    public void fillArray(double[] ds, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(ds, this);
    }

    private static IslandContinentalNoiseFunction fork(long seed) {
        return new IslandContinentalNoiseFunction(seed);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {

        return this;
    }

    @Override
    public double minValue() {
        return islandContinentalNoise.minValue();
    }

    @Override
    public double maxValue() {
        return islandContinentalNoise.maxValue();
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandContinentalNoiseFunction that = (IslandContinentalNoiseFunction) o;
        return islandContinentalNoise.equals(that.islandContinentalNoise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandContinentalNoise);
    }

}
