package com.example.africanliteraturelibraryapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * BroadcastReceiver to handle the "sync complete" broadcast from BookSyncService.
 * Can also be configured to listen for system broadcasts like BOOT_COMPLETED (if enabled in Manifest).
 */
public class BookBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BookBroadcastReceiver";
    // Define the custom action string for sync complete broadcast
    public static final String ACTION_SYNC_COMPLETE = "com.example.africanliteraturelibraryapp.SYNC_COMPLETE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_SYNC_COMPLETE.equals(action)) {
                // Handle the custom "sync complete" broadcast
                boolean success = intent.getBooleanExtra("sync_success", false);
                String message = intent.getStringExtra("sync_message");
                Log.d(TAG, "Sync Complete Broadcast: Success=" + success + ", Message=" + message);
                Toast.makeText(context, "Book Sync Complete: " + message, Toast.LENGTH_LONG).show();

                // TODO: Implement actual UI update logic here.
                // For example, if MainActivity is active, you might send a local broadcast
                // or update a shared ViewModel/repository that MainActivity observes.
                // Or, if the app is in the background, you might show a notification.

            } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                // This block will only execute if you uncommented the BOOT_COMPLETED intent-filter
                // in AndroidManifest.xml and added the RECEIVE_BOOT_COMPLETED permission.
                Log.d(TAG, "Device booted, potentially starting background sync or service.");
                Toast.makeText(context, "App: Device Booted!", Toast.LENGTH_LONG).show();
                // TODO: Potentially start BookSyncService here if background sync is needed on boot,
                // but be mindful of Android 8.0+ background execution limits.
            }
        }
    }
}
