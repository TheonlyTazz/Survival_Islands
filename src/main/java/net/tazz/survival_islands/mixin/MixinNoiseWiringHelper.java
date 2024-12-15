package net.tazz.survival_islands.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.tazz.survival_islands.world.util.SeedStealer;
import org.jline.utils.Log;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net/minecraft/world/level/levelgen/RandomState$1NoiseWiringHelper"})
public class MixinNoiseWiringHelper implements SeedStealer {
    private static final Logger LOGGER = LogUtils.getLogger();

    private long survivalIslands$seed;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void captureCtorForIsland(RandomState state, long l, boolean b, CallbackInfo ci) {
        this.survivalIslands$seed = l;
        LOGGER.info("Seed is " + survivalIslands$seed);
    }



    @Override
    public long steal() {

        return this.survivalIslands$seed;
    }
}
