package br.com.jmoicano.android.bakingapp.recipelist.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import br.com.jmoicano.android.bakingapp.service.source.RecipeRepository;
import br.com.jmoicano.android.bakingapp.service.source.remote.RecipeRemoteDataSource;
import br.com.jmoicano.android.bakingapp.service.source.remote.Resource;
import br.com.jmoicano.android.bakingapp.util.ListAdapterViewModel;

public class RecipeListViewModel extends ViewModel
        implements ListAdapterViewModel<Recipe> {

    private List<Recipe> recipes;
    private LiveData<Boolean> loading;
    private LiveData<Resource<List<Recipe>>> recipesResource;

    public RecipeListViewModel(RecipeRepository repository) {
        recipesResource = repository.getRecipes();
        this.recipes = new ArrayList<>();
        loading = Transformations.map(
                recipesResource,
                new Function<Resource<List<Recipe>>, Boolean>() {
                    @Override
                    public Boolean apply(Resource<List<Recipe>> input) {
                        return input.status == Resource.Status.LOADING;
                    }
                }
        );
    }

    public LiveData<Resource<List<Recipe>>> getRecipesResource() {
        return recipesResource;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public int numItens() {
        return recipes.size();
    }

    @Override
    public Recipe getPosition(int i) {
        return recipes.get(i);
    }
}
