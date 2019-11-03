package com.audiokontroller.timecard.ui.login;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.audiokontroller.timecard.data.LoginDataSource;

/**
 * ViewModel provider factory to instantiate RegisterUserViewModel.
 * Required given RegisterUserViewModel has a non-empty constructor
 */
public class RegisterUserViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterUserViewModel.class)) {
            return (T) new RegisterUserViewModel(new LoginDataSource());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

