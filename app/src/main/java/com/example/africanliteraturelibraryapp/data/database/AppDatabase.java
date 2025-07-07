package com.example.africanliteraturelibraryapp.data.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.africanliteraturelibraryapp.data.dao.BookDao;
import com.example.africanliteraturelibraryapp.data.model.Book;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room database class for the African Literature Library App.
 * Defines the database configuration and provides access to DAOs.
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    public abstract BookDao bookDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // ExecutorService for background database operations
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Gets the singleton instance of the database.
     *
     * @param context The application context.
     * @return The singleton AppDatabase instance.
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) { // Synchronized to prevent multiple threads creating instances
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "african_literature_database") // Database name
                            .addCallback(sRoomDatabaseCallback) // Add callback for initial data population
                            .build();
                    Log.d(TAG, "AppDatabase instance created.");
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback for database creation and opening.
     * Used to pre-populate the database with initial data.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "AppDatabase onCreate callback: Populating initial data.");

            // Populate the database in a background thread
            databaseWriteExecutor.execute(() -> {
                BookDao dao = INSTANCE.bookDao();
                // Clear existing data (optional, useful for fresh installs)
                dao.deleteAllBooks();

                // Insert initial mock data
                dao.insertBooks(
                        new Book("Things Fall Apart", "Chinua Achebe", "Fiction",
                                "A classic novel about pre-colonial Igbo life and the impact of colonialism.",
                                "https://placehold.co/200x300/4CAF50/FFFFFF?text=TFA",
                                "https://en.wikipedia.org/wiki/Things_Fall_Apart"),
                        new Book("Half of a Yellow Sun", "Chimamanda Ngozi Adichie", "Historical Fiction",
                                "Set during the Nigerian Civil War, exploring its effects on the lives of ordinary people.",
                                "https://placehold.co/200x300/FFC107/000000?text=HOAYS",
                                "https://en.wikipedia.org/wiki/Half_of_a_Yellow_Sun"),
                        new Book("The Joys of Motherhood", "Buchi Emecheta", "Fiction",
                                "Explores the challenges faced by women in patriarchal Nigerian society.",
                                "https://placehold.co/200x300/388E3C/FFFFFF?text=JOM",
                                "https://en.wikipedia.org/wiki/The_Joys_of_Motherhood"),
                        new Book("Purple Hibiscus", "Chimamanda Ngozi Adichie", "Fiction",
                                "A story of a young girl's struggle against religious fanaticism and political unrest in Nigeria.",
                                "https://placehold.co/200x300/8BC34A/000000?text=PH",
                                "https://en.wikipedia.org/wiki/Purple_Hibiscus"),
                        new Book("Weep Not, Child", "Ngũgĩ wa Thiong'o", "Fiction",
                                "The story of a young man's struggle for education amidst the Mau Mau Uprising in Kenya.",
                                "https://placehold.co/200x300/009688/FFFFFF?text=WNC",
                                "https://en.wikipedia.org/wiki/Weep_Not,_Child")
                );
                Log.d(TAG, "Initial book data populated successfully.");
            });
        }
    };
}
