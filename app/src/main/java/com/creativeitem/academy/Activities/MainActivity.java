package com.creativeitem.academy.Activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.creativeitem.academy.Fragments.AccountFragment;
import com.creativeitem.academy.Fragments.CourseFragment;
import com.creativeitem.academy.Fragments.MyCourseFragment;
import com.creativeitem.academy.Fragments.WishlistFragment;
import com.creativeitem.academy.JSONSchemas.CategorySchema;
import com.creativeitem.academy.JSONSchemas.CourseSchema;
import com.creativeitem.academy.JSONSchemas.LanguageSchema;
import com.creativeitem.academy.JSONSchemas.SystemSettings;
import com.creativeitem.academy.Models.Category;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Models.DifficultyLevel;
import com.creativeitem.academy.Models.Language;
import com.creativeitem.academy.Models.Price;
import com.creativeitem.academy.Models.Rating;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BottomSheetBehavior bottomSheetBehavior;

    private Spinner categorySpinner;
    private Spinner priceSpinner;
    private Spinner difficultyLevelSpinner;
    private Spinner languageSpinner;
    private Spinner ratingSpinner;

    private String selectedCategory;
    private String selectedPrice;
    private String selectedDifficultyLevel;
    private String selectedLanguage;
    private String selectedRating;


    Button showSearchBoxButton;
    Button hideSearchBoxButton;
    EditText searchStringInputField;
    Button backButton;
    ImageView applicationLogo;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton filterButton;
    ImageButton closeFilterViewButton;
    Button filterApplyButton;
    Button filterResetButton;
    ArrayAdapter<Category> categoryAdapter;
    ArrayAdapter<Price> priceAdapter;
    ArrayAdapter<DifficultyLevel> difficultyLevelArrayAdapter;
    ArrayAdapter<Language> languageArrayAdapter;
    ArrayAdapter<Rating> ratingArrayAdapter;
    // this is the search string
    Editable searchString;
    View bottomSheetView;
    private ArrayList<Course> mCourses = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Check this out", "Main activity");
        //This is the layout file which is going to be displayed
        setContentView(R.layout.activity_main);
        // calling the init function
        init();

        getSystemSettings();
        // Activating Course Fragment while the application runs for the first time
        getSupportFragmentManager().beginTransaction().replace(R.id.homePageFrameLayout, new CourseFragment()).commit();

        // Initializing bottom sheet view
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navListener);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(TAG, "Bottom Sheet Collapsed");
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "Bottom Sheet Expanded");
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "Bottom Sheet Dragging");
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        //Initializing the spinners
        getCategories();
        getPrice();
        getDifficultyLevel();
        getLanguage();
        getRating();

        // This is the onClick listener for the collapse filter view button
        closeFilterViewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        // This is the onClick listener for the Apply Filter view button
        filterApplyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
                filterCourse();
            }
        });

        // This is the onClick listener for the Reset Filter view button
        filterResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                resetFilter();
            }
        });
    }

    private void init() {
        showSearchBoxButton = findViewById(R.id.showSearchBarButton);
        hideSearchBoxButton = findViewById(R.id.hideSearchBarButton);
        applicationLogo = findViewById(R.id.applicationLogo);
        backButton = findViewById(R.id.backButton);
        searchStringInputField = findViewById(R.id.searchStringInputField);

        filterButton = findViewById(R.id.floatingFilterButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomSheetView = findViewById(R.id.bottomSheet);
        closeFilterViewButton = findViewById(R.id.filterViewCloseButton);
        filterApplyButton = findViewById(R.id.filterApplyButton);
        filterResetButton = findViewById(R.id.filterResetButton);

        // Adding Search button on keyboard
        searchStringInputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCourse();
                    return true;
                }
                return false;
            }
        });
        backButton.setVisibility(View.GONE);
        //Creating and sending bottom navigation views object and bottom navigation view listener object
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    public void handleFilterButton(View view) {
        switch (bottomSheetBehavior.getState()){
            case BottomSheetBehavior.STATE_COLLAPSED:
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
                bottomSheetView.bringToFront();
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                bottomSheetBehavior.setState(bottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    private void initializeCategorySpinner(final ArrayList<Category> mCategory) {
        categorySpinner = findViewById(R.id.filterCategorySpinner);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCategory);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSelectedCategory(mCategory.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializePriceSpinner(final ArrayList<Price> mPrice){
        priceSpinner = findViewById(R.id.filterPriceSpinner);
        priceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mPrice);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceSpinner.setAdapter(priceAdapter);
        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSelectedPrice(mPrice.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initializeDifficultySpinner(final ArrayList<DifficultyLevel> mDifficultyLevel){
        difficultyLevelSpinner = findViewById(R.id.filterLevelSpinner);
        difficultyLevelArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mDifficultyLevel);
        difficultyLevelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyLevelSpinner.setAdapter(difficultyLevelArrayAdapter);
        difficultyLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSelectedDifficultyLevel(mDifficultyLevel.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeLanguageSpinner(final ArrayList<Language> mLanguage){
        languageSpinner = findViewById(R.id.filterLanguageSpinner);
        languageArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mLanguage);
        languageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageArrayAdapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSelectedLanguage(mLanguage.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeRatingSpinner(final ArrayList<Rating> mRating){
        ratingSpinner = findViewById(R.id.filterRatingSpinner);
        ratingArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mRating);
        ratingArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ratingArrayAdapter);
        ratingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSelectedRating(mRating.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCategories() {
        // Making empty array of category
        final ArrayList<Category> mCategory = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CategorySchema>> call = api.getCategories();
        call.enqueue(new Callback<List<CategorySchema>>() {
            @Override
            public void onResponse(Call<List<CategorySchema>> call, Response<List<CategorySchema>> response) {
                Log.d(TAG, "CategorySchema Fetched Successfully");
                List<CategorySchema> categorySchema = response.body();
                mCategory.add(new Category(0, "All", "", 0));
                for (CategorySchema m : categorySchema) {
                    mCategory.add(new Category(m.getId(), m.getName(), m.getThumbnail(), m.getNumberOfCourses()));
                }
                initializeCategorySpinner(mCategory);
            }

            @Override
            public void onFailure(Call<List<CategorySchema>> call, Throwable t) {
                Log.d(TAG, "CategorySchema Fetching Failed");
            }
        });
    }
    private void getPrice() {
        // Making empty array of category
        final ArrayList<Price> mPrice = new ArrayList<>();
        mPrice.add(new Price(0, "all", "All"));
        mPrice.add(new Price(1, "free", "Free"));
        mPrice.add(new Price(2, "paid", "Paid"));
        initializePriceSpinner(mPrice);
    }

    private void getDifficultyLevel() {
        // Making empty array of category
        final ArrayList<DifficultyLevel> mDifficultyLevel = new ArrayList<>();
        mDifficultyLevel.add(new DifficultyLevel(0, "all", "All"));
        mDifficultyLevel.add(new DifficultyLevel(1, "beginner", "Beginner"));
        mDifficultyLevel.add(new DifficultyLevel(2, "advanced", "Advanced"));
        mDifficultyLevel.add(new DifficultyLevel(3, "intermediate", "Intermediate"));
        initializeDifficultySpinner(mDifficultyLevel);
    }
    private void getLanguage() {
        // Making empty array of Language
        final ArrayList<Language> mLanguage = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<LanguageSchema>> call = api.getLanguages();
        call.enqueue(new Callback<List<LanguageSchema>>() {
            @Override
            public void onResponse(Call<List<LanguageSchema>> call, Response<List<LanguageSchema>> response) {
                List<LanguageSchema> languageSchema = response.body();
                mLanguage.add(new Language(0, "all", "All"));
                for (LanguageSchema m : languageSchema) {
                    mLanguage.add(new Language(m.getId(),m.getValue(), m.getDisplayedValue()));
                }
                initializeLanguageSpinner(mLanguage);
            }

            @Override
            public void onFailure(Call<List<LanguageSchema>> call, Throwable t) {

            }
        });
    }

    private void getRating() {
        // Making empty array of Rating
        final ArrayList<Rating> mRating = new ArrayList<>();

        mRating.add(new Rating(0, 0, "All"));
        mRating.add(new Rating(1, 1, "⭐️"));
        mRating.add(new Rating(2, 2, "⭐⭐️"));
        mRating.add(new Rating(3, 3, "⭐️⭐⭐"));
        mRating.add(new Rating(4, 4, "⭐️⭐⭐⭐"));
        mRating.add(new Rating(5, 5, "⭐️⭐⭐⭐⭐"));
        initializeRatingSpinner(mRating);
    }

    public void getSelectedCategory(Category category) {
        if (category.getId() == 0){
            this.selectedCategory = "all";
        }else{
            this.selectedCategory = Integer.toString(category.getId());
        }
    }
    public void getSelectedPrice(Price price) {
        this.selectedPrice = price.getValue();
    }
    public void getSelectedDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.selectedDifficultyLevel = difficultyLevel.getValue();
    }
    public void getSelectedLanguage(Language language) {
        this.selectedLanguage = language.getValue();
    }
    public void getSelectedRating(Rating rating) {
        if (rating.getValue() == 0){
            this.selectedRating = "all";
        }else{
            this.selectedRating = Integer.toString(rating.getValue());
        }
    }


    // This function is responsible for filtering the course list
    private void filterCourse() {
        String searchedString = searchString+"";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getFilteredCourses(selectedCategory, selectedPrice, selectedDifficultyLevel, selectedLanguage, selectedRating, searchedString);
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                Log.d(TAG, "The Response "+response.toString());
                mCourses = new ArrayList<>();
                List<CourseSchema> courseSchemas = response.body();
                for (CourseSchema m : courseSchemas) {
                    mCourses.add(new Course(m.getId(), m.getTitle(), m.getThumbnail(), m.getPrice(), m.getInstructorName(), m.getRating(), m.getNumberOfRatings(),m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                    Log.d(TAG, "Fetched Data "+m.getTitle());
                }

                Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
                intent.putExtra("Course", mCourses);
                MainActivity.this.startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        // Toggling keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchStringInputField.getWindowToken(),0);
    }

    // Reset Filter Page
    private void resetFilter(){
        categorySpinner.setSelection(0, true);
        priceSpinner.setSelection(0, true);
        difficultyLevelSpinner.setSelection(0, true);
        languageSpinner.setSelection(0, true);
        ratingSpinner.setSelection(0, true);
    }


    private void getSystemSettings() {
        Log.d("System Settings", "System Settings");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<SystemSettings> call = api.getSystemSettings();
        call.enqueue(new Callback<SystemSettings>() {
            @Override
            public void onResponse(Call<SystemSettings> call, Response<SystemSettings> response) {
                SystemSettings systemSettings = response.body();
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(systemSettings.getThumbnail())
                        .into(applicationLogo);
            }

            @Override
            public void onFailure(Call<SystemSettings> call, Throwable t) {

            }
        });
    }

    public void searchCourse() {
        searchString = searchStringInputField.getText();
        getCourseBySearchString(searchString);
    }

    public void showSearchBox(View view) {
        // Showing the search input field
        searchStringInputField.setVisibility(View.VISIBLE);
        searchStringInputField.setFocusableInTouchMode(true);
        searchStringInputField.requestFocus();
        // Hiding the application logo
        applicationLogo.setVisibility(View.GONE);
        //Hiding the search button
        showSearchBoxButton.setVisibility(View.GONE);
        //Show hide search box button
        hideSearchBoxButton.setVisibility(View.VISIBLE);
        // Showing keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchStringInputField, InputMethodManager.SHOW_IMPLICIT);
    }
    public void hideSearchBox(View view) {
        // Hiding the search input field
        searchStringInputField.setVisibility(View.GONE);
        // Showing the application logo
        applicationLogo.setVisibility(View.VISIBLE);
        //Showing the search button
        showSearchBoxButton.setVisibility(View.VISIBLE);
        //Hide hide search box button
        hideSearchBoxButton.setVisibility(View.GONE);

        // Hiding keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchStringInputField.getWindowToken(),0);
    }

    private void getCourseBySearchString(Editable searchString) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<List<CourseSchema>> call = api.getCoursesBySearchString(searchString);
        call.enqueue(new Callback<List<CourseSchema>>() {
            @Override
            public void onResponse(Call<List<CourseSchema>> call, Response<List<CourseSchema>> response) {
                mCourses = new ArrayList<>();
                List<CourseSchema> courseSchemas = response.body();
                for (CourseSchema m : courseSchemas) {
                    mCourses.add(new Course(m.getId(),m.getTitle(),m.getThumbnail(),m.getPrice(),m.getInstructorName(),m.getRating(),m.getNumberOfRatings(),m.getTotalEnrollment(), m.getShareableLink(), m.getCourseOverviewProvider(), m.getCourseOverviewUrl()));
                }

                Intent intent = new Intent(getApplicationContext(), CoursesActivity.class);
                intent.putExtra("Course", mCourses);
                getApplicationContext().startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        // Hiding keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchStringInputField.getWindowToken(),0);
    }

    public void viewAllCourses(View view) {
        filterCourse();
    }

    public FloatingActionButton getFloatingActionButton() {
        return filterButton;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){

                        case R.id.navigation_course:
                            filterButton.show();
                            selectedFragment = new CourseFragment();
                            break;
                        case R.id.navigation_my_course:
                            filterButton.hide();
                            selectedFragment = new MyCourseFragment();
                            break;
                        case R.id.navigation_wishlist:
                            filterButton.hide();
                            selectedFragment = new WishlistFragment();
                            break;
                        case R.id.navigation_account:
                            filterButton.hide();
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.homePageFrameLayout, selectedFragment).commit();
                    return true;
                }
            };
}