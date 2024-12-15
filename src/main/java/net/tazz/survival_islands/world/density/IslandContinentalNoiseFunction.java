package net.tazz.survival_islands.world.density;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.tazz.survival_islands.Survival_Islands;
import net.tazz.survival_islands.Config;
import net.tazz.survival_islands.noise.IslandContinentalNoise;
import net.tazz.survival_islands.noise.OctaveNoise;
import net.tazz.survival_islands.util.ConcurrentLinkedHashCache;
import net.tazz.survival_islands.world.util.SeedStealer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import net.tazz.survival_islands.world.IslandDensityFunctions;

import java.util.Objects;



public class IslandContinentalNoiseFunction implements DensityFunction {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final MapCodec<IslandContinentalNoiseFunction> UCODEC = MapCodec.unit(IslandContinentalNoiseFunction::new);
    public static final KeyDispatchDataCodec<IslandContinentalNoiseFunction> CODEC = KeyDispatchDataCodec.of(UCODEC);

    private static final ConcurrentLinkedHashCache<IslandContinentalNoise, IslandContinentalNoise> ISLAND_CONTINENTAL_NOISE_INSTANCE_CACHE =
            new ConcurrentLinkedHashCache<>(1, Integer.MAX_VALUE, 512);

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
                Config.continentalTargetRangeA.min(), Config.continentalTargetRangeA.max(),
                Config.continentalTargetRangeB.max(), Config.continentalTargetRangeB.max(),
                Config.islandUnderwaterFalloffDistanceMultiplier,
                domainWarpNoise, rangeVariationNoise
        );
        LOGGER.info("randomsource: {}", random);
        this.islandContinentalNoise = ISLAND_CONTINENTAL_NOISE_INSTANCE_CACHE.computeIfAbsent(islandContinentalNoise, (k) -> islandContinentalNoise);
    }

    @Override
    public double compute(FunctionContext ctx) {
        LOGGER.info("We are still logging");
        return islandContinentalNoise.compute(ctx.blockX(), ctx.blockZ());
    }

    @Override
    public void fillArray(double[] ds, ContextProvider contextProvider) {
        LOGGER.info("We are Filling Arrays");
        contextProvider.fillAllDirectly(ds, this);
    }

    private static IslandContinentalNoiseFunction fork(long seed) {
        LOGGER.info("Forking islandContinentalNoiseFunction");
        return new IslandContinentalNoiseFunction(seed);
    }

    @Override
    public @NotNull DensityFunction mapAll(Visitor visitor) {
        if (visitor instanceof SeedStealer seed) {
            return fork(seed.steal());
        }
        return this;
    }

    @Override
    public double minValue() {
        LOGGER.info("Minvalue of islandContinentalNoiseFunction {}", islandContinentalNoise.minValue());
        return islandContinentalNoise.minValue();
    }

    @Override
    public double maxValue() {
        LOGGER.info("Maxvalue of islandContinentalNoiseFunction {}", islandContinentalNoise.maxValue());
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
