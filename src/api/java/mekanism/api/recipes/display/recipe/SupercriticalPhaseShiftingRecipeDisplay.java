package mekanism.api.recipes.display.recipe;

import java.util.Objects;

import com.google.common.base.Preconditions;

import mekanism.api.recipes.MekanismRecipeTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.registries.DeferredHolder;

public record SupercriticalPhaseShiftingRecipeDisplay(SlotDisplay ingredient, SlotDisplay result,
        SlotDisplay craftingStation, float experience) implements OneInputRecipeDisplay {

    private static final DeferredHolder<Type<?>, Type<SupercriticalPhaseShiftingRecipeDisplay>> TYPE = DeferredHolder
            .create(Registries.RECIPE_DISPLAY, MekanismRecipeTypes.NAME_SUPERCRITICAL_PHASE_SHIFTING);

    public SupercriticalPhaseShiftingRecipeDisplay {
        Objects.requireNonNull(ingredient);
        Objects.requireNonNull(result);
        Objects.requireNonNull(craftingStation);
        Preconditions.checkArgument(experience >= 0, "Experience produced must not be negative");
    }

    @Override
    public Type<? extends RecipeDisplay> type() {
        return TYPE.value();
    }
}
