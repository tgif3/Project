package com.example.project1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class StorageManager {
    public void Save(int lastNumber, Context context) {
        // TODO create new thread named "storage"
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(lastNumber);
            outputStreamWriter.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public ArrayList<Integer> Load(Context context) {
        // TODO create new thread named "storage"
        int lastNumber = readLastNumber(context);

        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            result.add(lastNumber + i);
        }

        return result;
    }

    private int readLastNumber(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(context.getFilesDir(), "config.txt")));
            while ((line = reader.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
            Log.e("Exception", e.toString());
        } catch (IOException e) {
            Log.e("Exception", e.toString());
        }

        return Integer.parseInt(stringBuilder.toString());
    }
}
