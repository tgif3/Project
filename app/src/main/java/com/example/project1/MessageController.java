package com.example.project1;

import android.content.Context;

import java.util.ArrayList;

public class MessageController {
    private StorageManager storageManager;
    private ConnectionManager connectionManager;
    private Context context;
    private ArrayList<Integer> data;

    public MessageController(Context context) {
        this.context = context;
        storageManager = new StorageManager();
        connectionManager = new ConnectionManager();
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        data = new ArrayList<>();
    }

    public void fetch(boolean fromCache) {
        if (fromCache) {
            new Thread(() -> {
                data.addAll(storageManager.Load(context, getLastItem(), false));
                refreshLayout();
            }, "storage");
        }
        else {
            new Thread(() -> {
                try {
                    ArrayList<Integer> networkNumbers = connectionManager.Load(storageManager.readLastNumber(context));
                    storageManager.Save(networkNumbers.get(networkNumbers.size() - 1), context);
                    data.addAll(storageManager.Load(context, getLastItem(), true));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                refreshLayout();
            }, "cloud");
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

    public void clear() {
        data = new ArrayList<>();

        refreshLayout();
    }
}
