package com.creativeitem.academy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.creativeitem.academy.JSONSchemas.StatusSchema;
import com.creativeitem.academy.JSONSchemas.UserSchema;
import com.creativeitem.academy.Network.Api;
import com.creativeitem.academy.R;
import com.creativeitem.academy.Utils.Helpers;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText currentPassword, newPassword, confirmPassword;
    Button updateButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();
        initProgressBar();
    }

    private void init() {
        currentPassword = findViewById(R.id.currentPasswordEditText);
        newPassword = findViewById(R.id.newPasswordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        updateButton = findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn()){
                    updatePassword();
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "Log In First", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePassword() {
        SharedPreferences preferences = this.getSharedPreferences(Helpers.SHARED_PREF, 0);
        String authToken = preferences.getString("userToken", "loggedOut");
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);
        Call<StatusSchema> call = api.updatePassword(authToken, currentPassword.getText(), newPassword.getText(), confirmPassword.getText());
        call.enqueue(new Callback<StatusSchema>() {
            @Override
            public void onResponse(Call<StatusSchema> call, Response<StatusSchema> response) {
                progressBar.setVisibility(View.INVISIBLE);
                StatusSchema statusSchema = response.body();
                if (statusSchema.getStatus().equals("success")){
                    Toast.makeText(ChangePasswordActivity.this, "Password Has Been Updated Successfully", Toast.LENGTH_SHORT).show();
                    currentPassword.getText().clear();
                    newPassword.getText().clear();
                    confirmPassword.getText().clear();
                }else{
                    Toast.makeText(ChangePasswordActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusSchema> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ChangePasswordActivity.this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Initialize the progress bar
    private void initProgressBar() {
        progressBar = findViewById(R.id.progressBar);
        Sprite circularLoading = new Circle();
        progressBar.setIndeterminateDrawable(circularLoading);
    }


    private boolean isLoggedIn() {
        SharedPreferences preferences = this.getSharedPreferences(Helpers.SHARED_PREF, 0);
        int userValidity = preferences.getInt("userValidity", 0);
        if (userValidity == 1) {
            return true;
        }else{
            return false;
        }
    }

    public void handleBackButton(View view) {
        ChangePasswordActivity.super.onBackPressed();
    }
}
