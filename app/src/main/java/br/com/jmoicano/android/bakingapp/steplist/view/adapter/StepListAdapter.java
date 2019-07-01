package br.com.jmoicano.android.bakingapp.steplist.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.jmoicano.android.bakingapp.data.model.Step;
import br.com.jmoicano.android.bakingapp.databinding.StepItemBinding;
import br.com.jmoicano.android.bakingapp.util.ListAdapterViewModel;

public abstract class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder>
        implements StepClickListener {

    private ListAdapterViewModel<Step> mViewModel;

    public StepListAdapter(ListAdapterViewModel<Step> mViewModel) {
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StepItemBinding binding = StepItemBinding.inflate(inflater);
        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Step item = mViewModel.getPosition(position);
        holder.bind(item);
        holder.mStepItemBinding.setOnClick(this);
    }

    @Override
    public int getItemCount() {
        return mViewModel.numItens();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder{
        private StepItemBinding mStepItemBinding;

        public StepViewHolder(StepItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mStepItemBinding = itemBinding;
        }

        void bind(Step item) { mStepItemBinding.setItem(item); }
    }
}
