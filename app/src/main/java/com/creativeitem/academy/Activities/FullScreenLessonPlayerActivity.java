package com.creativeitem.academy.Activities;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.VideoConfig;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
public class FullScreenLessonPlayerActivity  extends YouTubeBaseActivity {
    private String  mVideoType;
    private String  mVideoUrl;
    YouTubePlayerView myouTubePlayerView;
    VideoView html5VideoPlayer;
    ImageView emptyVideoScreen;
    private ProgressBar progressBarForVideoPlayer;
    YouTubePlayer.OnInitializedListener  mOnInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_activity);
        init();
        getTheLessonDetails();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width) , (int) (height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        playLessonOnFullScreen(mVideoType, mVideoUrl);
    }

    private void init() {
        html5VideoPlayer = (VideoView) findViewById(R.id.html5Player);
        emptyVideoScreen = findViewById(R.id.emptyVideoScreen);
        initProgressBar();
    }

    // Initialize the progress bar
    private void initProgressBar() {
        progressBarForVideoPlayer = findViewById(R.id.loadingVideoPlayer);
        Sprite waveLoading = new Wave();
        progressBarForVideoPlayer.setIndeterminateDrawable(waveLoading);
    }

    private void getTheLessonDetails() {
        mVideoType = (String) getIntent().getSerializableExtra("videoType");
        mVideoUrl  = (String) getIntent().getSerializableExtra("videoUrl");
    }

    private void playLessonOnFullScreen(String videoType, String videoUrl) {
        if (videoType.equals("html5") && !videoUrl.equals("")){
            playHTML5Video(videoUrl);
        }else{
            Toast.makeText(this, "Proper Video URL is Missing", Toast.LENGTH_SHORT).show();
            html5VideoPlayer.setVisibility(View.GONE);
            emptyVideoScreen.setVisibility(View.VISIBLE);
        }
    }

    private void playHTML5Video(String videoUrl) {
        progressBarForVideoPlayer.setVisibility(View.VISIBLE);
        emptyVideoScreen.setVisibility(View.GONE);
        html5VideoPlayer.setVisibility(View.VISIBLE);
        html5VideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressBarForVideoPlayer.setVisibility(View.INVISIBLE);

            }
        });
        html5VideoPlayer.setVideoPath(videoUrl);
        MediaController mediaController = new MediaController(this);
        html5VideoPlayer.setMediaController(mediaController);
        mediaController.setAnchorView(html5VideoPlayer);
        html5VideoPlayer.start();

        // This keeps tracks of if the video is buffering or not.
        final MediaPlayer.OnInfoListener onInfoToPlayStateListener = new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START: {
                        progressBarForVideoPlayer.setVisibility(View.GONE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START: {
                        progressBarForVideoPlayer.setVisibility(View.VISIBLE);
                        return true;
                    }
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END: {
                        progressBarForVideoPlayer.setVisibility(View.GONE);
                        return true;
                    }
                }
                return false;
            }

        };
        html5VideoPlayer.setOnInfoListener(onInfoToPlayStateListener);
    }
    private void playYouTubeVideo(String videoUrl) {
        final String youtubeVideoId = VideoConfig.extractYoutubeId(videoUrl);
        myouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        myouTubePlayerView.setVisibility(View.VISIBLE);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeVideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        myouTubePlayerView.initialize(VideoConfig.getApiKey(), mOnInitializedListener);
    }

    private void playVimeoOnWebView(String videoUrl) {
        String vimeoVideoId = VideoConfig.extractVimeoId(videoUrl);
        WebView webView = (WebView) findViewById(R.id.videoPlayerWebView);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://player.vimeo.com/video/"+vimeoVideoId+"?player_id=player&title=0&byline=0&portrait=0&autoplay=1&api=1");
    }
}
