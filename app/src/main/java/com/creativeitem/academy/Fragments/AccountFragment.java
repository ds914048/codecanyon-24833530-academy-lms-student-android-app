package com.creativeitem.academy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.creativeitem.academy.Activities.ChangePasswordActivity;
import com.creativeitem.academy.Activities.EditProfileActivity;
import com.creativeitem.academy.Activities.MainActivity;
import com.creativeitem.academy.Activities.SignInActivity;
import com.creativeitem.academy.JSONSchemas.UserSchema;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {
    Button signInButton;
    RelativeLayout signInPlaceholder, accountView;
    CircleImageView displayImageView;
    TextView userName;
    RelativeLayout editProfileRelativeLayout, changePasswordRelativeLayout, logOutRelativeLayout;
    private ProgressBar progressBar;
    View view;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_fragment, container, false);
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

    private boolean isLoggedIn() {
        SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        int userValidity = preferences.getInt("userValidity", 0);
        if (userValidity == 1) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    void init(View view) {
        signInPlaceholder = view.findViewById(R.id.signInPlaceHolder);
        accountView = view.findViewById(R.id.accountView);
        displayImageView = view.findViewById(R.id.displayImageView);
        userName = view.findViewById(R.id.userName);
        editProfileRelativeLayout = view.findViewById(R.id.editProfileRelativeLayout);
        changePasswordRelativeLayout = view.findViewById(R.id.changePasswordRelativeLayout);
        logOutRelativeLayout = view.findViewById(R.id.logOutRelativeLayout);

        logOutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllTheSharedPreferencesValues();
            }
        });

        editProfileRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        changePasswordRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        // SignIn button action
        signInButton = view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    // Initialize the progress bar
    private void initProgressBar(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }


    // it will show the looged in view
    private void loggedInView() {
        getUserDetails();
        signInPlaceholder.setVisibility(View.GONE);
        accountView.setVisibility(View.VISIBLE);
    }

    // it will show the looged out view
    private void loggedOutView() {
        signInPlaceholder.setVisibility(View.VISIBLE);
        accountView.setVisibility(View.GONE);
    }

    // get user details
    private void getUserDetails() {
        if (isLoggedIn()){
            getUserDataFromAPI();
        }else{
            Toast.makeText(getContext(), "Please Login First", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserDataFromAPI() {
        final SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<UserSchema> call = api.getUserProfileInfo(authToken);
        call.enqueue(new Callback<UserSchema>() {
            @Override
            public void onResponse(Call<UserSchema> call, Response<UserSchema> response) {
                progressBar.setVisibility(View.INVISIBLE);
                UserSchema userSchema = response.body();
                if (userSchema.getStatus().equals("success")){
                    initViewElementsWithUserInfo(userSchema);
                }else{
                    Toast.makeText(getContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSchema> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewElementsWithUserInfo(UserSchema userSchema) {
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(mContext)
                .asBitmap()
                .load(userSchema.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(displayImageView);
        userName.setText(userSchema.getFirstName()+" "+userSchema.getLastName());
        progressBar.setVisibility(View.GONE);
    }
    private void clearAllTheSharedPreferencesValues() {
        final SharedPreferences preferences = getContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        preferences.edit().remove("userId").commit();
        preferences.edit().remove("userValidity").commit();
        preferences.edit().remove("userFirstName").commit();
        preferences.edit().remove("userLastName").commit();
        preferences.edit().remove("userEmail").commit();
        preferences.edit().remove("userRole").commit();
        preferences.edit().remove("userToken").commit();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}
