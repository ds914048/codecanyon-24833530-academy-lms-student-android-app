package com.creativeitem.academy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creativeitem.academy.Adapters.TopCourseAdapter;
import com.creativeitem.academy.JSONSchemas.CategorySchema;
import com.creativeitem.academy.JSONSchemas.CourseSchema;
import com.creativeitem.academy.Models.Category;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.Utils.Helpers;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Adapters.CategoryAdapter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseFragment extends Fragment {

    //vars
    private static final String TAG = "Fragment";
    // CategorySchema array of objects.
    private ArrayList<Category> mCategory = new ArrayList<>();
    // CourseSchema array of objects.
    private ArrayList<Course> mtopCourse = new ArrayList<>();

    private RecyclerView recyclerViewForTopCourses = null;
    private RecyclerView recyclerViewForCategories = null;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        //This function is responsible for fetching the images from the web
        View view = inflater.inflate(R.layout.course_fragment, container, false);
        recyclerViewForTopCourses  = view.findViewById(R.id.recyclerViewForTopCourses);
        recyclerViewForCategories  = view.findViewById(R.id.recyclerViewForCategories);
        initProgressBar(view);
        Log.d("Done", "Start");
        getTopCourses();
        getCategories();
        return view;
    }

    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void initTopCourseRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewForTopCourses.setLayoutManager(layoutManager);
        TopCourseAdapter adapter = new TopCourseAdapter(getActivity(), mtopCourse);
        recyclerViewForTopCourses.setAdapter(adapter);
    }

    private void initCategoryListRecyclerView() {
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), mCategory);
        recyclerViewForCategories.setAdapter(adapter);
        recyclerViewForCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setHeight(int numberOfCategories) {

        int pixels = Helpers.convertDpToPixel((numberOfCategories * 90) + 10); // 9 is the number of categories and the 90 is each items height with the margin of top and bottom. Extra 10 dp for readability
        Log.d(TAG, "Lets change the height of recycler view");
        ViewGroup.LayoutParams params1 = recyclerViewForCategories.getLayoutParams();
        recyclerViewForCategories.setMinimumHeight(pixels);
        recyclerViewForCategories.requestLayout();
    }

    private  void getTopCourses() {
        progressBar.setVisibility(View.VISIBLE);
        // Making an empty array of mtopCourse
        mtopCourse = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getTopCourses();
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                Log.d("Size of response", response.toString());
                List<CourseSchema> topCourseSchema = response.body();
                for (CourseSchema m : topCourseSchema){
                    mtopCourse.add(new Course(m.getId(),m.getTitle(),m.getThumbnail(),m.getPrice(),m.getInstructorName(),m.getRating(),m.getNumberOfRatings(),m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                }
                initTopCourseRecyclerView();
                Log.d("Course Done", "done");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Log.d(TAG, "Top Course Fetched Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getCategories() {
        progressBar.setVisibility(View.VISIBLE);
        // Making empty array of category
        mCategory = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CategorySchema>> call = api.getCategories();
        call.enqueue(new Callback<List<CategorySchema>>() {
            @Override
            public void onResponse(Call<List<CategorySchema>> call, Response<List<CategorySchema>> response) {
                Log.d(TAG, "CategorySchema Fetched Successfully");
                List<CategorySchema> categorySchema = response.body();
                for (CategorySchema m : categorySchema) {
                    mCategory.add(new Category(m.getId(), m.getName(), m.getThumbnail(), m.getNumberOfCourses()));
                }
                initCategoryListRecyclerView();
                setHeight(mCategory.size());
                Log.d("Done", " category done");
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<CategorySchema>> call, Throwable t) {
                Log.d(TAG, "CategorySchema Fetching Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}