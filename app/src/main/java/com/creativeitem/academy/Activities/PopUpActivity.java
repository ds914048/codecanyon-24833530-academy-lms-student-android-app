package com.creativeitem.academy.Activities;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.VideoConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PopUpActivity extends YouTubeBaseActivity {
    private Course mCourse;
    YouTubePlayerView myouTubePlayerView;
    YouTubePlayer.OnInitializedListener  mOnInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width) , (int) (height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        getCourseObject();
    }

    private void getCourseObject() {
        mCourse = (Course) getIntent().getSerializableExtra("Course");
        playCoursePreview();
    }

    private void playCoursePreview() {
        if (mCourse.getCourseOverviewProvider().equals("youtube")){
            playYouTubeVideo();
        }
        if (mCourse.getCourseOverviewProvider().equals("vimeo")){
            playVimeoOnWebView();
        }
        if (mCourse.getCourseOverviewProvider().equals("html5")){
            playHTML5Video();
        }
    }

    private void playHTML5Video() {
        final VideoView videoView;
        videoView = (VideoView) findViewById(R.id.html5Player);
        videoView.setVisibility(View.VISIBLE);
        videoView.setVideoPath(mCourse.getCourseOverviewUrl());
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
    }
    private void playYouTubeVideo() {
        final String youtubeVideoId = VideoConfig.extractYoutubeId(mCourse.getCourseOverviewUrl());
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

    private void playVimeoOnWebView() {
        String vimeoVideoId = VideoConfig.extractVimeoId(mCourse.getCourseOverviewUrl());
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
