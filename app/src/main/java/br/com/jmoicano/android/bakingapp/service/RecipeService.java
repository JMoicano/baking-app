package br.com.jmoicano.android.bakingapp.service;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
