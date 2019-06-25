package br.com.jmoicano.android.bakingapp.service.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Recipe;

public interface RecipeDataSource {
    LiveData<Resource<List<Recipe>>> getRecipes();
}
