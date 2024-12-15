package net.tazz.survival_islands;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.tazz.survival_islands.noise.OctaveNoiseRecipe2D;
import net.tazz.survival_islands.util.FloatRange;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Survival_Islands.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Double> ISLAND_SIZE = BUILDER.comment("Island Size:").define("islandSize", 128.0);
    private static final ModConfigSpec.ConfigValue<Integer> ISLAND_SEPERATION = BUILDER.comment("Island Seperation:").define("islandSeperation", 9);
    private static final ModConfigSpec.ConfigValue<Double> ISLAND_UNDERWATER_DISTANCE_MULTIPLIER = BUILDER.comment("Island Underwater Falloff Distance Multiplier").define("islandUnderwaterFalloffDistanceMultiplier", 9.0);

    private static final ModConfigSpec.BooleanValue HARDCORE_MODE = BUILDER.comment("Only spawn 1 island?").define("hardcoreMode", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double islandSize;
    public static int islandSeparation;
    public static FloatRange continentalTargetRangeA = new FloatRange(-0.25f, 0.7f);
    public static FloatRange continentalTargetRangeB = new FloatRange(-1.0f, 1.4f);

    public static double islandUnderwaterFalloffDistanceMultiplier;
    public static boolean hardcoreMode;

    public static OctaveNoiseRecipe2D domainWarpNoise = new OctaveNoiseRecipe2D(1, 28, 22, 1.732, 1.732);
    public static OctaveNoiseRecipe2D rangeVariationNose = new OctaveNoiseRecipe2D(2, 344, 1.2, 1.732, 1.732);


    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        islandSize = ISLAND_SIZE.get();
        islandSeparation = ISLAND_SEPERATION.get();

        islandUnderwaterFalloffDistanceMultiplier = ISLAND_UNDERWATER_DISTANCE_MULTIPLIER.get();
        hardcoreMode = HARDCORE_MODE.get();

    }
}
