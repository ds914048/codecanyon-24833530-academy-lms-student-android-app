package com.creativeitem.academy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creativeitem.academy.Adapters.CourseCurriculumSectionAdapter;
import com.creativeitem.academy.Adapters.ViewPagerAdapter;
import com.creativeitem.academy.JSONSchemas.CourseDetailsSchema;
import com.creativeitem.academy.JSONSchemas.SectionSchema;
import com.creativeitem.academy.JSONSchemas.StatusSchema;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Models.CourseDetails;
import com.creativeitem.academy.Models.Section;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.BounceInterpolator;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseDetailsActivity extends AppCompatActivity {
    private Course mCourse;
    private TextView mcourseTitle;
    private TextView mNumberOfEnrolledStudentNumber;
    private TextView mNumericRating;
    private TextView mTotalNumberOfRatingByUsers;
    private TextView mCoursePrice;
    private ImageView mCourseBanner;
    private RatingBar mStarRating;
    private ImageButton mPlayCoursePreview;
    private ImageButton mWishlistThisCourse;
    private ImageButton mShareThisCourse;
    private ImageButton mBackToCourseList;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private ProgressBar progressBar;
    private ArrayList<CourseDetails> mEachCourseDetail = new ArrayList<>();
    private ArrayList<Section> mSections = new ArrayList<>();
    private RecyclerView mSectionRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        initElements();
        initProgressBar();
        // The filtered course object is being passed from another activity or adapter
        getCourseObject();
    }

    private void initElements() {
        mBackToCourseList = findViewById(R.id.backToAllCoursesList);
        mcourseTitle = findViewById(R.id.courseTitle);
        mNumberOfEnrolledStudentNumber = findViewById(R.id.numberOfEnrolledStudentNumber);
        mNumericRating = findViewById(R.id.numericRating);
        mTotalNumberOfRatingByUsers = findViewById(R.id.totalNumberOfRatingByUsers);
        mCoursePrice = findViewById(R.id.coursePrice);
        mCourseBanner = findViewById(R.id.courseBannerImage);
        mStarRating = findViewById(R.id.starRating);
        mPlayCoursePreview = findViewById(R.id.playCoursePreview);
        mWishlistThisCourse = findViewById(R.id.wishlistThisCourse);
        mShareThisCourse = findViewById(R.id.shareThisCourse);
        mShareThisCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareThisCourseOnSocialMedia(view);
            }
        });
        mSectionRecyclerView = findViewById(R.id.courseCurriculumSectionRecyclerView);
    }

    private void setupViewPager(CourseDetails courseDetails) {
        mViewPager = findViewById(R.id.courseViewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), courseDetails);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.courseViewPagerTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }
    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }
    public void handleBackButton(View view) {
        CourseDetailsActivity.super.onBackPressed();
    }

    private void getCourseObject() {
        if (getIntent().hasExtra("courseId")) {
            int courseId = (int) getIntent().getSerializableExtra("courseId");
            getCourseDetails(courseId);
        } else {
            mCourse = (Course) getIntent().getSerializableExtra("Course");
            mcourseTitle.setText(mCourse.getTitle());
            mStarRating.setRating(mCourse.getRating());
            mTotalNumberOfRatingByUsers.setText("( "+mCourse.getTotalNumberRating()+" )");
            mNumberOfEnrolledStudentNumber.setText(Integer.toString(mCourse.getNumberOfEnrollment()));
            Glide.with(this)
                    .asBitmap()
                    .load(mCourse.getThumbnail())
                    .into(mCourseBanner);
            mPlayCoursePreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playPreview();
                }
            });
            mCoursePrice.setText(mCourse.getPrice());
            mNumericRating.setText(Float.toString(mCourse.getRating()));
            getCourseDetails(mCourse.getId());
        }

    }
    
    private void playPreview() {
        Intent intent = new Intent(getApplicationContext(), PopUpActivity.class);
        intent.putExtra("Course", mCourse);
        startActivity(intent);
    }

    // This block of code is responsilble for sharing on social media
    private void shareThisCourseOnSocialMedia(View view) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = mCourse.getShareableLink();
        String shareSub = mCourse.getTitle();
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }

    //API Calls
    private void getCourseDetails(Integer courseId) {
        progressBar.setVisibility(View.VISIBLE);
        // Auth Token
        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        final String authToken = preferences.getString("userToken", "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseDetailsSchema>> call = api.getCourseDetails(authToken, courseId);
        call.enqueue(new Callback<List<CourseDetailsSchema>>() {
            @Override
            public void onResponse(Call<List<CourseDetailsSchema>> call, Response<List<CourseDetailsSchema>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                List<CourseDetailsSchema> courseDetailsSchemas = response.body();
                Log.d("CourseDetailsHere", new Gson().toJson(courseDetailsSchemas));
                for (CourseDetailsSchema m : courseDetailsSchemas){
                    mEachCourseDetail.add(new CourseDetails(m.getId(), m.getIncludes(), m.getOutcomes(), m.getRequirements(), m.isWishlisted()));
                    List<SectionSchema> sectionSchemas = m.getSections();
                    for (SectionSchema n : sectionSchemas){
                        mSections.add(new Section(n.getId(), n.getTitle(), n.getLessons(), n.getCompletedLessonNumber(), n.getTotalDuration(), n.getLessonCounterStarts(), n.getLessonCounterEnds()));
                    }
                    if (m.isWishlisted()){
                        mWishlistThisCourse.setImageResource(R.drawable.wishlist_filled);
                    }else{
                        mWishlistThisCourse.setImageResource(R.drawable.wishlist_empty);
                    }
                }
                setupViewPager(mEachCourseDetail.get(0));
                initSectionRecyclerView();

            }

            @Override
            public void onFailure(Call<List<CourseDetailsSchema>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("CourseDetailsHere", t.toString());
            }
        });

    }

    private void initSectionRecyclerView() {
        CourseCurriculumSectionAdapter adapter = new CourseCurriculumSectionAdapter(getApplicationContext(), mSections);
        mSectionRecyclerView.setAdapter(adapter);
        mSectionRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setHeight(mSections.size(), mSectionRecyclerView);
    }

    private void setHeight(int numberOfItems, RecyclerView mRecyclerView) {
        int pixels = Helpers.convertDpToPixel((numberOfItems * 40) + 10); // numberOfItems is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        ViewGroup.LayoutParams params1 = mRecyclerView.getLayoutParams();
        mRecyclerView.setMinimumHeight(pixels);
        mRecyclerView.requestLayout();
    }

    public void handleWishListButton(View view) {
        // Auth Token
        SharedPreferences preferences = getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "invalid");
        if (authToken.equals("invalid")){
            Toast.makeText(this, "Please Login First", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CourseDetailsActivity.this, SignInActivity.class);
            startActivity(intent);
        }else{
            toggleWishListItem();
        }
    }

    private void showConfirmationAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are You Sure?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //code
                        toggleWishListItem();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
    private void toggleWishListItem() {
        progressBar.setVisibility(View.VISIBLE);
        // Auth Token
        SharedPreferences preferences = this.getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<StatusSchema> call = api.toggleWishListItems(authToken, mCourse.getId());
        call.enqueue(new Callback<StatusSchema>() {
            @Override
            public void onResponse(Call<StatusSchema> call, Response<StatusSchema> response) {
                StatusSchema statusSchema = response.body();
                if (statusSchema.getStatus().equals("added")){
                    mWishlistThisCourse.setImageResource(R.drawable.wishlist_filled);
                    setBounceAnimationOnButton(mWishlistThisCourse);
                    Toast.makeText(CourseDetailsActivity.this, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                }else{
                    mWishlistThisCourse.setImageResource(R.drawable.wishlist_empty);
                    setBounceAnimationOnButton(mWishlistThisCourse);
                    Toast.makeText(CourseDetailsActivity.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StatusSchema> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setBounceAnimationOnButton(ImageButton imageButton) {
        final Animation bounceAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        BounceInterpolator interpolator = new BounceInterpolator(0.2, 30);
        bounceAnim.setInterpolator(interpolator);
        imageButton.startAnimation(bounceAnim);
    }

    public void handleBuyNow(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mCourse.getShareableLink()));
        startActivity(browserIntent);
    }
}
