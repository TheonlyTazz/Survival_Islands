package net.tazz.survival_islands.mixin;


import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.tazz.survival_islands.world.util.SeedStealer;

@Mixin(targets = {"net/minecraft/world/level/levelgen/RandomState$1NoiseWiringHelper"})
public class MixinNoiseWiringHelper implements SeedStealer {
    private long survivalIslands$seed;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void captureCtorForIsland(NoiseGeneratorSettings settings, HolderGetter noiseParametersGetter, long levelSeed, CallbackInfo ci) {
        this.survivalIslands$seed = levelSeed;
    }



    @Override
    public long steal() {
        return this.survivalIslands$seed;
    }
}
