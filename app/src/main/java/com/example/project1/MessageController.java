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
        data = new ArrayList<>();
    }

    public void fetch(boolean fromCache) throws InterruptedException {
        if (fromCache)
            data.addAll(storageManager.Load(context));
        else
        {
            data.addAll(connectionManager.Load(storageManager.readLastNumber(context)));
            storageManager.Save(getLastItem(), context);
        }


    }

    private int getLastItem()
    {
        return data.get(data.size() - 1);
    }
}
