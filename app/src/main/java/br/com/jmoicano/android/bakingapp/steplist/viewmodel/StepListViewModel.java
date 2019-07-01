package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Ingredient;
import br.com.jmoicano.android.bakingapp.data.model.Step;
import br.com.jmoicano.android.bakingapp.util.ListAdapterViewModel;

public class StepListViewModel extends ViewModel implements ListAdapterViewModel<Step> {
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public StepListViewModel(List<Step> steps, List<Ingredient> ingredients) {
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public int numItens() {
        return steps.size();
    }

    @Override
    public Step getPosition(int i) {
        return steps.get(i);
    }
}
