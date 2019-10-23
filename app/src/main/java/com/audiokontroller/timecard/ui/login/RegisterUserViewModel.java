package com.audiokontroller.timecard.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.audiokontroller.timecard.data.UserRepository;
import com.audiokontroller.timecard.data.model.User;

public class RegisterUserViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private UserRepository userRepository;
    private LoginViewModel loginViewModel;

    private User newUser;

    RegisterUserViewModel(UserRepository userRepository, LoginViewModel loginViewModel){
        this.loginViewModel = loginViewModel;
        this.userRepository = userRepository;
    }

    public void saveNewUser(@NonNull User user){
        userRepository.update(user);
        this.newUser = user;
    }

    public void loginNewUser(@NonNull User user){
        loginViewModel.login();
    }

}
