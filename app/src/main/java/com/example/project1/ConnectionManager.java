package com.example.project1;

import java.util.ArrayList;

public class ConnectionManager {

    public ArrayList<Integer> Load(int lastNumber) throws InterruptedException {
        // TODO create new thread named "cloud"

        Thread.sleep(100);
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            result.add(lastNumber + i);
        }

        return result;
    }
}
