package com.example.meetfriends.etkinlikFragmentlar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.meetfriends.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EtkinlikMain extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private String[] titles = new String[]{"Etkinlik", "Detaylar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlik_main);
        getSupportActionBar().hide();
        viewPager = findViewById(R.id.main_activity_view_pager);
        tabLayout = findViewById(R.id.main_activity_tab_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager,((tab, position) -> tab.setText(titles[position]))).attach();


    }
}