package com.example.africanliteraturelibraryapp.activities.layoutviews;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import com.example.africanliteraturelibraryapp.R;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tab); // This links to your layout_tab.xml

        tabLayout = findViewById(R.id.tabLayout);

        // Add tabs programmatically (you could also define them in XML within TabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("Fiction"));
        tabLayout.addTab(tabLayout.newTab().setText("Poetry"));
        tabLayout.addTab(tabLayout.newTab().setText("Non-Fiction"));
        tabLayout.addTab(tabLayout.newTab().setText("Drama"));

        // Set a listener for tab selections
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection
                Toast.makeText(TabLayoutActivity.this, "Selected Category: " + tab.getText(), Toast.LENGTH_SHORT).show();
                // TODO: In a real app, you would update the content in the FrameLayout
                // (R.id.tabContentContainer) here, perhaps by swapping Fragments.
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Actions when a tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Actions when a selected tab is re-selected
            }
        });
    }
}
