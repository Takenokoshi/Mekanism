package mekanism.api.recipes.book;

import java.util.Optional;
import java.util.function.IntFunction;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import io.netty.buffer.ByteBuf;
import mekanism.api.chemical.ChemicalIds;
import mekanism.api.chemical.ChemicalResource;
import mekanism.api.recipes.MekanismRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public enum SupercriticalPhaseShiftingBookCategory implements MekanismBookCategory {
    MISC("misc", "supercharged_coil", "sps_casing");

    public static final IntFunction<SupercriticalPhaseShiftingBookCategory> BY_ID = ByIdMap
            .continuous(SupercriticalPhaseShiftingBookCategory::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<SupercriticalPhaseShiftingBookCategory> CODEC = StringRepresentable
            .fromEnum(SupercriticalPhaseShiftingBookCategory::values);
    public static final StreamCodec<ByteBuf, SupercriticalPhaseShiftingBookCategory> STREAM_CODEC = ByteBufCodecs
            .idMapper(BY_ID, SupercriticalPhaseShiftingBookCategory::ordinal);

    private final Holder<RecipeBookCategory> categoryHolder;
    private final Holder<Item> primaryIcon;
    @Nullable
    private final Holder<Item> secondaryIcon;
    private final String name;

    SupercriticalPhaseShiftingBookCategory(String name, String primaryMekIcon, @Nullable String secondaryMekIcon) {
        this(name, MekanismBookCategory.createMekanismKey(primaryMekIcon),
                MekanismBookCategory.createMekanismKey(secondaryMekIcon));
    }

    SupercriticalPhaseShiftingBookCategory(String name, ResourceKey<Item> primaryIcon,
            @Nullable ResourceKey<Item> secondaryIcon) {
        this.name = name;
        this.primaryIcon = MekanismBookCategory.createItem(primaryIcon);
        this.secondaryIcon = MekanismBookCategory.createItem(secondaryIcon);
        this.categoryHolder = MekanismBookCategory.createHolder(MekanismRecipeTypes.NAME_SUPERCRITICAL_PHASE_SHIFTING,
                this.name);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Override
    public RecipeBookCategory toRecipeBookCategory() {
        return categoryHolder.value();
    }

    @Override
    public Object getPrimaryIcon(Provider holderLookup) {
        if (this == MISC) {
            // Try to render misc as antimatter
            return holderLookup.get(ChemicalIds.ANTIMATTER).<Object>map(ChemicalResource::of)
                    .orElseGet(() -> new ItemStack(primaryIcon));
        }
        return new ItemStack(primaryIcon);
    }

    @Override
    public Optional<Object> getSecondaryIcon(Provider holderLookup) {
        return secondaryIcon == null ? Optional.empty() : Optional.of(new ItemStack(secondaryIcon));
    }

    public record SupercriticalPhaseShiftingBookInfo(SupercriticalPhaseShiftingBookCategory category, String group)
            implements Recipe.BookInfo<SupercriticalPhaseShiftingBookCategory> {

        public static final MapCodec<SupercriticalPhaseShiftingBookInfo> MAP_CODEC = Recipe.BookInfo.mapCodec(CODEC,
                MISC, SupercriticalPhaseShiftingBookInfo::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, SupercriticalPhaseShiftingBookInfo> STREAM_CODEC = Recipe.BookInfo
                .streamCodec(SupercriticalPhaseShiftingBookCategory.STREAM_CODEC,
                        SupercriticalPhaseShiftingBookInfo::new);
    }
}
