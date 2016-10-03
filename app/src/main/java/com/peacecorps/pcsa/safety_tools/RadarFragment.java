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

    public static final String TAG = RadarFragment.class.getSimpleName();
    private ViewPager mPager;
    private ImageView nextStep, prevStep;
    private TextView stepIndicator;

    private int[] stepsContent = new int[]{R.string.radar_step1, R.string.radar_step2,
            R.string.radar_step3, R.string.radar_step4, R.string.radar_step5};
    private final int[] steps = new int[]{R.string.step_1, R.string.step_2,
            R.string.step_3, R.string.step_4, R.string.step_5};

    public static final int NUM_PAGES = 5;
    private enum pages {
        FIRST_PAGE, SECOND_PAGE, THIRD_PAGE, FOURTH_PAGE, FIFT_PAGE
    }


    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the fragment
     */
    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {

        assert (inflater != null);

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

    /**
     * Creates the click listener for the button previous step. When the first page is shown
     * the button is set to invisible, otherwise is set to visible.
     */
    private void clickListenerPrevStep() {
        prevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int previousPage = mPager.getCurrentItem() - 1;
                mPager.setCurrentItem(previousPage);

                CharSequence stepTitle = Html.fromHtml(getString(steps[mPager.getCurrentItem()]));
                Log.d(TAG, "Page Text " + stepTitle);
                stepIndicator.setText(stepTitle);

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

    /**
     * Creates the click listener for the button next step. When the last page is shown
     * the button is set to invisible, otherwise is set to visible.
     */
    private void clickListenerNextStep() {
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int nextPage = mPager.getCurrentItem() + 1;
                mPager.setCurrentItem(nextPage);

                CharSequence stepTitle = Html.fromHtml(getString(steps[mPager.getCurrentItem()]));
                Log.d(TAG, "Page Text " + stepTitle);
                stepIndicator.setText(stepTitle);

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

    /**
     * Creates and initialize the onPageListener for the mPager variable.
     */
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
                    Log.d(TAG, "Reached first page, set prevStep button invisible.");
                    prevStep.setVisibility(View.INVISIBLE);
                } else if (position == pages.FIFT_PAGE.ordinal()) {
                    Log.d(TAG, "Reached last page, set nextStep button invisible.");
                    nextStep.setVisibility(View.INVISIBLE);
                } else if (position == pages.SECOND_PAGE.ordinal()) {
                    Log.d(TAG, "Passed first page, set prevStep button visible.");
                    prevStep.setVisibility(View.VISIBLE);
                } else if (position == pages.FOURTH_PAGE.ordinal()) {
                    Log.d(TAG, "Backed from last page, set nextStep button visible.");
                    nextStep.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });
    }

    /**
     * Creates and set the components in the layout of the radar fragment with its IDs.
     *
     * @param rootView - Object inflated with the radarFragment layout.
     */
    private void setViewContents(final View rootView) {
        assert (rootView != null);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        nextStep = (ImageView) rootView.findViewById(R.id.next_step);
        prevStep = (ImageView) rootView.findViewById(R.id.prev_step);
        stepIndicator = (TextView) rootView.findViewById(R.id.steps_text);
    }

    /**
     * Initialize the components (mPager, stepIndicator and prevStep) with its initial values.
     */
    private void initializeContents() {
        PagerAdapter mPagerAdapter = new ScreenSlideCustomPagerAdapter(getActivity(),
                stepsContent, NUM_PAGES);
        mPager.setAdapter(mPagerAdapter);

        CharSequence stepTitle = Html.fromHtml(getString(steps[0]));
        stepIndicator.setText(stepTitle);

        prevStep.setVisibility(View.INVISIBLE);
    }
}
