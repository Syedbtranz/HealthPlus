package com.btranz.healthplus.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.btranz.healthplus.R;
import com.btranz.healthplus.fragments.RespirationRateFragment;
import com.btranz.healthplus.fragments.OxygenSaturationFragment;
import com.btranz.healthplus.fragments.PulseRateFragment;
import com.btranz.healthplus.fragments.TemperatureFragment;
import com.btranz.healthplus.fragments.BPDFragment;
import com.btranz.healthplus.fragments.BPSFragment;


public class VitalsDataHistory extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BPSFragment(), "Blood Pressure Systolic (mmHG)");
        adapter.addFrag(new BPDFragment(), "Blood Pressure Diastolic (mmHG");
        adapter.addFrag(new PulseRateFragment(), "Pulse Rate (per minute)");
        adapter.addFrag(new OxygenSaturationFragment(), "Oxygen Saturation (SpO2) (%)");
        adapter.addFrag(new TemperatureFragment(), "Temperature (0C)");
        adapter.addFrag(new RespirationRateFragment(), "Respiration Rate (breaths per minute)");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
