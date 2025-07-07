package com.example.africanliteraturelibraryapp.layouts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.africanliteraturelibraryapp.R;

public class FrameLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frame); // Correctly references layout_frame.xml
    }
}
