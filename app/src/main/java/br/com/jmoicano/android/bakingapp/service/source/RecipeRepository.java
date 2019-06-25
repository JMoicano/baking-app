package br.com.jmoicano.android.bakingapp.service.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import br.com.jmoicano.android.bakingapp.service.source.remote.Resource;

public class RecipeRepository implements RecipeDataSource{

    private volatile static RecipeRepository INSTANCE = null;
    private static final Object LOCK = new Object();
    private final RecipeDataSource recipeDataSource;

    private RecipeRepository(RecipeDataSource recipeDataSource) {
        this.recipeDataSource = recipeDataSource;
    }

    public static RecipeRepository getInstance(RecipeDataSource recipeDataSource) {
        if (INSTANCE == null){
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipeRepository(recipeDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<Resource<List<Recipe>>> getRecipes() {
        return recipeDataSource.getRecipes();
    }
}
