package br.com.jmoicano.android.bakingapp.recipelist.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.jmoicano.android.bakingapp.service.source.RecipeRepository;

public class RecipeListViewModelFactory implements ViewModelProvider.Factory {

    private RecipeRepository repository;

    public RecipeListViewModelFactory(RecipeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeListViewModel(repository);
    }
}
