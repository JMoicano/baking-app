package br.com.jmoicano.android.bakingapp.steplist.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Ingredient;
import br.com.jmoicano.android.bakingapp.data.model.Step;
import br.com.jmoicano.android.bakingapp.ingredientsdetail.IngredientsDetailActivity;
import br.com.jmoicano.android.bakingapp.ingredientsdetail.IngredientsDetailFragment;
import br.com.jmoicano.android.bakingapp.stepdetail.StepDetailActivity;
import br.com.jmoicano.android.bakingapp.stepdetail.StepDetailFragment;
import br.com.jmoicano.android.bakingapp.steplist.view.adapter.StepListAdapter;
import br.com.jmoicano.android.bakingapp.steplist.viewmodel.StepListViewModel;
import br.com.jmoicano.android.bakingapp.steplist.viewmodel.StepListViewModelFactory;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {


    public static Intent newInstance(Context context, List<Step> steps, List<Ingredient> ingredients){
        Intent intent = new Intent(context, StepListActivity.class);
        intent.putParcelableArrayListExtra(ARG_STEP_LIST, (ArrayList<? extends Parcelable>) steps);
        intent.putParcelableArrayListExtra(ARG_INGR_LIST, (ArrayList<? extends Parcelable>) ingredients);
        return intent;
    }

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private StepListViewModel mViewModel;

    public static final String ARG_STEP_LIST = "steps";
    public static final String ARG_INGR_LIST = "ingredients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ArrayList<Step> steps = getIntent().getParcelableArrayListExtra(ARG_STEP_LIST);
        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra(ARG_INGR_LIST);
        mViewModel = ViewModelProviders.of(
                this,
                new StepListViewModelFactory(steps, ingredients))
                .get(StepListViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        TextView textViewIngredients = findViewById(R.id.tv_ingredients);
        textViewIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepListActivity stepListActivity = StepListActivity.this;
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList(IngredientsDetailFragment.ARG_ITEM,
                            (ArrayList<? extends Parcelable>) mViewModel.getIngredients());
                    IngredientsDetailFragment fragment = new IngredientsDetailFragment();
                    fragment.setArguments(arguments);
                    stepListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(stepListActivity, IngredientsDetailActivity.class);
                    intent.putExtra(IngredientsDetailFragment.ARG_ITEM,
                            (ArrayList<? extends Parcelable>) mViewModel.getIngredients());

                    stepListActivity.startActivity(intent);
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StepListAdapter(mViewModel) {
            @Override
            public void onStepClicked(Step step) {
                StepListActivity stepListActivity = StepListActivity.this;
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(StepDetailFragment.ARG_ITEM, step);
                    StepDetailFragment fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    stepListActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Intent intent = new Intent(stepListActivity, StepDetailActivity.class);
                    intent.putExtra(StepDetailFragment.ARG_ITEM, step);

                    stepListActivity.startActivity(intent);
                }

            }
        });
    }
}
