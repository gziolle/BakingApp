package com.gziolle.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.gziolle.bakingapp.model.Step;

/**
 * BakingApp
 * Created by Guilherme Ziolle on 27/06/2017.
 * gziolle@gmail.com
 */

public class StepDetailsInnerFragment extends Fragment {

    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private TextView mDescriptionTextView;
    private TextView noVideoTextView;

    public StepDetailsInnerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details_inner, container, false);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        noVideoTextView = (TextView) rootView.findViewById(R.id.tvNoVideo);


        if (mStep != null) {
            String videoUrl = mStep.getVideoUrl();
            if (videoUrl != null && !("").equals(videoUrl))
                initializeVideoPlayer(mStep.getVideoUrl());
            else {
                mExoPlayerView.setVisibility(View.GONE);
                if (noVideoTextView != null) {
                    noVideoTextView.setVisibility(View.VISIBLE);
                }
            }
            if (rootView.findViewById(R.id.stepDescriptionCard) != null) {
                mDescriptionTextView = (TextView) rootView.findViewById(R.id.tvStepDescription);
                addDescriptionToView(mStep.getDescription());
            }
        }
        return rootView;
    }

    private void addDescriptionToView(String description) {
        if (description == null) return;
        mDescriptionTextView.setText(description);
    }

    private void initializeVideoPlayer(String videoUrl) {
        if (videoUrl == null) return;

        if (mExoPlayer == null) {
            //Creates a ExoPlayerView instance
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(
                    Uri.parse(videoUrl),
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    extractorsFactory,
                    null,
                    null);
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    public void addStep(Step step) {
        mStep = step;
    }
}
