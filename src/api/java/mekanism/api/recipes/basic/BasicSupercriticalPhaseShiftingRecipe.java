package mekanism.api.recipes.basic;

import java.util.List;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.ChemicalStackTemplate;
import mekanism.api.recipes.MekanismRecipeSerializers;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.book.SupercriticalPhaseShiftingBookCategory;
import mekanism.api.recipes.book.SupercriticalPhaseShiftingBookCategory.SupercriticalPhaseShiftingBookInfo;
import mekanism.api.recipes.display.recipe.SupercriticalPhaseShiftingRecipeDisplay;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BasicSupercriticalPhaseShiftingRecipe extends
        BasicChemicalToChemicalRecipe<SupercriticalPhaseShiftingBookCategory, SupercriticalPhaseShiftingBookInfo> {

    private static final Holder<Item> SUPERCHARGED_COIL = DeferredHolder.create(Registries.ITEM,
            Identifier.fromNamespaceAndPath(MekanismAPI.MEKANISM_MODID, "supercharged_coil"));

    public BasicSupercriticalPhaseShiftingRecipe(
            CommonInfo commonInfo, SupercriticalPhaseShiftingBookInfo bookInfo, ChemicalStackIngredient input,
            float experience, ChemicalStackTemplate output) {
        super(MekanismRecipeTypes.TYPE_SUPERCRITICAL_PHASE_SHIFTING.value(), commonInfo, bookInfo, input, experience,
                output);
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleChemicalRecipeInput>> getSerializer() {
        return MekanismRecipeSerializers.SUPERCRITICAL_PHASE_SHIFTING.value();
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(new SupercriticalPhaseShiftingRecipeDisplay(
                getInput().display(),
                getOutputDisplay(),
                new SlotDisplay.ItemSlotDisplay(SUPERCHARGED_COIL),
                experience()));
    }

}