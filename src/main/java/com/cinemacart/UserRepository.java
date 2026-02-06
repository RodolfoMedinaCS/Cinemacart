package com.cinemacart;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.QueryDocumentSnapshot;



public class UserRepository {

    private final Firestore db;

    public UserRepository(){
        this.db = FirestoreClient.getFirestore();
    }

}

//Save user to Firestore database

