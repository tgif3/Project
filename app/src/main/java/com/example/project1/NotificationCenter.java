package com.example.project1;
import android.os.Handler;

import com.example.project1.interfaces.RepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;


public class NotificationCenter implements Subject {
    private static NotificationCenter INSTANCE = null;

    private ArrayList<RepositoryObserver> observers;

    private NotificationCenter() {
        observers = new ArrayList<>();
//        getNewDataFromRemote();
    }

//    // Simulate network
//    private void getNewDataFromRemote() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setUserData();
//            }
//        }, 10000);
//    }

    // Creates a Singleton of the class
    public static NotificationCenter getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NotificationCenter();
        }
        return INSTANCE;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if(!observers.contains(repositoryObserver)) {
            observers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        observers.remove(repositoryObserver);
    }

    @Override
    public void data_loaded() {
        for (RepositoryObserver observer: observers) {
            observer.updateData();
        }
    }

//    public void setUserData() {
//        dataLoaded();
//    }
}
