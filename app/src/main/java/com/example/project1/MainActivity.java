package com.example.project1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project1.interfaces.RepositoryObserver;
import com.example.project1.interfaces.Subject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RepositoryObserver {
    private Context context;
    private LinearLayout linearLayout;
    private MessageController messageController;

    private Subject notificationCenter;

    static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        notificationCenter = NotificationCenter.getInstance();
        notificationCenter.registerObserver(this);

        messageController = new MessageController(context);


        // initialize UI
        linearLayout = findViewById(R.id.linear_layout);

        findViewById(R.id.clear_btn).setOnClickListener(v -> {
            messageController.clear();
        });

        findViewById(R.id.refresh_btn).setOnClickListener(v -> {
            messageController.fetch(true);
        });

        findViewById(R.id.get_btn).setOnClickListener(v -> {
            messageController.fetch(false);
        });
    }

    private void updateLinearLayout(ArrayList<Integer> arrayList) {
        linearLayout.removeAllViews();
        for (Integer num : arrayList) {
            TextView textView = new TextView(context);
            textView.setTextSize(25);
            textView.setText(num + "");

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
