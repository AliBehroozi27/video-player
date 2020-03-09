package com.example.videoplayer.custom_recycler_view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.model.Video;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class VideoPlayerRecyclerView extends RecyclerView {

    private static final String TAG = "VideoPlayerRecyclerView";
    private int secondItemStartPosition;
    private boolean justPlayed;

    private enum VolumeState {ON, OFF}

    // ui
    private ImageView thumbnail, volumeControl;
    private ProgressBar progressBar;
    private View viewHolderParent;
    private FrameLayout frameLayout;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;

    // vars
    private ArrayList<Video> videos = new ArrayList<>();
    private int videoSurfaceDefaultHeight = 0;
    private int screenDefaultHeight = 0;
    private Context context;
    private int playPosition = -1;
    private boolean isVideoViewAdded;

    // controlling playback state
    private VolumeState volumeState;

    public VideoPlayerRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoPlayerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context.getApplicationContext();
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        videoSurfaceDefaultHeight = point.x;
        screenDefaultHeight = point.y;

        videoSurfaceView = new PlayerView(context);
        videoSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        // Bind the player to the view.
        videoSurfaceView.setUseController(false);
        videoSurfaceView.setPlayer(videoPlayer);
        setVolumeControl(VolumeState.ON);

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.e(TAG, "-----------------------------------------" );
                    if (thumbnail != null) { // show the old thumbnail
                        thumbnail.setVisibility(VISIBLE);
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo(true);
                    } else {
                        playVideo(false);
                    }
                }

//                switch (newState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        Log.e(TAG, "-----------------------------------------");
//                        if (thumbnail != null) { // show the old thumbnail
//                            thumbnail.setVisibility(VISIBLE);
//                        }
//
//                        // There's a special case when the end of the list has been reached.
//                        // Need to handle that with this bit of logic
//                        if (!recyclerView.canScrollVertically(1)) {
//                            playVideo(true);
//                        } else {
//                            playVideo(false);
//                        }
//                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        Log.e(TAG, "onScrollStateChanged: dragging ");
//                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        Log.e(TAG, "onScrollStateChanged: settling");
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e(TAG, "onScrolled: " + dx + "  " + dy);
                super.onScrolled(recyclerView, dx, dy);
