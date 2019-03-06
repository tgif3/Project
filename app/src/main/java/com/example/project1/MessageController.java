package com.example.project1;

import android.content.Context;

import java.util.ArrayList;

class MessageController {
    private StorageManager storageManager;
    private ConnectionManager connectionManager;
    private Context context;
    private ArrayList<Integer> data;

    MessageController(Context context) {
        this.context = context;
        storageManager = new StorageManager();
        connectionManager = new ConnectionManager();
        data = new ArrayList<>();
    }

    void fetch(boolean fromCache) {
        if (fromCache) {
            MainActivity.handler.post(() -> {
                data.addAll(storageManager.Load(context, getLastItem(), false));
                refreshLayout();
            });
        } else {
            MainActivity.handler.postDelayed(() -> {
                ArrayList<Integer> networkNumbers = connectionManager.Load(storageManager.readLastNumber(context));
                storageManager.Save(networkNumbers.get(networkNumbers.size() - 1), context);
                data.addAll(storageManager.Load(context, getLastItem(), true));

                refreshLayout();
            }, 100);
        }
    }

    private void refreshLayout() {
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.data_loaded(data);
    }

    private int getLastItem() {
        if (data.isEmpty())
            return 0;
        else
            return data.get(data.size() - 1);
    }

    void clear() {
        data = new ArrayList<>();

        refreshLayout();
    }
}
