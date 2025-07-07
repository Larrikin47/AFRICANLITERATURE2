package com.example.africanliteraturelibraryapp.layouts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * A simple pager adapter that represents 2 TabFragment objects, in
 * sequence.
 */
public class TabPagerAdapter extends FragmentStateAdapter {

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a new Fragment based on the position
        switch (position) {
            case 0:
                return new TabFragment1();
            case 1:
                return new TabFragment2();
            default:
                return new TabFragment1(); // Fallback
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs/fragments
    }
}
