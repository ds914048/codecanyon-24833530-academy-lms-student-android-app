package com.creativeitem.academy.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.creativeitem.academy.JSONSchemas.CategorySchema;
import com.creativeitem.academy.JSONSchemas.CourseSchema;
import com.creativeitem.academy.JSONSchemas.LanguageSchema;
import com.creativeitem.academy.Models.Category;
import com.creativeitem.academy.Models.Course;
import com.creativeitem.academy.Models.DifficultyLevel;
import com.creativeitem.academy.Models.Language;
import com.creativeitem.academy.Models.Price;
import com.creativeitem.academy.Models.Rating;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Adapters.CoursesAdapter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoursesActivity extends AppCompatActivity{
    //vars
    private static final String TAG = "Course-List";
    private RecyclerView recyclerViewForFilteredCourses = null;
    private ArrayList<Course> mCourses = new ArrayList<>();
    private TextView numberOfFilteredCourses;
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
    FloatingActionButton filterButton;
    View bottomSheetView;
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

    private BottomSheetBehavior bottomSheetBehavior;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        init();
        initProgressBar();
        // The filtered course object is being passed from another activity or adapter
        getCourseObject();

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:

                    case BottomSheetBehavior.STATE_EXPANDED:
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
        backButton.setVisibility(View.VISIBLE);

        searchStringInputField = findViewById(R.id.searchStringInputField);
        filterButton = findViewById(R.id.floatingFilterButton);
        bottomSheetView = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView);
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
    }
    public void handleBackButton(View view) {
        CoursesActivity.super.onBackPressed();
    }
    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }
    private void getCourseObject() {
        // Initializing the fetched course object to mCourses
        recyclerViewForFilteredCourses  = findViewById(R.id.recyclerViewForFilteredCourses);
        mCourses = (ArrayList<Course>) getIntent().getSerializableExtra("Course");

        // Updating the course result text
        numberOfFilteredCourses = findViewById(R.id.filterResultTitle);
        numberOfFilteredCourses.setText("Showing "+mCourses.size()+" Courses");

        // Initializing the recyclerview
        reloadCourses(mCourses);
    }

    private void reloadCourses(ArrayList<Course> mCourses) {
        // Updating the course result text
        numberOfFilteredCourses = findViewById(R.id.filterResultTitle);
        numberOfFilteredCourses.setText("Showing "+mCourses.size()+" Courses");

        // Initializing the recyclerview
        initFilteredCourseRecyclerView();
    }

    private void initFilteredCourseRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        CoursesAdapter adapter = new CoursesAdapter(CoursesActivity.this, mCourses);
        recyclerViewForFilteredCourses.setAdapter(adapter);
        recyclerViewForFilteredCourses.setLayoutManager(layoutManager);
    }


    public void searchCourse() {
        searchString = searchStringInputField.getText();
        filterCourse();
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
        // Toggling keyboard
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
        // clearing the search box text
        searchStringInputField.getText().clear();
        // Toggling keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchStringInputField.getWindowToken(),0);
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

    // Filter pages elements initializing
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
        Log.d("Search String", "The search string "+searchString);
        progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.INVISIBLE);
                reloadCourses(mCourses);

            }

            @Override
            public void onFailure(Call<List<CourseSchema>> call, Throwable t) {
                Toast.makeText(CoursesActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
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
}
