package com.example.meetfriends.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.meetfriends.R;
import com.google.android.material.tabs.TabLayout;

public class infoTabLayoutActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tab_layout);

        tabLayout = findViewById(R.id.infotablayot);
        viewPager = findViewById(R.id.infoviewpager);

        tabLayout.setupWithViewPager(viewPager);

        infoVPAdapter infoVPAdapter= new infoVPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        infoVPAdapter.addFragment(new NedenFragment(),"Hedefimiz Nedir");
        infoVPAdapter.addFragment(new NasilKullanilirFragment(),"Uygulama Kullanımı");
        infoVPAdapter.addFragment(new BizKimizFragment(),"Biz Kimiz");

        viewPager.setAdapter(infoVPAdapter);
    }
}