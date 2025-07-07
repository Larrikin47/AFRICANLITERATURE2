package com.example.africanliteraturelibraryapp.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;
import com.example.africanliteraturelibraryapp.data.dao.BookDao;
import com.example.africanliteraturelibraryapp.data.model.Book;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.util.Log;

/**
 * The Room database for the African Literature Library App.
 * Defines the database schema and serves as the main access point for the underlying connection.
 * This is a new file to bypass potential previous file corruption.
 */
@Database(entities = {Book.class}, version = 2, exportSchema = false)
public abstract class AppDatabaseFix extends RoomDatabase {

    public abstract BookDao bookDao();

    private static volatile AppDatabaseFix INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabaseFix getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseFix.class) {
                if (INSTANCE == null) {
                    Log.d("AppDatabaseFix", "Creating new AppDatabaseFix instance.");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), // <-- CORRECTED THIS LINE!
                                    AppDatabaseFix.class, "african_literature_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback to populate the database with initial data when it's first created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d("AppDatabaseFix", "Database onCreate callback triggered. Populating with initial data.");

            databaseWriteExecutor.execute(() -> {
                BookDao dao = INSTANCE.bookDao();
                dao.deleteAll();

                dao.insert(new Book("Things Fall Apart", "Chinua Achebe", "Fiction",
                        "A classic novel about pre-colonial Igbo life and the impact of colonialism, told through the life of Okonkwo.",
                        "https://images-na.ssl-images-amazon.com/images/I/51+uN9c+9yL._SX331_BO1,204,203,200_.jpg",
                        "https://www.gutenberg.org/files/61973/61973-h/61973-h.htm"));

                dao.insert(new Book("Half of a Yellow Sun", "Chimamanda Ngozi Adichie", "Historical Fiction",
                        "A novel about the Nigerian Civil War, exploring its impact through the lives of intertwined characters.",
                        "https://images-na.ssl-images-amazon.com/images/I/510R457n+jL._SX322_BO1,204,203,200_.jpg",
                        "https://en.wikipedia.org/wiki/Half_of_a_Yellow_Sun"));

                dao.insert(new Book("Purple Hibiscus", "Chimamanda Ngozi Adichie", "Coming-of-Age",
                        "A powerful story of a young girl's journey to freedom and self-discovery in post-colonial Nigeria.",
                        "https://images-na.ssl-images-amazon.com/images/I/412579051L._SX322_BO1,204,203,200_.jpg",
                        "https://en.wikipedia.org/wiki/Purple_Hibiscus"));

                dao.insert(new Book("Weep Not, Child", "Ngũgĩ wa Thiong'o", "Fiction",
                        "Set during the Mau Mau Uprising in Kenya, this novel explores the impact of colonialism on a young boy's life and education.",
                        "https://images-na.ssl-images-amazon.com/images/I/416HhT776KL._SX331_BO1,204,203,200_.jpg",
                        "https://en.wikipedia.org/wiki/Weep_Not,_Child"));
            });
        }
    };
}
