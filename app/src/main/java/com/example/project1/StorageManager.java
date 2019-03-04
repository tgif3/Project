package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class StorageManager {
    private static final String KEY = "MANAGER";
    private static final String LAST_KEY = "LAST_KEY";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    private static void setLastNumber(Context context, int number) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(LAST_KEY, number);
        editor.apply();
    }

    public void Save(int lastNumber, Context context) {
        // TODO create new thread named "storage"

//        new Thread(() -> {
        setLastNumber(context, lastNumber);
//        }, "storage")
    }

    public ArrayList<Integer> Load(Context context, int lastLayoutNumber, boolean loadAll) {
        // TODO create new thread named "storage"
        int lastNumber = readLastNumber(context);
        ArrayList<Integer> result = new ArrayList<>();

        if (lastLayoutNumber < lastNumber) {
            if (loadAll) {
                int currentItem = lastLayoutNumber + 1;
                while (currentItem <= lastNumber) {
                    result.add(currentItem);
                    currentItem++;
                }
            } else {
                for (int i = 1; i <= 10; i++)
                    result.add(lastLayoutNumber + i);
            }
        }

        return result;
    }

    public int readLastNumber(Context context) {
        return getSharedPreferences(context).getInt(LAST_KEY, 0);
    }
}
