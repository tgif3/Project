package com.example.project1;

import java.util.ArrayList;

public class StorageManager {
    public void Save(int lastNumber) {
        // TODO create new thread named "storage"

    }

    public ArrayList<Integer> Load() {
        // TODO create new thread named "storage"
        int lastNumber = readLastNumber();

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            result.add(lastNumber + i);
        }

        return result;
    }

    private int readLastNumber() {
        return 10;
    }
}
