package com.creativeitem.academy.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.creativeitem.academy.Activities.SignInActivity;
import com.creativeitem.academy.Adapters.WishlistAdapter;
import com.creativeitem.academy.JSONSchemas.BooleanSchema;
import com.creativeitem.academy.JSONSchemas.CourseSchema;
import com.creativeitem.academy.JSONSchemas.StatusSchema;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WishlistFragment extends Fragment implements WishlistAdapter.RemoveItemFromWishList {
    private static final String TAG = "WishlistFragment";
    GridView myWishlistGridLayout;
    private ProgressBar progressBar;
    Button signInButton;
    TextView myCoursesLabel;
    RelativeLayout myWishlistView, signInPlaceholder;


    WishlistAdapter.RemoveItemFromWishList mRemoveFromWishlist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.wishlist_fragment, container, false);
        init(view);
        initProgressBar(view);
        if (isLoggedIn()){
            this.loggedInView();
        }else{
            this.loggedOutView();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoggedIn()){
            this.loggedInView();
        }else{
            this.loggedOutView();
        }
    }

    private void init(View view) {
        myWishlistGridLayout = view.findViewById(R.id.myCoursesGridLayout);
        myCoursesLabel = view.findViewById(R.id.myCoursesLabel);
        myWishlistView = view.findViewById(R.id.myWishlistView);
        signInPlaceholder = view.findViewById(R.id.signInPlaceHolder);
        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        int userValidity = preferences.getInt("userValidity", 0);
        if (userValidity == 1) {
            return true;
        }else{
            return false;
        }
    }

    // it will show the looged in view
    private void loggedInView() {
        // fetching all of the my courses
        getMyWishlist();
        signInPlaceholder.setVisibility(View.GONE);
        myWishlistView.setVisibility(View.VISIBLE);
    }

    // it will show the looged out view
    private void loggedOutView() {
        signInPlaceholder.setVisibility(View.VISIBLE);
        myWishlistView.setVisibility(View.GONE);
    }

    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void initMyWishlistGridView(ArrayList<Course> mWishlist){
        WishlistAdapter adapter = new WishlistAdapter(getActivity(), mWishlist, this);
        myWishlistGridLayout.invalidateViews();
        myWishlistGridLayout.setAdapter(adapter);
    }

    public void getMyWishlist() {
        progressBar.setVisibility(View.VISIBLE);
        // CourseSchema array of objects.
        final ArrayList<Course> mWishlist = new ArrayList<>();
        // Auth Token
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getMyWishlist(authToken);
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                List<CourseSchema> myCourseSchema = response.body();
                for (CourseSchema m : myCourseSchema) {
                    mWishlist.add(new Course(m.getId(), m.getTitle(), m.getThumbnail(), m.getPrice(), m.getInstructorName(), m.getRating(), m.getNumberOfRatings(), m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                }
                initMyWishlistGridView(mWishlist);
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Fetched Wishlist Successfully");
            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Log.d(TAG, "Wishlist Fetched Failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void removeFromWishList(int courseId) {
        // Auth Token
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<StatusSchema> call = api.toggleWishListItems(authToken, courseId);
        call.enqueue(new Callback<StatusSchema>() {
            @Override
            public void onResponse(Call<StatusSchema> call, Response<StatusSchema> response) {
                getMyWishlist();
            }

            @Override
            public void onFailure(Call<StatusSchema> call, Throwable t) {
                Log.d(TAG, "Wishlist removed Failed");
            }
        });
    }
}
