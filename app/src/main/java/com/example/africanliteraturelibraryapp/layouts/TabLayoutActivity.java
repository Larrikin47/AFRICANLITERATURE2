package com.example.africanliteraturelibraryapp.layouts;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.example.africanliteraturelibraryapp.R;

public class TabLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tab); // Correctly references layout_tab.xml

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Create an adapter for the ViewPager2
        TabPagerAdapter adapter = new TabPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Link TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Tab 1: Popular");
                            break;
                        case 1:
                            tab.setText("Tab 2: New Arrivals");
                            break;
                    }
                }).attach();
    }
}
