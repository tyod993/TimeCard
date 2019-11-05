package com.audiokontroller.timecard.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.audiokontroller.timecard.R;

public class RegisterUserFragment extends Fragment {

    private LoginViewModel mViewModel;

    private Button mSignUpButton;
    private EditText mFNameInput;
    private EditText mLNameInput;
    private EditText mEmailInput;
    private EditText mPasswordInput;

    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSignUpButton = getActivity().findViewById(R.id.signUpButton);
        mFNameInput = getActivity().findViewById(R.id.firstNameInput);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_user_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    public void setViewModel(LoginViewModel viewModel){this.mViewModel = viewModel;}
}
