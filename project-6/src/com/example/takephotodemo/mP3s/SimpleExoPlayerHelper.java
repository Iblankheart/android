package com.dlc.mp3demo.mP3s;

import android.content.Context;
import android.net.Uri;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SimpleExoPlayerHelper {

    private final Context mContext;
    private SimpleExoPlayer mSimpleExoPlayer;
    private ConcatenatingMediaSource mConcatenatingMediaSource;

    public SimpleExoPlayerHelper(Context context, PlayerView playerView) {
        mContext = context;

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(mSimpleExoPlayer);

        mConcatenatingMediaSource = new ConcatenatingMediaSource();

    }

    public static SimpleExoPlayerHelper createMySimpleExoPlayer(Context context, PlayerView playerView) {
        return new SimpleExoPlayerHelper(context, playerView);

    }

    public SimpleExoPlayer getSimpleExoPlayer() {
        return mSimpleExoPlayer;
    }

    public SimpleExoPlayerHelper prepare(List<String> urls) {
        mConcatenatingMediaSource = new ConcatenatingMediaSource();
        for (String url : urls) {
            MediaSource mediaSource = buildMediaSource(url);
            mConcatenatingMediaSource.addMediaSource(mediaSource);
        }
        mSimpleExoPlayer.prepare(mConcatenatingMediaSource);

        return this;
    }

    public SimpleExoPlayerHelper prepare(String url) {
        List<String> strings = new ArrayList<>();
        strings.add(url);
        prepare(strings);
        return this;
    }

    public enum RepeatMode {

        REPEAT_MODE_ALL(Player.REPEAT_MODE_ALL),

        REPEAT_MODE_ONE(Player.REPEAT_MODE_ONE),

        REPEAT_MODE_OFF(Player.REPEAT_MODE_OFF);

        private int mMode;

        RepeatMode(int repeatMode) {
            mMode = repeatMode;
        }

        public int getMode() {
            return mMode;
        }

        public void setMode(int mode) {
            mMode = mode;
        }
    }



    public SimpleExoPlayerHelper setRepeatMode(RepeatMode repeatMode) {
        mSimpleExoPlayer.setRepeatMode(repeatMode.getMode());
        return this;
    }

    public SimpleExoPlayerHelper setPlayWhenReady(boolean playWhenReady) {
        mSimpleExoPlayer.setPlayWhenReady(playWhenReady);
        return this;
    }

    public void stop() {
        mSimpleExoPlayer.stop();
    }

    public void pause() {
        mSimpleExoPlayer.setPlayWhenReady(false);
    }

    public void start() {
        mSimpleExoPlayer.setPlayWhenReady(true);
    }

    public void release() {
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
    }

    private MediaSource buildMediaSource(String url) {
        String proxyUrl = getProxyUrl(url);
        return newVideoSource(proxyUrl);
    }


    private String getProxyUrl(String originalUrl) {
        HttpProxyCacheServer proxy = VideoCacheProxy.getProxy(mContext);
        return proxy.getProxyUrl(originalUrl);
    }


    private MediaSource newVideoSource(String url) {

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "useExoplayer"), bandwidthMeter);

        ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
        ExtractorMediaSource mediaSource = factory.createMediaSource(Uri.parse(url));

        return mediaSource;
    }
}
