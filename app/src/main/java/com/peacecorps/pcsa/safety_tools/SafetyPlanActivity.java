/**
 * File: SafetyPlanActivity.java
 * Purpose: Safety Plan Activity, handles all fragment transactions of the TabLayout
 */

package com.peacecorps.pcsa.safety_tools;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.peacecorps.pcsa.R;

import java.util.ArrayList;
import java.util.List;

public class SafetyPlanActivity extends AppCompatActivity {

    private Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    public static final int PAGES = 2;

    /**
     * Creates and returns the view hierarchy associated with the activity.
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        assert savedInstanceState != null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_plan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.safety_plan_title);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Configure an adapter that will be used for the page associated with the security plans
     * @param viewPager - Page to be associated
     */
    private void setupViewPager(final ViewPager viewPager) {
        assert viewPager != null;

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PhysicalSafetyFragment(), getString(R.string.physical_safety));
        adapter.addFragment(new HomeFragment(), getString(R.string.home_fragment));
        adapter.addFragment(new WorkplaceFragment(), getString(R.string.workplace));
        adapter.addFragment(new SafetyTechnologyFragment(), getString(R.string.safety_technology));
        adapter.addFragment(new CommunityFragment(), getString(R.string.community));
        adapter.addFragment(new TransportationFragment(), getString(R.string.transport));

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        /**
         * Constructor of the viewPager adapter
         * @param manager - Fragment that starts the viewPagerAdapter
         */
        ViewPagerAdapter(final FragmentManager manager) {
            super(manager);
            assert manager != null;
        }

        /**
         * Adds a fragment and a title referring to it
         * @param fragment - Fragment to be added
         * @param title - Title to be added to the fragment
         */
        public void addFragment(final Fragment fragment, final String title) {
            assert fragment != null;
            assert title != null;

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        /**
         * Grab a specific item from the list of security plans fragments
         * @param position - Item position to be caught
         * @return Fragment - Fragment containing the item for
         */
        @Override
        public Fragment getItem(final int position) {
            assert position >= 0;

            Fragment fragment = mFragmentList.get(position);
            return fragment;
        }

        /**
         * Get the size of the fragments of the list for the security plans
         * @return int - Size of the fragment list for the security plans
         */
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * Picks up the page for the given position
         * @param position - Position to take the page
         * @return CharSequence - Title of the page for the position
         */
        @Override
        public CharSequence getPageTitle(final int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
