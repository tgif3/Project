package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class StorageManager {
    private static final String KEY = "MANAGER";
    private static final String LAST_KEY = "LAST_KEY";
    private static StorageManager INSTANCE;
    private StorageManager() {}

    public static StorageManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StorageManager();
        }
        return INSTANCE;
    }

    public void save(int lastNumber, Context context) {
        setLastNumber(context, lastNumber);
    }

    public ArrayList<Integer> load(Context context, int lastLayoutNumber, boolean loadAll) {
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

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public int readLastNumber(Context context) {
        return getSharedPreferences(context).getInt(LAST_KEY, 0);
    }

    private static void setLastNumber(Context context, int number) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(LAST_KEY, number);
        editor.apply();
    }
}
