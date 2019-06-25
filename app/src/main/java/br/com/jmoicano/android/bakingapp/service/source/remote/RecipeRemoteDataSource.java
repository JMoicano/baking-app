package br.com.jmoicano.android.bakingapp.service.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.ServiceGenerator;
import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import br.com.jmoicano.android.bakingapp.service.RecipeService;
import br.com.jmoicano.android.bakingapp.service.source.RecipeDataSource;
import br.com.jmoicano.android.bakingapp.service.source.Resource;
import retrofit2.Call;

public class RecipeRemoteDataSource implements RecipeDataSource {

    private volatile static RecipeRemoteDataSource INSTANCE = null;
    private final RecipeService recipeService;
    private static final Object LOCK = new Object();


    private RecipeRemoteDataSource() {
        recipeService = ServiceGenerator.create(RecipeService.class);
    }

    public static RecipeRemoteDataSource getInstance(){
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipeRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<Recipe>>> getRecipes() {
        Call<List<Recipe>> call = recipeService.getRecipes();
        final MutableLiveData<Resource<List<Recipe>>> recipes = new MutableLiveData<>();
        recipes.postValue(Resource.<List<Recipe>>loading());
        call.enqueue(new RetrofitCallback<>(recipes, "getRecipes"));
        return recipes;
    }
}
