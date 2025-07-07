package com.example.africanliteraturelibraryapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * BroadcastReceiver to handle custom broadcasts, e.g., when a new book is added.
 * This receiver is declared as `exported="false"` in the Manifest, meaning it can
 * only receive broadcasts from within this application.
 */
public class BookReceiver extends BroadcastReceiver {

    private static final String TAG = "BookReceiver";
    // Define a custom action for this receiver, matching the one in AndroidManifest.xml
    public static final String ACTION_NEW_BOOK_ADDED = "com.example.africanliteraturelibraryapp.NEW_BOOK_ADDED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            Log.d(TAG, "BookReceiver received action: " + action);

            if (ACTION_NEW_BOOK_ADDED.equals(action)) {
                // Extract data from the intent, if any
                String bookTitle = intent.getStringExtra("book_title");
                String message = "New book added: " + (bookTitle != null ? bookTitle : "Unknown Title");
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.i(TAG, message);

                // TODO: Here you could trigger a refresh of the RecyclerView in MainActivity
                // For example, by sending a local broadcast or updating a ViewModel.
            }
            // Add more conditions for other custom actions if needed
        }
    }
}
