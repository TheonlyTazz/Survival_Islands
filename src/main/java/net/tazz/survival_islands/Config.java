package net.tazz.survival_islands;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.tazz.survival_islands.noise.OctaveNoiseRecipe2D;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Survival_islands.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<Double> ISLAND_SIZE = BUILDER.comment("Island Size:").define("islandSize", 128.0);
    private static final ModConfigSpec.ConfigValue<Integer> ISLAND_SEPERATION = BUILDER.comment("Island Seperation:").define("islandSeperation", 9);
    private static final ModConfigSpec.ConfigValue<Float> CONT_RANGE_A_MIN = BUILDER.comment("Cont Range A Min:").define("contRangeAMin", -0.25f);
    private static final ModConfigSpec.ConfigValue<Float> CONT_RANGE_A_MAX = BUILDER.comment("Cont Range A Max:").define("contRangeAMax", 0.7f);
    private static final ModConfigSpec.ConfigValue<Float> CONT_RANGE_B_MIN = BUILDER.comment("Cont Range B Min:").define("contRangeBMin", -1.0f);
    private static final ModConfigSpec.ConfigValue<Float> CONT_RANGE_B_MAX = BUILDER.comment("Cont Range B Max:").define("contRangeBMax", 1.4f);
    private static final ModConfigSpec.ConfigValue<Double> ISLAND_UNDERWATER_DISTANCE_MULTIPLIER = BUILDER.comment("Island Underwater Falloff Distance Multiplier").define("islandUnderwaterFalloffDistanceMultiplier", 9.0);

    private static final ModConfigSpec.BooleanValue HARDCORE_MODE = BUILDER.comment("Only spawn 1 island?").define("hardcoreMode", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double islandSize = 64.0;
    public static int islandSeparation = 1800;
    public static float continentalTargetRangeAMin = -0.25f;
    public static float continentalTargetRangeAMax = 0.7f;
    public static float continentalTargetRangeBMin = -1.0f;
    public static float continentalTargetRangeBMax = 1.4f;
    public static double islandUnderwaterFalloffDistanceMultiplier = 9.0;

    public static OctaveNoiseRecipe2D domainWarpNoise = new OctaveNoiseRecipe2D(1, 28, 22, 1.732, 1.732);
    public static OctaveNoiseRecipe2D rangeVariationNose = new OctaveNoiseRecipe2D(2, 344, 1.2, 1.732, 1.732);
    public static boolean hardcoreMode = false;


    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        islandSize = ISLAND_SIZE.get();
        islandSeparation = ISLAND_SEPERATION.get();
        continentalTargetRangeAMin = CONT_RANGE_A_MIN.get();
        continentalTargetRangeAMax = CONT_RANGE_A_MAX.get();
        continentalTargetRangeBMin = CONT_RANGE_B_MIN.get();
        continentalTargetRangeBMax = CONT_RANGE_B_MAX.get();
        islandUnderwaterFalloffDistanceMultiplier = ISLAND_UNDERWATER_DISTANCE_MULTIPLIER.get();

    }
}
