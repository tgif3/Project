package com.example.project1;

import java.util.ArrayList;

class ConnectionManager {

    ArrayList<Integer> Load(int lastNumber) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            result.add(lastNumber + i);
        }

        return result;
    }
}
