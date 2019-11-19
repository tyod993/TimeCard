package com.audiokontroller.timecard.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.audiokontroller.timecard.R;

public class RegisterUserFragment extends Fragment {

    private LoginViewModel loginViewModel;

    private CardView mSignUpButton;
    private EditText mFNameInput;
    private EditText mLNameInput;
    private EditText mEmailInput;
    private EditText mPasswordInput;
    private ProgressBar mLoadingProgressBar;

    public  RegisterUserFragment(LoginViewModel loginViewModel){this.loginViewModel = loginViewModel;}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSignUpButton = getActivity().findViewById(R.id.signUp_cardView);
        mFNameInput = getActivity().findViewById(R.id.firstNameInput);
        mLNameInput = getActivity().findViewById(R.id.lastNameInput);
        mEmailInput = getActivity().findViewById(R.id.emailInput);
        mPasswordInput = getActivity().findViewById(R.id.passwordInput);

        mLoadingProgressBar = getActivity().findViewById(R.id.signUpLoading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                mSignUpButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    mEmailInput.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    mPasswordInput.setError(getString(loginFormState.getPasswordError()));
                }
            }
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
                loginViewModel.loginDataChanged(mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString());
                loginViewModel.newUserDataChanged(mFNameInput.getText().toString(),
                        mLNameInput.getText().toString(), mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString());
            }
        };
        mEmailInput.addTextChangedListener(afterTextChangedListener);
        mPasswordInput.addTextChangedListener(afterTextChangedListener);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.registerNewUser();
                loginViewModel.login(getContext(),
                        mEmailInput.getText().toString(),
                        mPasswordInput.getText().toString());

            }
        });
    }

}
