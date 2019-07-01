package br.com.jmoicano.android.bakingapp.stepdetail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;

import br.com.jmoicano.android.bakingapp.R;
import br.com.jmoicano.android.bakingapp.data.model.Step;
import br.com.jmoicano.android.bakingapp.databinding.StepContentBinding;
import br.com.jmoicano.android.bakingapp.steplist.view.ui.StepListActivity;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";

    private Step mItem;

    private StepContentBinding mBinding;
    private SimpleExoPlayer exoPlayer;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM)) {
            mItem = getArguments().getParcelable(ARG_ITEM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getShortDescription());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.step_content, container, false);

        if (mItem != null) {
            mBinding.setStep(mItem);

            if(mItem.getVideoURL().isEmpty() && mItem.getThumbnailURL().isEmpty()){
                mBinding.pvVideo.setVisibility(View.GONE);
            } else if(!mItem.getVideoURL().isEmpty()){
                mBinding.pvVideo.setVisibility(View.VISIBLE);
                setupExoPlayer(Uri.parse(mItem.getVideoURL()));
            } else {
                mBinding.pvVideo.setVisibility(View.VISIBLE);
                setupExoPlayer(Uri.parse(mItem.getThumbnailURL()));
            }
        }

        return mBinding.getRoot();
    }

    private void setupExoPlayer(Uri videoUri) {
        PlayerView pvVideo = mBinding.pvVideo;
        if(exoPlayer == null){
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    requireContext(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl()
            );
            pvVideo.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(
                    requireContext(),
                    getResources().getString(R.string.app_name)
            );
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(requireContext(), userAgent);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory,
                    extractorsFactory,
                    new Handler(),
                    new ExtractorMediaSource.EventListener() {
                        @Override
                        public void onLoadError(IOException error) {
                            Toast.makeText(
                                    requireContext(),
                                    StepDetailFragment.this.getString(R.string.video_loading_error),
                                    Toast.LENGTH_SHORT);
                        }
                    });
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        if(exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
        super.onDestroy();
    }
}