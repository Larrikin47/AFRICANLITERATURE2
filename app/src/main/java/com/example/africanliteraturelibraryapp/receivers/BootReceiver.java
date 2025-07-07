package com.example.africanliteraturelibraryapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.africanliteraturelibraryapp.services.BookSyncService; // Assuming you have a BookSyncService

/**
 * BroadcastReceiver that listens for the BOOT_COMPLETED action.
 * This allows the app to perform actions (e.g., start a service) after the device reboots.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "Device booted. Starting BookSyncService...");
            Toast.makeText(context, "African Literature App: Device Booted!", Toast.LENGTH_LONG).show();

            // Example: Start a service to sync books or perform other background tasks
            // Ensure BookSyncService exists and is properly declared in AndroidManifest.xml
            Intent serviceIntent = new Intent(context, BookSyncService.class);
            context.startService(serviceIntent);

            // You might also want to schedule periodic tasks here using WorkManager
            // For example:
            // WorkManager.getInstance(context).enqueue(yourPeriodicWorkRequest);
        }
    }
}
