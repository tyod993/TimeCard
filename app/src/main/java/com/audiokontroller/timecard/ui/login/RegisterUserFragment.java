package com.audiokontroller.timecard.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.audiokontroller.timecard.R;
import com.audiokontroller.timecard.authentication.Result;
import com.audiokontroller.timecard.ui.mainmenu.MainMenuActivity;
import com.google.firebase.auth.FirebaseUser;

//TODO: Handle state changes

public class RegisterUserFragment extends Fragment {

    private final String TAG = RegisterUserFragment.class.getSimpleName();

    private LoginViewModel loginViewModel;

    private Button mSignUpButton;
    private EditText mEmailInput;
    private EditText mPasswordInput;

    // This constructor is the reason that the loading of the fragment is taking so long
    public  RegisterUserFragment(LoginViewModel loginViewModel){this.loginViewModel = loginViewModel;}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.register_user_fragment, container, false);
        mSignUpButton = rootView.findViewById(R.id.register_user_save_button);
        mEmailInput = rootView.findViewById(R.id.emailInput);
        mPasswordInput = rootView.findViewById(R.id.passwordInput);

        mSignUpButton.setOnClickListener(view ->{

            //There really needs to be a progress bar visual here for the time waiting on the auth.
            loginViewModel.registrationDataChanged(mEmailInput.getText().toString(), mPasswordInput.getText().toString());

            if(loginViewModel.getLoginFormState().getValue().isDataValid()) {

                loginViewModel.registerNewUser(
                        mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString())
                        .observe(getViewLifecycleOwner(), authResult ->{

                    if (authResult instanceof Result.Success) {
                        Intent intent = new Intent(getActivity(), MainMenuActivity.class);
                        FirebaseUser firebaseUser = (FirebaseUser) ((Result.Success) authResult).getData();
                        intent.putExtra(getResources().getString(R.string.first_login_key), true);
                        intent.putExtra(getResources().getString(R.string.user_id_key), firebaseUser.getUid());
                        intent.putExtra(getResources().getString(R.string.user_email_key), firebaseUser.getEmail());
                        intent.putExtra(getResources().getString(R.string.user_display_name_key), firebaseUser.getDisplayName());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                        Log.e(TAG, "error logging in." + authResult.toString());
                    }

                });
            }else {

                if(mEmailInput.getText().toString().equalsIgnoreCase("")){
                    mEmailInput.setError(getResources().getString(R.string.email_user_input_error));
                }
                if (mPasswordInput.getText() == null){
                    mPasswordInput.setError(getResources().getString(R.string.password_user_input_error));
                }

            }
        });

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState-> {
            if (loginFormState == null || loginFormState.isDataValid()) {
                return;
            }
            if (loginFormState.getUsernameError() != null) {
                mEmailInput.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                mPasswordInput.setError(getString(loginFormState.getPasswordError()));
            }
        });

        return rootView;
    }

}
