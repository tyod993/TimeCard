package com.audiokontroller.timecard.data.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.Executor;

public class FireUserProfileUpdate {

    private static final String TAG = FireUserProfileUpdate.class.getSimpleName();

    private final FirebaseUser currentUser;

    public FireUserProfileUpdate(FirebaseUser firebaseUser){currentUser = firebaseUser;}

    public void updateUserDisplayName(@NonNull String newDisplayName) {
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newDisplayName)
                    .build();
            currentUser.updateProfile(request)
                    .addOnCompleteListener( task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, ".displayNameChange.success");
                            }
                    });
    }

    public void updateUserProfilePicture(@NonNull Uri newPhoto){
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(newPhoto)
                .build();
        currentUser.updateProfile(request)
                .addOnCompleteListener((task-> {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "profilePictureChange.successful");
                        }
                }));
    }

    public void updateUserEmail(@NonNull String newEmail){
        currentUser.updateEmail(newEmail);
    }

    public void updateUserPassword(@NonNull String newPassword){
        currentUser.updatePassword(newPassword);
    }
}
