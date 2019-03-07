package com.example.project1;

import java.util.ArrayList;

public class ConnectionManager {
    private static ConnectionManager INSTANCE;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    public ArrayList<Integer> load(int lastNumber) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            result.add(lastNumber + i);
        }
        return result;
    }
}
