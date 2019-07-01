package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Ingredient;
import br.com.jmoicano.android.bakingapp.data.model.Step;

public class StepListViewModelFactory implements ViewModelProvider.Factory {
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public StepListViewModelFactory(List<Step> steps, List<Ingredient> ingredients) {
        this.steps = steps;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StepListViewModel(steps, ingredients);
    }
}
