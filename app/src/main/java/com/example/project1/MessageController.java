package com.example.project1;

import android.content.Context;

import java.util.ArrayList;

public class MessageController {
    private Context context;
    private NotificationCenter notificationCenter;
    private ConnectionManager connectionManager;
    private StorageManager storageManager;
    private ArrayList<Integer> data;

    private ArrayList<Integer> messages;

    private static MessageController INSTANCE;

    private MessageController(Context context) {
        this.context = context;
        storageManager = StorageManager.getInstance();
        connectionManager = ConnectionManager.getInstance();
        notificationCenter = NotificationCenter.getInstance();
        data = new ArrayList<>();
    }

    public static MessageController getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new MessageController(context);
        }
        return INSTANCE;
    }

    public void fetch(boolean fromCache) {
        if (fromCache) {
            Thread storage = new Thread(() -> {
                messages = storageManager.load(context, getLastItem(), false);
                data.addAll(messages);
                notificationCenter.data_loaded(data);
            }, "storage");
            storage.start();
        }
        else {
            Thread cloud = new Thread(() -> {
                messages = connectionManager.load(storageManager.readLastNumber(context));
                data = storageManager.load(context, 0, true);
                data.addAll(messages);
                notificationCenter.data_loaded(data);
                storageManager.save(messages.get(messages.size() - 1), context);
            }, "cloud");
            cloud.start();
        }
    }

    private int getLastItem() {
        if (data.isEmpty())
            return 0;
        else
            return data.get(data.size() - 1);
    }

    public void clear() {
        data = new ArrayList<>();
        notificationCenter.data_loaded(data);
    }
}
