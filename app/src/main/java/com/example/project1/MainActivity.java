package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        // initialize
        initializeUI();
    }

    private void initializeUI() {
        linearLayout = findViewById(R.id.linear_layout);
        updateLinearLayout();


        Button clearBtn = findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(v -> {
            MemoryManager.setNumbers(context, new HashSet<>());
            updateLinearLayout();
        });

        Button refreshBtn = findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(v -> {
            MemoryManager.initialize(context);
            updateLinearLayout();
        });

        Button getBtn = findViewById(R.id.get_btn);
        getBtn.setOnClickListener(v -> {
            int last = MemoryManager.getLastNumber(context);
            HashSet<String> strings = new HashSet<>();

            for (int i = last + 1; i < last + 11; i++) {
                strings.add(Integer.toString(i));
            }

            MemoryManager.addNumbers(context, strings);
            MemoryManager.setLastNumber(context, last + 10);
            updateLinearLayout();
        });
    }

    private void updateLinearLayout() {
        HashSet<String> strings = MemoryManager.getNumbers(context);
        Set<Integer> strings1 = new TreeSet<>();
        for (String string : strings) {
            strings1.add(Integer.parseInt(string));
        }

        linearLayout.removeAllViews();
        for (int num : strings1) {
            TextView textView = new TextView(context);
            textView.setText(num + " ");

            linearLayout.addView(textView);
        }
    }
}
