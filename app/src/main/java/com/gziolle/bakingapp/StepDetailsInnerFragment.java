/**
 * BakingApp
 * Created by Guilherme Ziolle on 27/06/2017.
 * gziolle@gmail.com
 * Copyright (c) 2017. All rights reserved
 */
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


/*
* Hosts the "Step Details" content:
*  - Video Player (if available)
*  - Image (if available)
*  - Step Description
* */
public class StepDetailsInnerFragment extends Fragment {

    private Step mStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private TextView mDescriptionTextView;
    private TextView noVideoTextView;

    /*
    * Default Constructor.
    * */
    public StepDetailsInnerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details_inner, container, false);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        noVideoTextView = (TextView) rootView.findViewById(R.id.tvNoVideo);

        return rootView;
    }

    /*
    * Adds the Step's description to the corresponding TextView
    * */
    private void addDescriptionToView(String description) {
        if (description == null) return;
        mDescriptionTextView.setText(description);
    }

    @Override
    public void onStart() {
        super.onStart();
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
            if (getActivity().findViewById(R.id.stepDescriptionCard) != null) {
                mDescriptionTextView = (TextView) getActivity().findViewById(R.id.tvStepDescription);
                addDescriptionToView(mStep.getDescription());
            }
        }
    }

    /*
    * Initializes the resources used by the video player.
    * */
    private void initializeVideoPlayer(String videoUrl) {
        if (videoUrl == null) return;

        if (mExoPlayer == null) {
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

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    /*
    * Releases the resources used by the ExoPlayer instance
    * */
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

    /*
    * Adds a Step to the fragment.
    * */
    public void addStep(Step step) {
        mStep = step;
    }
}
