package com.example.meetfriends.etkinlikFragmentlar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.meetfriends.etkinlik.EtkinlikFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

private String[] titles = new String[]{"Etkinlik", "Detaylar"};

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new EtkinlikFragment();
            case 1:
                return new Detail();
        }
        return new EtkinlikFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }











    /*
    public ViewPagerAdapter(@NonNull FragmentManager fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment;

        switch (position) {
            case 0:
                selectedFragment = EtkinlikFragment.newInstance();
                break;
            case 1:
                selectedFragment = Detail.newInstance();
                break;
            default:
                return null;
        }

        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence selectedTitle;

        switch (position) {
            case 0:
                selectedTitle = "Fitness Pictures";
                break;
            case 1:
                selectedTitle = "Details";
                break;
            default:
                return null;

        }
        return selectedTitle;
    }

*/
}