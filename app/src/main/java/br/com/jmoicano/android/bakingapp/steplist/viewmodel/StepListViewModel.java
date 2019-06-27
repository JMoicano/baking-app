package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.jmoicano.android.bakingapp.data.model.Step;

public class StepListViewModel extends ViewModel implements StepListAdapterViewModel {
    private List<Step> steps;

    public StepListViewModel(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int numSteps() {
        return steps.size();
    }

    @Override
    public Step getPosition(int i) {
        return steps.get(i);
    }
}
