package com.example.africanliteraturelibraryapp.layouts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.africanliteraturelibraryapp.R;

public class RelativeLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_relative); // Correctly references layout_relative.xml
    }
}
