package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Step;

public class StepListViewModelFactory implements ViewModelProvider.Factory {
    private List<Step> steps;

    public StepListViewModelFactory(List<Step> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StepListViewModel(steps);
    }
}
