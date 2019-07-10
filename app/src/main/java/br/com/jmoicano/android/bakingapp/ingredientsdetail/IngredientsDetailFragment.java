package br.com.jmoicano.android.bakingapp.ingredientsdetail;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Ingredient;
import br.com.jmoicano.android.bakingapp.databinding.IngredientContentBinding;
import br.com.jmoicano.android.bakingapp.ingredientsdetail.IngredientsDetailActivity;
import br.com.jmoicano.android.bakingapp.steplist.view.ui.StepListActivity;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link IngredientsDetailActivity}
 * on handsets.
 */
public class IngredientsDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";

    /**
     * The dummy content this fragment is presenting.
     */
    private List<Ingredient> mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            mItem = getArguments().getParcelableArrayList(ARG_ITEM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(requireActivity().getString(R.string.ingredients));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        IngredientContentBinding binding = DataBindingUtil.inflate(inflater, R.layout.ingredient_content, container, false);

        if (mItem != null) {
            for (Ingredient i: mItem) {
                binding.tvIngredientsDescription.append(
                        i.getQuantity() + " " +
                        i.getMeasure() + " " +
                        i.getIngredientName() + "\n"
                );
            }
        }

        return binding.getRoot();
    }
}
