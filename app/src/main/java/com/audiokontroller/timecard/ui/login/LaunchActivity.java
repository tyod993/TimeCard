package com.audiokontroller.timecard.ui.login;

import android.app.Activity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.authentication.firebase.FirebaseAuthHandler;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuActivity;
import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.data.model.LoggedInUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LaunchActivity extends AppCompatActivity {

    private final String TAG = LaunchActivity.class.getSimpleName();

    private LoginViewModel loginViewModel;

    private EditText emailEditText;
    private EditText passwordEditText;
    private CardView loginButton;
    private ProgressBar loadingProgressBar;
    private FrameLayout fragmentContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //search the SharedPref


        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //Check if Firebase user is already logged in before any initialization.
        if (loginViewModel.isFirebaseUserLoggedIn()){
            updateUiWithUser();
        }
        //

        //UI Components
        fragmentContainer = findViewById(R.id.login_fragment_container);
        passwordEditText = findViewById(R.id.password_text);
        emailEditText = findViewById(R.id.username_text);
        loginButton = findViewById(R.id.login);
        TextView createAccountTextView = findViewById(R.id.createAccount);
        loadingProgressBar = findViewById(R.id.loading);
        //

        //Format validation
        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null|| !loginFormState.isDataValid()) {
                loginButton.setEnabled(false);
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                emailEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });
        //

        //TODO this all may need ot be deleted if the simpler architecture works.
        //Let's get down to business
        loginViewModel.getLoginResult().observe(this, loginResult ->{
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser();
                    finish();
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
            });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(view ->{
            if(loginViewModel.getLoginFormState().getValue() == null){
                loginViewModel.loginDataChanged(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }else {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
                loginViewModel.getAuthResult().observe(this, result -> {
                    if(result != null) {
                        if (result) {
                            updateUiWithUser();
                            finish();
                        } else {
                            Log.d(TAG, "There was a problem, result == null");
                            //TODO handle errors. AND FIX ALL LIVE DATA< YOU MADE A STUPID MISTAKE!!!!!!
                        }
                    } else {
                        Log.d(TAG,"authResult is == null");
                    }
                });
            }

        });

        createAccountTextView.setOnClickListener(view ->{
                launchRegisterUserFrag();
                fragmentContainer.isClickable();
                fragmentContainer.bringToFront();

        });
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome);
        Intent intent = new Intent(LaunchActivity.this, MainMenuActivity.class);
        intent.putExtra(getResources().getString(R.string.user_id_key), loginViewModel.getFirebaseUserID());
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void launchRegisterUserFrag(){
        RegisterUserFragment registerUserFragment = new RegisterUserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.login_fragment_container, registerUserFragment);
        transaction.commit();
    }
}
