package br.com.jmoicano.android.bakingapp.steplist.viewmodel;

import br.com.jmoicano.android.bakingapp.data.model.Step;

public interface StepListAdapterViewModel {
    int numSteps();
    Step getPosition(int i);
}
