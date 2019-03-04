package com.example.project1.interfaces;

import java.util.ArrayList;

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void data_loaded(ArrayList<Integer> arrayList);
}
