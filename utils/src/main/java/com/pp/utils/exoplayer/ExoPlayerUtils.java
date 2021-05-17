package com.pp.utils.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pp.utils.R;

public class ExoPlayerUtils {
    SimpleExoPlayer exoPlayer = null;
    private final Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            Log.d("hello", "onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.d("hello", "onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.d("hello", "onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.d("hello", "onPlayerStateChanged: playWhenReady = " + String.valueOf(playWhenReady)
                    + " playbackState = " + playbackState);
            switch (playbackState) {
                case Player.STATE_ENDED:
                    Log.d("hello", "Playback ended!");
                    //Stop playback and return to start position
                    break;
                case Player.STATE_READY:
                    break;
                case Player.STATE_BUFFERING:
                    break;
                case Player.STATE_IDLE:
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.d("hello", "onPlaybackError: " + error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            Log.d("hello", "onPositionDiscontinuity");
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.d("hello", "MainActivity.onPlaybackParametersChanged." + playbackParameters.toString());
        }

        @Override
        public void onSeekProcessed() {

        }
    };

    public ExoPlayerUtils(Context context) {
        if (exoPlayer == null) {
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            TrackSelector trackSelector = new DefaultTrackSelector(factory);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            exoPlayer.addListener(eventListener);
        }
    }

    public void loadPlayer(Context context, String url, ViewGroup layer) {
        if (exoPlayer == null) {
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            TrackSelector trackSelector = new DefaultTrackSelector(factory);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            exoPlayer.addListener(eventListener);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.layout_player, null);
        PlayerView playerView = view.findViewById(R.id.playerView);
        layer.addView(view);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory factory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getPackageName()), bandwidthMeter);
        Uri uri = Uri.parse(url);
        MediaSource source = new ExtractorMediaSource.Factory(factory).createMediaSource(uri);
        exoPlayer.prepare(source);

        playerView.setPlayer(exoPlayer);

    }

}
