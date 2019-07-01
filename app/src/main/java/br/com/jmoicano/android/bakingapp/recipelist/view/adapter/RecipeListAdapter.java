package br.com.jmoicano.android.bakingapp.recipelist.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Recipe;
import br.com.jmoicano.android.bakingapp.databinding.RecipeItemBinding;
import br.com.jmoicano.android.bakingapp.util.ListAdapterViewModel;

public abstract class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>
        implements RecipeClickListener {

    ListAdapterViewModel<Recipe> mViewModel;

    public RecipeListAdapter(ListAdapterViewModel<Recipe> mViewModel) {
        this.mViewModel = mViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe item = mViewModel.getPosition(position);
        holder.bind(item);
        holder.mRecipeBinding.setOnClick(this);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeItemBinding binding = RecipeItemBinding.inflate(inflater);
        return new RecipeViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return mViewModel.numItens();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private RecipeItemBinding mRecipeBinding;

        public RecipeViewHolder(RecipeItemBinding recipeBinding) {
            super(recipeBinding.getRoot());
            mRecipeBinding = recipeBinding;
        }

        void bind(Recipe item) {mRecipeBinding.setItem(item);}
    }
}
