package br.com.jmoicano.android.bakingapp.stepdetail.viewmodel;

import androidx.lifecycle.ViewModel;

public class StepDetailViewModel extends ViewModel {

    private long videoPosition;

    public long getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(long videoPosition) {
        this.videoPosition = videoPosition;
    }
}
