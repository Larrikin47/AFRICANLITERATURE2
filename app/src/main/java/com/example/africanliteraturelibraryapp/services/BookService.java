package com.example.africanliteraturelibraryapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * A basic Service for handling background tasks that don't require a UI.
 * This service runs on the main thread by default. For long-running tasks,
 * consider using an AsyncTask (deprecated in favor of Kotlin Coroutines or Java's ExecutorService)
 * or a separate Thread/ExecutorService within the service to avoid blocking the UI thread.
 */
public class BookService extends Service {

    private static final String TAG = "BookService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "BookService: onCreate()");
        // Display a short-lived message when the service is created.
        Toast.makeText(this, "BookService Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "BookService: onStartCommand()");
        // Display a short-lived message when the service starts a command.
        Toast.makeText(this, "BookService Started", Toast.LENGTH_SHORT).show();

        // Retrieve data passed with the intent, if any.
        String taskData = intent != null ? intent.getStringExtra("task_data") : "No task data";
        Log.d(TAG, "BookService received task: " + taskData);

        // Simulate a background task.
        // IMPORTANT: Services run on the main thread by default. For actual long-running operations
        // (like network requests or heavy computations), you MUST offload them to a separate thread
        // to prevent Application Not Responding (ANR) errors.
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Performing background task: " + taskData);
                try {
                    Thread.sleep(3000); // Simulate work for 3 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    Log.e(TAG, "BookService task interrupted: " + e.getMessage());
                }
                Log.d(TAG, "Background task finished: " + taskData);

                // Stop the service once the task is done.
                // Using stopSelf(startId) ensures that the service stops only when
                // the last start command it received has been processed.
                stopSelf(startId);
            }
        }).start();


        // Return START_NOT_STICKY: If the system kills the service, it will not be re-created
        // unless there are pending start Intents to deliver. This is suitable for services
        // that perform one-off operations.
        // Other options:
        // START_STICKY: Service will be restarted if killed, onStartCommand will be called with a null intent.
        // START_REDELIVER_INTENT: Service will be restarted, onStartCommand will be called with the last intent.
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BookService: onDestroy()");
        // Display a short-lived message when the service is destroyed.
        Toast.makeText(this, "BookService Destroyed", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // This service is not designed to be bound by other components,
        // so we return null. If you needed to allow binding (e.g., for IPC),
        // you would return an implementation of IBinder here.
        return null;
    }
}