//                if (!justPlayed) {
//                    justPlayed = true;
//                    postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            justPlayed = false;
//
//                        }
//                    }, 1000);
//
//                }
                if (dy < 10 && dy > 0) {
                    Log.e(TAG, "-----------------------------------------" + dy);
                    if (thumbnail != null) { // show the old thumbnail
                        thumbnail.setVisibility(VISIBLE);
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo(true);
                    } else {
                        playVideo(false);
                    }
                }
            }
        });

        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (viewHolderParent != null && viewHolderParent.equals(view)) {
                    // resetVideoView();
                }

            }
        });

        videoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.");
                        if (progressBar != null) {
                            progressBar.setVisibility(VISIBLE);
                        }
                        if (!isVideoViewAdded) {
                            addVideoView();
                        }

                        break;
                    case Player.STATE_ENDED:
                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
                        videoPlayer.seekTo(0);
                        break;
                    case Player.STATE_IDLE:

                        break;
                    case Player.STATE_READY:
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.");
                        if (progressBar != null) {
                            progressBar.setVisibility(GONE);
                        }
                        if (isVideoViewAdded) {
                            visibleVideoView();
                        }
                        break;
                    default:
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

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
    }

    public void playVideo(boolean isEndOfList) {

        Log.e(TAG, "playVideo: ");

        int targetPosition;

        if (!isEndOfList) {
            int startPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
            int endPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                int endPositionVideoHeight = getVisibleVideoSurfaceHeight(true, endPosition);
                int startPositionVideoHeight = getVisibleVideoSurfaceHeight(false, startPosition);

                targetPosition = startPositionVideoHeight > endPositionVideoHeight ? startPosition : endPosition;
            } else {
                targetPosition = startPosition;
            }
        } else {
            targetPosition = videos.size() - 1;
        }

        Log.e(TAG, "playVideo: target position: " + targetPosition);

        // video is already playing so return
        if (targetPosition == playPosition) {
            return;
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition;
        if (videoSurfaceView == null) {
            return;
        }

        // remove any old surface views from previously playing videos

        videoSurfaceView.animate().alpha(0).setDuration(50).start();
        if (thumbnail != null) {
            Log.e(TAG, "playVideo: thumb bring");
            thumbnail.bringToFront();
        }
        ///videoSurfaceView.setVisibility(INVISIBLE);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                removeVideoView(videoSurfaceView);

                int currentPosition = targetPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();

                View child = getChildAt(currentPosition);
                if (child == null) {
                    return;
                }

                VideoPlayerViewHolder holder = (VideoPlayerViewHolder) child.getTag();
                if (holder == null) {
                    playPosition = -1;
                    return;
                }
                thumbnail = holder.thumbnail;
                progressBar = holder.progressBar;
                volumeControl = holder.volumeControl;
                viewHolderParent = holder.itemView;
                frameLayout = holder.itemView.findViewById(R.id.media_container);

                videoSurfaceView.setPlayer(videoPlayer);

                viewHolderParent.setOnClickListener(videoViewClickListener);

                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                        context, Util.getUserAgent(context, "RecyclerView VideoPlayer"));
                String videoUrl = videos.get(targetPosition).getVideoUrl();
                if (videoUrl != null) {
                    MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(videoUrl));
                    videoPlayer.prepare(videoSource);
                    videoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
                    videoPlayer.setPlayWhenReady(true);
                }
            }
        }, 50);

    }


    private OnClickListener videoViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            toggleVolume();
        }
    };

    /**
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     *
     * @param playPosition
     * @return
     */
    private int getVisibleVideoSurfaceHeight(boolean isSecond, int playPosition) {
        int at = playPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: " + at);

        View child = getChildAt(at);
        if (child == null) {
            return 0;
        }

        int[] location = new int[2];
        child.getLocationInWindow(location);

        if (isSecond)
            secondItemStartPosition = location[1];

        if (location[1] < 0) {
            return location[1] + secondItemStartPosition;
        } else {
            return screenDefaultHeight - location[1];
        }
    }


    // Remove the old player
    private void removeVideoView(PlayerView videoView) {
        videoSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        Log.e(TAG, "removeVideoView: removing " + videoView.getAlpha());
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent == null) {
            return;
        }

        int index = parent.indexOfChild(videoView);
        if (index >= 0) {
            parent.removeViewAt(index);
            isVideoViewAdded = false;
            viewHolderParent.setOnClickListener(null);
        }
    }

    private void addVideoView() {
        Log.e(TAG, "addVideoView: ");
        frameLayout.addView(videoSurfaceView, 0);
        Log.e(TAG, "addVideoView: thumb " + frameLayout.indexOfChild(thumbnail));
        Log.e(TAG, "addVideoView: surface " + frameLayout.indexOfChild(videoSurfaceView));
        Log.e(TAG, "addVideoView: prog" + frameLayout.indexOfChild(progressBar));

        //videoSurfaceView.setVisibility(GONE);
        thumbnail.bringToFront();
        Log.e(TAG, "addVideoView: thumb " + frameLayout.indexOfChild(thumbnail));
        Log.e(TAG, "addVideoView: surface " + frameLayout.indexOfChild(videoSurfaceView));
        Log.e(TAG, "addVideoView: prog" + frameLayout.indexOfChild(progressBar));

        isVideoViewAdded = true;
    }

    private void visibleVideoView() {
        Log.e(TAG, "visibleVideoView: ");
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        videoSurfaceView.requestFocus();
        videoSurfaceView.animate().alpha(1).setDuration(100).start();
        videoSurfaceView.bringToFront();
        Log.e(TAG, "addVideoView: thumb " + frameLayout.indexOfChild(thumbnail));
        Log.e(TAG, "addVideoView: surface " + frameLayout.indexOfChild(videoSurfaceView));
        Log.e(TAG, "addVideoView: prog" + frameLayout.indexOfChild(progressBar));
        //thumbnail.setVisibility(GONE);
    }

    private void resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(videoSurfaceView);
            playPosition = -1;
            videoSurfaceView.setVisibility(INVISIBLE);
            thumbnail.setVisibility(VISIBLE);
        }
    }

    public void releasePlayer() {

        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }

        viewHolderParent = null;
    }

    public void stop() {
        videoPlayer.setPlayWhenReady(false);
    }

    public void resume() {
        videoPlayer.setPlayWhenReady(true);
    }

    public boolean isPlayerNull() {
        return videoPlayer == null;
    }


    private void toggleVolume() {
        if (videoPlayer != null) {
            if (volumeState == VolumeState.OFF) {
                Log.d(TAG, "togglePlaybackState: enabling volume.");
                setVolumeControl(VolumeState.ON);

            } else if (volumeState == VolumeState.ON) {
                Log.d(TAG, "togglePlaybackState: disabling volume.");
                setVolumeControl(VolumeState.OFF);

            }
        }
    }

    private void setVolumeControl(VolumeState state) {
        volumeState = state;
        if (state == VolumeState.OFF) {
            videoPlayer.setVolume(0f);
            animateVolumeControl();
        } else if (state == VolumeState.ON) {
            videoPlayer.setVolume(1f);
            animateVolumeControl();
        }
    }

    private void animateVolumeControl() {
        if (volumeControl != null) {
            volumeControl.bringToFront();
            if (volumeState == VolumeState.OFF) {
                volumeControl.setImageResource(R.drawable.ic_volume_off_grey_24dp);
            } else if (volumeState == VolumeState.ON) {
                volumeControl.setImageResource(R.drawable.ic_volume_on_grey_24dp);
            }
            volumeControl.animate().cancel();

            volumeControl.setAlpha(1f);

            volumeControl.animate()
                    .alpha(0f)
                    .setDuration(600).setStartDelay(1000);
        }
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
