package com.audiokontroller.timecard.data;

import androidx.annotation.NonNull;

import com.audiokontroller.timecard.data.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserDataSourceManager {

    private UserDataSource dataSource;

    //This should manage all of the updates and initialization of the database 
    public UserDataSourceManager(@NonNull UserDataSource dataSource){
        this.dataSource = dataSource;
    }

    public void populateRoomFromFirebase(){
        FirebaseUser user = dataSource.firebaseUser;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User localUser = new User(user.getUid(), user.getEmail(), null, null);
        try {
            Map<String, Object> userDataMap =
            db.collection(Collections.USERS)
                    .whereEqualTo("userID", user.getUid())
                    .get()
                    .getResult()
                    .getDocuments()
                    .get(0)
                    .getData();

        } catch(NullPointerException e){
            //TODO handle null pointer exception if it is a new user with no data in firestore
        }
    }
}
