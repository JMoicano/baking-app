package br.com.jmoicano.android.bakingapp.recipelist.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import br.com.jmoicano.android.bakingapp.databinding.ActivityRecipeListBinding;
import br.com.jmoicano.android.bakingapp.recipelist.view.adapter.RecipeListAdapter;
import br.com.jmoicano.android.bakingapp.recipelist.viewmodel.RecipeListViewModel;
import br.com.jmoicano.android.bakingapp.recipelist.viewmodel.RecipeListViewModelFactory;
import br.com.jmoicano.android.bakingapp.service.source.RecipeRepository;
import br.com.jmoicano.android.bakingapp.service.source.remote.RecipeRemoteDataSource;
import br.com.jmoicano.android.bakingapp.service.source.remote.Resource;
import br.com.jmoicano.android.bakingapp.steplist.view.ui.StepListActivity;
import br.com.jmoicano.android.bakingapp.util.ViewUtils;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListViewModel viewModel;

    private ActivityRecipeListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(
                this,
                new RecipeListViewModelFactory(
                        RecipeRepository.getInstance(
                                RecipeRemoteDataSource.getInstance()
                        )
                )
        ).get(RecipeListViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        final RecipeListAdapter adapter = new RecipeListAdapter(viewModel) {
            @Override
            public void onRecipeClick(Recipe recipe) {
                Intent intent = StepListActivity.newInstance(
                        RecipeListActivity.this,
                        recipe.getSteps()
                );

                RecipeListActivity.this.startActivity(intent);
            }
        };
        int spanCount = ViewUtils.calculateNoOfColumns(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount, RecyclerView.VERTICAL, false);
        binding.content.rvRecipes.setLayoutManager(layoutManager);
        binding.content.rvRecipes.setAdapter(adapter);
        viewModel.getRecipesResource().observe(this, new Observer<Resource<List<Recipe>>>() {
            @Override
            public void onChanged(Resource<List<Recipe>> listResource) {
                if (listResource.status == Resource.Status.SUCCESS){
                    viewModel.setRecipes(listResource.data);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        setSupportActionBar(binding.toolbar);
    }

}
