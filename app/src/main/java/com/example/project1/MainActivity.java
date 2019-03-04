package com.example.project1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.interfaces.RepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements RepositoryObserver {
    private int last;
    private Context context;
    private Handler handler;
    private LinearLayout linearLayout;
    private static final int DELAY = 100;

    private Subject notificationCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        last = 0;
        context = MainActivity.this;
        handler = new Handler();

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.registerObserver(this);

        // initialize
        initializeUI();
    }

    private void initializeUI() {
        linearLayout = findViewById(R.id.linear_layout);

        Button clearBtn = findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(v -> {
            last = 0;
            linearLayout.removeAllViews();
        });

        Button refreshBtn = findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(v -> {
            ArrayList<Integer> items = storageManager.Load(context);
            linearLayout.removeAllViews();
            if (last < mLast) {
                for (int i = last + 1; i <= last + 10; i++) {
                    TextView textView = new TextView(context);
                    textView.setTextSize(25);
                    textView.setText(i + " ");

                    linearLayout.addView(textView);
                }
            }
            last += 10;
        });

        Button getBtn = findViewById(R.id.get_btn);
        getBtn.setOnClickListener(v -> {
            handler.postDelayed(() -> {
                int last = MemoryManager.getLastNumber(context);
                HashSet<String> strings = new HashSet<>();

                for (int i = last + 1; i < last + 11; i++) {
                    strings.add(Integer.toString(i));
                }

                MemoryManager.addNumbers(context, strings);
                MemoryManager.setLastNumber(context, last + 10);
                updateLinearLayout();
            }, DELAY);
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
            textView.setTextSize(25);
            textView.setText(num + " ");

            linearLayout.addView(textView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationCenter.removeObserver(this);
    }

    @Override
    public void updateData() {
        updateLinearLayout();
    }
}
