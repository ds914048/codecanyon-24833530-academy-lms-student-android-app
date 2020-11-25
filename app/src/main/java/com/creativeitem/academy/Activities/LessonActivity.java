package com.creativeitem.academy.Activities;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.creativeitem.academy.Adapters.LessonAdapter;
import com.creativeitem.academy.Adapters.SectionAdapter;
import com.creativeitem.academy.JSONSchemas.CourseDetailsSchema;
import com.creativeitem.academy.JSONSchemas.CourseSchema;
import com.creativeitem.academy.JSONSchemas.LessonCompletionSchema;
import com.creativeitem.academy.JSONSchemas.LessonSchema;
import com.creativeitem.academy.JSONSchemas.SectionSchema;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Models.CourseDetails;
import com.creativeitem.academy.Models.MyCourse;
import com.creativeitem.academy.Models.Section;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.creativeitem.academy.Utils.VideoConfig;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LessonActivity extends YouTubeBaseActivity implements SectionAdapter.PassLessonToActivity {

    private TextView courseTitle;
    private TextView courseCompletionNumberOutOfTotal;
    private ProgressBar courseCompletionProgressBar;
    private MyCourse mCourse;
    private ArrayList<Section> mSections = new ArrayList<>();
    private ProgressBar progressBar;
    private ProgressBar progressBarForVideoPlayer;
    private ArrayList<CourseDetails> mEachCourseDetail = new ArrayList<>();

    RecyclerView sectionRecyclerView;
    VideoView html5VideoPlayer;
    YouTubePlayerView youtubePlayer;
    WebView vimeoWebViewPlayer;
    ImageButton fullScreenLessonBtn;
    LessonSchema mLesson;
    ImageView emptyVideoScreen;
    TextView attachmentTitle;
    Button downloadAttachmentButton;
    RelativeLayout downloadAttachmentArea;
    DownloadManager downloadManager;
    Spinner moreButton;
    RelativeLayout quizStuffs;

    TextView quizTitle;
    Button startQuiz;

    YouTubePlayer.OnInitializedListener  mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        init();
        getCourseObject();
        getAllSections();
        initViewElement();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width) , (int) (height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    private void init() {
        courseTitle = findViewById(R.id.courseTitle);
        courseCompletionNumberOutOfTotal = findViewById(R.id.courseCompletionNumberOutOfTotal);
        courseCompletionProgressBar = findViewById(R.id.courseCompletionProgressBar);
        sectionRecyclerView = findViewById(R.id.sectionRecyclerView);
        html5VideoPlayer = findViewById(R.id.html5Player);
        youtubePlayer =  findViewById(R.id.youtubePlayer);
        vimeoWebViewPlayer = findViewById(R.id.videoPlayerWebView);
        fullScreenLessonBtn = findViewById(R.id.fullScreenLesson);
        emptyVideoScreen = findViewById(R.id.emptyVideoScreen);
        downloadAttachmentArea = findViewById(R.id.downloadAttachmentArea);
        attachmentTitle = findViewById(R.id.attachmentTitle);
        downloadAttachmentButton = findViewById(R.id.downloadAttachmentButton);
        moreButton = findViewById(R.id.moreButton);
        quizTitle = findViewById(R.id.quizTitle);
        startQuiz = findViewById(R.id.startQuiz);
        quizStuffs = findViewById(R.id.quizStuffs);
        initProgressBar();
        initViewElement();
        initBroadcastReceiver();
    }

    private void initViewElement() {
        fullScreenLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullScreenTheLesson(mLesson);
            }
        });
        downloadAttachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadAttachemt();
            }
        });

        initMoreOptionButton();
    }

    private void initMoreOptionButton() {
        ArrayAdapter<CharSequence> optionAdapter = ArrayAdapter.createFromResource(this, R.array.lesson_options, android.R.layout.simple_spinner_item);
        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        moreButton.setAdapter(optionAdapter);
        moreButton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemIdAtPosition(i) == 1){
                    getCourseObjectById("details");
                }else if(adapterView.getItemIdAtPosition(i) == 2) {
                    getCourseObjectById("share");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCourseObjectById(final String action) {
        progressBar.setVisibility(View.VISIBLE);
        // Making empty array of category
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<CourseSchema> call = api.getCourseObject(mLesson.getCourseId());
        call.enqueue(new Callback<CourseSchema>() {
            @Override
            public void onResponse(Call<CourseSchema> call, Response<CourseSchema> response) {
                CourseSchema courseSchema = response.body();
                Course mCourse = new Course(courseSchema.getId(), courseSchema.getTitle(), courseSchema.getThumbnail(), courseSchema.getPrice(), courseSchema.getInstructorName(), courseSchema.getRating(), courseSchema.getNumberOfRatings(), courseSchema.getTotalEnrollment(), courseSchema.getShareableLink(), courseSchema.getCourseOverviewProvider(), courseSchema.getCourseOverviewUrl());
                if (action.equals("details")){
                    switchToCourseDetailsActivity(mCourse);
                }else if(action.equals("share")) {
                    shareThisCourse(mCourse.getTitle(), mCourse.getShareableLink());
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CourseSchema> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void shareThisCourse(String courseTitle, String shareableLink) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = shareableLink;
        String shareSub = courseTitle;
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }
    private void switchToCourseDetailsActivity(Course mCourse) {
        Intent intent = new Intent(LessonActivity.this, CourseDetailsActivity.class);
        intent.putExtra("Course", mCourse);
        this.startActivity(intent);
    }

    private void getAllSections() {
        progressBar.setVisibility(View.VISIBLE);
        // Making empty array of category
        mSections = new ArrayList<>();
        // Auth Token
        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<SectionSchema>> call = api.getAllSections(authToken, mCourse.getId());
        call.enqueue(new Callback<List<SectionSchema>>() {
            @Override
            public void onResponse(Call<List<SectionSchema>> call, Response<List<SectionSchema>> response) {
                List<SectionSchema> sectionSchemas = response.body();
                for (SectionSchema m : sectionSchemas){
                    mSections.add(new Section(m.getId(), m.getTitle(), m.getLessons(), m.getCompletedLessonNumber(), m.getTotalDuration(), m.getLessonCounterStarts(), m.getLessonCounterEnds()));
                }
                initSectionRecyclerView();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<SectionSchema>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
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

    private void initSectionRecyclerView() {
        SectionAdapter adapter = new SectionAdapter(getApplicationContext(), mSections, this, true);
        sectionRecyclerView.setAdapter(adapter);
        sectionRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    private void getCourseObject() {
        mCourse = (MyCourse) getIntent().getSerializableExtra("Course");
        courseTitle.setText(mCourse.getTitle());
        courseCompletionNumberOutOfTotal.setText(mCourse.getTotalNumberOfCompletedLessons()+"/"+mCourse.getTotalNumberOfLessons()+" Lessons are completed");
        courseCompletionProgressBar.setProgress(mCourse.getCourseCompletion());
        Log.d("CourseId", mCourse.getId()+"");
    }
    public void handleBackButton(View view) {
        LessonActivity.super.onBackPressed();
    }

    @Override
    public void PassLesson(final LessonSchema eachLesson, LessonAdapter.ViewHolder viewHolder) {
        //viewHolder.itemView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_custom_blue_button));
        this.mLesson = eachLesson;
        courseTitle.setText(eachLesson.getTitle());
        if (eachLesson.getLessonType().equals("video")){
            fullScreenLessonBtn.setVisibility(View.VISIBLE);
            if (eachLesson.getVideoType().equals("html5") && !eachLesson.getVideoUrl().equals("")){
                playHTML5Video(eachLesson.getVideoUrl());
            }else{
                fullScreenLessonBtn.setVisibility(View.GONE);
                Toast.makeText(this, "Proper Video URL is Missing", Toast.LENGTH_SHORT).show();
                html5VideoPlayer.setVisibility(View.GONE);
                quizStuffs.setVisibility(View.GONE);
                emptyVideoScreen.setVisibility(View.VISIBLE);
            }
        }
        else if(eachLesson.getLessonType().equals("other")){
            attachmentTitle.setText("Download "+mLesson.getAttachment());
            html5VideoPlayer.setVisibility(View.GONE);
            quizStuffs.setVisibility(View.GONE);
            downloadAttachmentArea.setVisibility(View.VISIBLE);

        }else if(eachLesson.getLessonType().equals("quiz")) {
            progressBarForVideoPlayer.setVisibility(View.VISIBLE);
            fullScreenLessonBtn.setVisibility(View.GONE);
            html5VideoPlayer.setVisibility(View.GONE);
            downloadAttachmentArea.setVisibility(View.GONE);
            quizStuffs.setVisibility(View.VISIBLE);
            quizTitle.setText(eachLesson.getTitle());
            startQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LessonActivity.this, QuizActivity.class);
                    intent.putExtra("lessonId", eachLesson.getId());
                    startActivity(intent);
                }
            });
            progressBarForVideoPlayer.setVisibility(View.GONE);
        }
    }

    @Override
    public int ToggleLessonCompletionStatus(LessonSchema eachLesson, final boolean lessonCompletionStatus) {
        int courseProgress = 0;
        if (lessonCompletionStatus){
            courseProgress = 1;
        }else{
            courseProgress = 0;
        }
        progressBar.setVisibility(View.VISIBLE);
        // Auth Token
        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<LessonCompletionSchema> call = api.saveCourseProgress(authToken, eachLesson.getId(), courseProgress);
        call.enqueue(new Callback<LessonCompletionSchema>() {
            @Override
            public void onResponse(Call<LessonCompletionSchema> call, Response<LessonCompletionSchema> response) {
                LessonCompletionSchema lessonCompletionSchema = response.body();
                courseCompletionNumberOutOfTotal.setText(lessonCompletionSchema.getNumberOfCompletedLessons()+"/"+lessonCompletionSchema.getNumberOfLessons()+" Lessons are completed");
                courseCompletionProgressBar.setProgress(lessonCompletionSchema.getCourseProgress());
                progressBar.setVisibility(View.GONE);
                if (lessonCompletionStatus){
                    Toast.makeText(LessonActivity.this, "Marked as Completed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LessonActivity.this, "Marked as Incompleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LessonCompletionSchema> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LessonActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        });
        return 12;
    }

    private void playHTML5Video(final String videoUrl) {
        downloadAttachmentArea.setVisibility(View.GONE);
        progressBarForVideoPlayer.setVisibility(View.VISIBLE);
        emptyVideoScreen.setVisibility(View.GONE);
        html5VideoPlayer.setVisibility(View.VISIBLE);
        quizStuffs.setVisibility(View.GONE);
        html5VideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
                        /*
                         * add media controller
                         */
                        MediaController mc = new MediaController(LessonActivity.this);
                        html5VideoPlayer.setMediaController(mc);
                        /*
                         * and set its position on screen
                         */
                        mc.setAnchorView(html5VideoPlayer);
                        progressBarForVideoPlayer.setVisibility(View.GONE);
                    }
                });
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
    private void playYouTubeVideo(final String videoUrl) {
        html5VideoPlayer.setVisibility(View.GONE);
        vimeoWebViewPlayer.setVisibility(View.GONE);
        final String youtubeVideoId = VideoConfig.extractYoutubeId(videoUrl);
        youtubePlayer.setVisibility(View.VISIBLE);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeVideoId);
                youTubePlayer.setFullscreen(false);
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        Intent intent = new Intent(LessonActivity.this, FullScreenLessonPlayerActivity.class);
                        intent.putExtra("videoType", "YouTube");
                        intent.putExtra("videoUrl", videoUrl);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youtubePlayer.initialize(VideoConfig.getApiKey(), mOnInitializedListener);
    }

    private void playVimeoOnWebView(String videoUrl) {
        html5VideoPlayer.setVisibility(View.GONE);
        youtubePlayer.setVisibility(View.GONE);
        String vimeoVideoId = VideoConfig.extractVimeoId(videoUrl);
        vimeoWebViewPlayer.setVisibility(View.VISIBLE);
        vimeoWebViewPlayer.getSettings().setJavaScriptEnabled(true); // enable javascript
        vimeoWebViewPlayer.getSettings().setDomStorageEnabled(true);
        vimeoWebViewPlayer.getSettings().setAppCacheEnabled(true);
        vimeoWebViewPlayer.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        vimeoWebViewPlayer.getSettings().setDatabaseEnabled(true);
        vimeoWebViewPlayer.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        vimeoWebViewPlayer.getSettings().setPluginState(WebSettings.PluginState.ON);
        vimeoWebViewPlayer.getSettings().setDomStorageEnabled(true);
        vimeoWebViewPlayer.loadUrl("https://player.vimeo.com/video/"+vimeoVideoId+"?player_id=player&title=0&byline=0&portrait=0&autoplay=1&api=1");
    }

    public void fullScreenTheLesson(LessonSchema eachLesson) {
        Intent intent = new Intent(LessonActivity.this, FullScreenLessonPlayerActivity.class);
        intent.putExtra("videoType", eachLesson.getVideoType());
        intent.putExtra("videoUrl", eachLesson.getVideoUrl());
        startActivity(intent);
    }

    // This function is responsible for downloading an attachment
    public void downloadAttachemt() {
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mLesson.getAttachmentUrl()));
        downloadManager.enqueue(request);
    }

    // Broadcast receiver is important for let you know that a service is done or ongoing or something else.
    private void initBroadcastReceiver() {
        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Toast.makeText(ctxt, mLesson.getAttachment()+" Downloaded Successfully", Toast.LENGTH_SHORT).show();
                Intent downloadIntent = new Intent();
                downloadIntent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                startActivity(downloadIntent);
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
