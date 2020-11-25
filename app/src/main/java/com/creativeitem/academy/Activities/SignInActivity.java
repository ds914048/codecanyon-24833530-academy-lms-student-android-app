package com.creativeitem.academy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.creativeitem.academy.JSONSchemas.SystemSettings;
import com.creativeitem.academy.JSONSchemas.UserSchema;
import com.creativeitem.academy.Models.User;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    EditText emailAddressForLogin, passwordForLogin;
    ImageView applicationLogo;
    TextView loginTitle;

    private ProgressBar progressBar;
    private User mUser;
    private static final String TAG = "SignInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        init();
        initProgressBar();
        getSystemSettings();
    }

    private void init() {
        emailAddressForLogin = findViewById(R.id.emailAddressForLogin);
        passwordForLogin = findViewById(R.id.passwordForLogin);
        applicationLogo = findViewById(R.id.applicationLogo);
        loginTitle = findViewById(R.id.loginTitle);
    }
    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }

    private void getSystemSettings() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<SystemSettings> call = api.getSystemSettings();
        call.enqueue(new Callback<SystemSettings>() {
            @Override
            public void onResponse(Call<SystemSettings> call, Response<SystemSettings> response) {
                SystemSettings systemSettings = response.body();
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(systemSettings.getFavicon())
                        .into(applicationLogo);
                loginTitle.setText(systemSettings.getSystemName()+" Login");
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SystemSettings> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignInActivity.this, "Failed to fetch system data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void signUp(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.BASE_URL_PREFIX+"home/login"));
        startActivity(browserIntent);
    }

    public void logIn(View view) {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<UserSchema> call = api.getUserDetails(emailAddressForLogin.getText(), passwordForLogin.getText());
        call.enqueue(new Callback<UserSchema>() {
            @Override
            public void onResponse(Call<UserSchema> call, Response<UserSchema> response) {
                UserSchema userSchema = response.body();
                if (userSchema.getValidity() == 0) {
                    Toast.makeText(SignInActivity.this, "Wrong Login Credentials", Toast.LENGTH_SHORT).show();
                }else{
                    saveUserDataOnSharedPreference(userSchema);
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<UserSchema> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void saveUserDataOnSharedPreference(UserSchema userSchema) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Helpers.SHARED_PREF, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userId", userSchema.getUserId());
        editor.putInt("userValidity", userSchema.getValidity());
        editor.putString("userFirstName", userSchema.getFirstName());
        editor.putString("userLastName", userSchema.getLastName());
        editor.putString("userEmail", userSchema.getEmail());
        editor.putString("userRole", userSchema.getRole());
        editor.putString("userToken", userSchema.getToken());
        editor.commit();
    }
}
