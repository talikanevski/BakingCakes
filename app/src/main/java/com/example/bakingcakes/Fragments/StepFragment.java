package com.example.bakingcakes.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
//import android.support.v4.media.session.MediaSessionCompat;
//import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingcakes.Activities.DetailActivity;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.Models.Step;
import com.example.bakingcakes.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import java.util.List;
import java.util.Objects;

import static com.example.bakingcakes.Activities.StepsActivity.CURRENT_STEP;
import static com.example.bakingcakes.Activities.StepsActivity.CURRENT_STEP_NUMBER;

//import android.support.v4.media.session.MediaSessionCompat;


public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = StepFragment.class.getSimpleName();

    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private Cake cake;
    private List<Step> stepList;
    private Step step;
    private int stepNumber;
    private long exoPlayerPosition = 0;
    private Button back;
    private Button forward;
    private TextView description;
    private ImageView imageView;
    //    private MediaSessionCompat mMediaSession;
//    private PlaybackStateCompat.Builder mStateBuilder;
    private AppBarLayout actionBar;


    public StepFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
//        actionBar = rootView.findViewById(R.id.step_app_bar);

        exoPlayerView = rootView.findViewById(R.id.exoPlayerView);
        back = rootView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber--;
                exoPlayerPosition = 0;
                setUp(stepNumber);
            }
        });
        forward = rootView.findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepNumber++;
                exoPlayerPosition = 0;
                setUp(stepNumber);
            }
        });
        description = rootView.findViewById(R.id.description);
        imageView = rootView.findViewById(R.id.step_thumbnail);

        // Initialize the player.
        initializePlayer();

        // Initialize the Media Session.
//        initializeMediaSession();

        if (savedInstanceState == null) {
            Intent callingIntent = Objects.requireNonNull(getActivity()).getIntent();

            if (callingIntent.hasExtra(CURRENT_STEP)) {
                step = callingIntent.getParcelableExtra(CURRENT_STEP);
                stepNumber = callingIntent.getIntExtra(CURRENT_STEP_NUMBER, 0);
                cake = callingIntent.getParcelableExtra(DetailActivity.CURRENT_CAKE);
            }
        } else {
            step = savedInstanceState.getParcelable(CURRENT_STEP);
            stepNumber = savedInstanceState.getInt(CURRENT_STEP_NUMBER);
            cake = savedInstanceState.getParcelable(DetailActivity.CURRENT_CAKE);
            exoPlayerPosition = savedInstanceState.getLong(CURRENT_STEP_NUMBER);
        }

        stepList = cake.getSteps();

        setUp(stepNumber);

        return rootView;
    }

    private void setUp(int stepN) {
        exoPlayer.stop();

//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        stepNumber = stepN;
        step = stepList.get(stepNumber);
        if (stepNumber == 0) {
            back.setVisibility(View.INVISIBLE);
            description.setText(cake.getCakeName() + ": " + step.getStepShortDescription());

            assert actionBar != null;
//            actionBar.setTitle(cake.getCakeName() + " - " + step.getStepShortDescription());

        } else {
            back.setVisibility(View.VISIBLE);
            description.setText(cake.getCakeName() + ". Step " + step.getStepDescription());

            assert actionBar != null;

//            actionBar.setTitle(cake.getCakeName() + "Step " + stepNumber);
        }
        if (stepNumber == stepList.size() - 1) {
            forward.setVisibility(View.INVISIBLE);
        } else {
            forward.setVisibility(View.VISIBLE);
        }

        if (step.getStepVideoUrl().isEmpty()) {
            exoPlayerView.setVisibility(View.GONE);
            imageView.setImageBitmap(cake.getCakeImage());
            imageView.setVisibility(View.VISIBLE);
        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            Uri mediaUri = Uri.parse(step.getStepVideoUrl());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    Objects.requireNonNull(getContext()), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            //in case of rotation
            exoPlayer.seekTo(exoPlayerPosition);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_STEP, step);
        outState.putInt(CURRENT_STEP_NUMBER, stepNumber);
        if (exoPlayer != null) {
            outState.putLong(CURRENT_STEP_NUMBER, exoPlayer.getCurrentPosition());
        } else {
            outState.putLong(CURRENT_STEP_NUMBER, exoPlayerPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializePlayer() {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.addListener(this);
        }
    }

    // It creates the MediaSessionCompat object, sets the flags for external clients,TODO  Do I need it here? Because of this I'd changed gradle to a lower level - 27
    // sets the available actions I want to support, and start the session.
//    private void initializeMediaSession() {
//
//        // Create a MediaSessionCompat.
//        mMediaSession = new MediaSessionCompat(getContext(), TAG);
//
//        // Enable callbacks from MediaButtons and TransportControls.
//        mMediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//
//        // Do not let MediaButtons restart the player when the app is not visible.
//        mMediaSession.setMediaButtonReceiver(null);
//
//        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
//        mStateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PAUSE |
//                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//
//        mMediaSession.setPlaybackState(mStateBuilder.build());
//
//
//        // MySessionCallback has methods that handle callbacks from a media controller.
//        mMediaSession.setCallback(new MySessionCallback());
//
//        // Start the Media Session since the activity is active.
//        mMediaSession.setActive(true);
//    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        releasePlayer();
        // When the activity is destroyed, set the MediaSession to inactive.
//        mMediaSession.setActive(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    //this is an ExoPlayer.EventListener
    // and will be called every time the player state changes,
    // so this is where the Media Session is updated .
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            Log.d(TAG, "onPlayerStateChanged: PLAYING");
//            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
//                    exoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            Log.d(TAG, "onPlayerStateChanged: PAUSED");
//            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
//                    exoPlayer.getCurrentPosition(), 1f);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override //Providing Up navigation
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(Objects.requireNonNull(getActivity()));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//
//    /**
//     * Media Session Callbacks, where all external clients control the player.//TODO do I really need it?
//     */
//    private class MySessionCallback extends MediaSessionCompat.Callback {
//        @Override
//        public void onPlay() {
//            exoPlayer.setPlayWhenReady(true);
//        }
//
//        @Override
//        public void onPause() {
//            exoPlayer.setPlayWhenReady(false);
//        }
//
//        @Override
//        public void onSkipToPrevious() {
//            exoPlayer.seekTo(0);
//        }
//    }
}
