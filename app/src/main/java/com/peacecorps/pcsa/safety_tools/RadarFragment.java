/*
 * File: RadarFragment.java
 * Package: safety_tools
 *
 * Purpose: Shows the guidelines and steps to manage risks and danger
 */


package com.peacecorps.pcsa.safety_tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

public class RadarFragment extends Fragment {

    public static final int NUM_PAGES = 5;
    public static final String TAG = RadarFragment.class.getSimpleName();
    private ViewPager mPager;
    private ImageView nextStep, prevStep;
    private TextView stepIndicator;
    private int[] stepsContent = new int[]{R.string.radar_step1, R.string.radar_step2,
            R.string.radar_step3, R.string.radar_step4, R.string.radar_step5};
    private enum pages {
        FIRST_PAGE, SECOND_PAGE, THIRD_PAGE, FOURTH_PAGE, FIFT_PAGE
    }
    private final int[] steps = new int[]{R.string.step_1, R.string.step_2,
            R.string.step_3, R.string.step_4, R.string.step_5};


    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_radar, container, false);

        setViewContents(rootView);
        initializeContents();

        clickListenerPrevStep();
        clickListenerNextStep();

        pageChangeListener();

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.radar);
        }
        
        return rootView;
    }

    private void clickListenerPrevStep() {
        prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                stepIndicator.setText(Html.fromHtml(getString(steps[mPager.getCurrentItem()])));

                if (mPager.getCurrentItem() == pages.FIRST_PAGE.ordinal()) {
                    prevStep.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Prev Button hidden. mPager " + mPager.getCurrentItem());
                }

                if (nextStep.getVisibility() == View.INVISIBLE) {
                    nextStep.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void clickListenerNextStep() {
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                stepIndicator.setText(Html.fromHtml(getString(steps[mPager.getCurrentItem()])));

                if (mPager.getCurrentItem() == pages.FIFT_PAGE.ordinal()) {
                    nextStep.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "Next Button hidden. mPager " + mPager.getCurrentItem());
                }

                if (prevStep.getVisibility() == View.INVISIBLE) {
                    prevStep.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void pageChangeListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position,
                                       final float positionOffset,
                                       final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                stepIndicator.setText(Html.fromHtml(getString(steps[position])));

                if (position == pages.FIRST_PAGE.ordinal()) {
                    prevStep.setVisibility(View.INVISIBLE);
                } else if (position == pages.FIFT_PAGE.ordinal()) {
                    nextStep.setVisibility(View.INVISIBLE);
                } else if (position == pages.SECOND_PAGE.ordinal()) {
                    prevStep.setVisibility(View.VISIBLE);
                } else if (position == pages.FOURTH_PAGE.ordinal()) {
                    nextStep.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });
    }

    private void setViewContents(final View rootView) {
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        nextStep = (ImageView) rootView.findViewById(R.id.next_step);
        prevStep = (ImageView) rootView.findViewById(R.id.prev_step);
        stepIndicator = (TextView) rootView.findViewById(R.id.steps_text);
    }

    private void initializeContents() {
        PagerAdapter mPagerAdapter = new ScreenSlideCustomPagerAdapter(getActivity(),
                stepsContent, NUM_PAGES);
        mPager.setAdapter(mPagerAdapter);

        stepIndicator.setText(Html.fromHtml(getString(steps[0])));
        prevStep.setVisibility(View.INVISIBLE);
    }
}
