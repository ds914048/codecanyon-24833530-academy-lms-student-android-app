package com.creativeitem.academy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.Wave;

public class QuizActivity extends AppCompatActivity {

    WebView quizView;
    private ProgressBar progressBar;
    private ProgressBar progressBarForVideoPlayer;
    private int lessonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        init();
        initProgressBar();
        getTheLessonId();
        setupQuizWebView();
    }

    private void init() {
        quizView = findViewById(R.id.quizView);
    }
    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);

        progressBarForVideoPlayer = findViewById(R.id.loadingVideoPlayer);
        Sprite waveLoading = new Wave();
        progressBarForVideoPlayer.setIndeterminateDrawable(waveLoading);
    }

    private void getTheLessonId() {
        this.lessonId = (int) getIntent().getSerializableExtra("lessonId");
    }

    private void setupQuizWebView() {
        progressBar.setVisibility(View.VISIBLE);
        quizView.getSettings().setJavaScriptEnabled(true); // enable javascript
        quizView.getSettings().setDomStorageEnabled(true);
        quizView.getSettings().setAppCacheEnabled(true);
        quizView.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        quizView.getSettings().setDatabaseEnabled(true);
        quizView.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        quizView.getSettings().setPluginState(WebSettings.PluginState.ON);
        quizView.getSettings().setDomStorageEnabled(true);
        quizView.loadUrl(Api.QUIZ_BASE_URL+lessonId);
        quizView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void handleBackButton(View view) {
        QuizActivity.super.onBackPressed();
    }

    public void takeAgain(View view) {
        this.setupQuizWebView();
    }
}
