package com.example.africanliteraturelibraryapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.africanliteraturelibraryapp.receivers.BookBroadcastReceiver; // Import your receiver class

public class BookSyncService extends Service {

    private static final String TAG = "BookSyncService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "BookSyncService: onCreate()");
        Toast.makeText(this, "BookSyncService Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "BookSyncService: onStartCommand() - Starting sync...");
        Toast.makeText(this, "Book Sync Started...", Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "Simulating data synchronization...");
                    Thread.sleep(5000); // Simulate 5 seconds of sync time
                    Log.d(TAG, "Data synchronization complete.");

                    // --- CHANGE STARTS HERE ---
                    // Create an EXPLICIT Intent targeting your BookBroadcastReceiver
                    Intent broadcastIntent = new Intent(BookBroadcastReceiver.ACTION_SYNC_COMPLETE);
                    broadcastIntent.setClass(BookSyncService.this, BookBroadcastReceiver.class); // Explicitly set the target component

                    broadcastIntent.putExtra("sync_success", true);
                    broadcastIntent.putExtra("sync_message", "Books updated successfully!");
                    sendBroadcast(broadcastIntent); // Now this is an explicit broadcast
                    // --- CHANGE ENDS HERE ---

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Log.e(TAG, "Sync interrupted: " + e.getMessage());

                    // --- CHANGE STARTS HERE ---
                    Intent broadcastIntent = new Intent(BookBroadcastReceiver.ACTION_SYNC_COMPLETE);
                    broadcastIntent.setClass(BookSyncService.this, BookBroadcastReceiver.class); // Explicitly set the target component
                    // --- CHANGE ENDS HERE ---

                    broadcastIntent.putExtra("sync_success", false);
                    broadcastIntent.putExtra("sync_message", "Sync failed: " + e.getMessage());
                    sendBroadcast(broadcastIntent);

                } finally {
                    stopSelf(startId);
                }
            }
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BookSyncService: onDestroy()");
        Toast.makeText(this, "BookSyncService Destroyed", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}