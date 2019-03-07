package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements RepositoryObserver {
    private Context context;
    private LinearLayout linearLayout;
    private MessageController messageController;

    private Subject notificationCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.registerObserver(this);

        messageController = MessageController.getInstance(context);

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
            messageController.fetch(true);
        });

        Button getBtn = findViewById(R.id.get_btn);
        getBtn.setOnClickListener(v -> {
            messageController.fetch(false);
        });
    }

    private void updateLinearLayout(ArrayList<Integer> arrayList) {
        runOnUiThread(() -> {
            linearLayout.removeAllViews();
            for (Integer num : arrayList) {
                TextView textView = new TextView(context);
                textView.setTextSize(30);
                textView.setTextColor(Color.rgb(0, 175, 0));
                textView.setText(num + "");
                linearLayout.addView(textView);
            }
        });
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
