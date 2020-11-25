package com.creativeitem.academy.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.creativeitem.academy.Fragments.AccountFragment;
import com.creativeitem.academy.Fragments.CourseFragment;
import com.creativeitem.academy.Fragments.MyCourseFragment;
import com.creativeitem.academy.Fragments.WishlistFragment;
import com.creativeitem.academy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomNavigationActivity extends AppCompatActivity {
    private static final String TAG = "BottomNavigationActivit";
    ToggleFilter mToggleFilter;
    BottomNavigationView bottomNavigationView;

    public BottomNavigationActivity(MainActivity mainActivity) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_view);
        init();
    }


    public void BottomNavigationActivity(ToggleFilter toggleFilter) {
        mToggleFilter = toggleFilter;
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //Creating and sending bottom navigation views object and bottom navigation view listener object
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    // This function gets fired up while clicking on bottom navigation bar
    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.navigation_course:
                            selectedFragment = new CourseFragment();
                            mToggleFilter.toggleFilterButton(true);
                            break;
                        case R.id.navigation_my_course:
                            selectedFragment = new MyCourseFragment();
                            break;
                        case R.id.navigation_wishlist:
                            selectedFragment = new WishlistFragment();
                            break;
                        case R.id.navigation_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.homePageFrameLayout, selectedFragment).commit();
                    return true;
                }
            };

    interface ToggleFilter {
        void toggleFilterButton(boolean status);
    }
}

