/**
 * File: CommunityFragment.java
 * Purpose: Community fragment is designed to be table member layout
 */

package com.peacecorps.pcsa.safety_tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.peacecorps.pcsa.R;

public class CommunityFragment extends Fragment {

    private ViewPager mPager = null;
    private PagerAdapter mPagerAdapter = null;
    private int[] contentToDisplay = new int[]{
            R.string.safety_plan_5_concerns,
            R.string.safety_plan_5_action
    };
    private Button actionButton = null;
    private static View rootView = null;

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final @Nullable ViewGroup container,
                             final @Nullable Bundle savedInstanceState) {

        assert inflater != null;

        rootView = inflater.inflate(R.layout.fragment_safety_plan_common, container, false);

        settingView();

        settingAdapterOfPager();

        settingOnClickListener();

        settingPageChangeListener();

        return rootView;
    }

    /**
     * Set listener of page change in the CommunityFragment view
     */
    private void settingPageChangeListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position,
                                       final float positionOffset,
                                       final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (position == 1) {
                    actionButton.setText(Html.fromHtml(getString(R.string.see_concerns)));
                } else {
                    actionButton.setText(Html.fromHtml(getString(R.string.see_action)));
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    /**
     * Set views of the CommunityFragment view
     */
    private void settingView() {
        mPager = (ViewPager) rootView.findViewById(R.id.safety_plan_pager);
        actionButton = (Button) rootView.findViewById(R.id.actionButton);
    }

    /**
     * Set onClick listener of the action button of the CommunityFragment view
     */
    private void settingOnClickListener() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (actionButton.getText().toString().equals(getString(R.string.see_action))) {
                    actionButton.setText(Html.fromHtml(getString(R.string.see_concerns)));
                    mPager.setCurrentItem(1);
                } else {
                    actionButton.setText(Html.fromHtml(getString(R.string.see_action)));
                    mPager.setCurrentItem(0);
                }
            }
        });
    }

    /**
     * Set adapter of pager of the CommunityFragment view
     */
    private void settingAdapterOfPager() {
        mPagerAdapter = new ScreenSlideCustomPagerAdapter(getActivity(),
                contentToDisplay,
                SafetyPlanActivity.PAGES);
        mPager.setAdapter(mPagerAdapter);
    }
}
