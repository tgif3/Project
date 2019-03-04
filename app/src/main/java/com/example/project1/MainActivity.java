package com.example.project1;

import android.content.Context;
import android.content.Intent;
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
    private MessageController messageController;
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

        messageController = new MessageController(context);

        // initialize
        initializeUI();
    }

    private void initializeUI() {
        linearLayout = findViewById(R.id.linear_layout);

        Button clearBtn = findViewById(R.id.clear_btn);
        clearBtn.setOnClickListener(v -> {
            messageController.clear();
        });

        Button refreshBtn = findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(v -> {
            try {
                messageController.fetch(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Button getBtn = findViewById(R.id.get_btn);
        getBtn.setOnClickListener(v -> {
            try {
                messageController.fetch(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateLinearLayout(ArrayList<Integer> arrayList) {
        linearLayout.removeAllViews();
        for (Integer num : arrayList) {
            TextView textView = new TextView(context);
            textView.setTextSize(25);
            textView.setText(num);

            linearLayout.addView(textView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificationCenter.removeObserver(this);
    }

    @Override
    public void updateData(ArrayList<Integer> arrayList) {
        updateLinearLayout(arrayList);
    }
}
