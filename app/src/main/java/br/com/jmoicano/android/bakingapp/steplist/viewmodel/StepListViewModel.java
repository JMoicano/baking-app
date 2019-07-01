package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Step;
import br.com.jmoicano.android.bakingapp.util.ListAdapterViewModel;

public class StepListViewModel extends ViewModel implements ListAdapterViewModel<Step> {
    private List<Step> steps;

    public StepListViewModel(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int numItens() {
        return steps.size();
    }

    @Override
    public Step getPosition(int i) {
        return steps.get(i);
    }
}
