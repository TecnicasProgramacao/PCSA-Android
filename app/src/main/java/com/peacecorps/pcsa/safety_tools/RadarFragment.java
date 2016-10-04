/*
 * File: RadarFragment.java
 * Package: safety_tools
 *
 * Purpose: Shows the guidelines and steps to manage risks and danger
 */

package com.peacecorps.pcsa.safety_tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.peacecorps.pcsa.BuildConfig;
import com.peacecorps.pcsa.R;

public class RadarFragment extends Fragment {

    public static final String TAG = RadarFragment.class.getSimpleName();

    private ViewPager mPager = null;
    private ImageView nextButton = null;
    private ImageView prevButton = null;
    private TextView stepIndicator = null;

    private int[] stepsContent = new int[]{R.string.radar_step1, R.string.radar_step2,
            R.string.radar_step3, R.string.radar_step4, R.string.radar_step5};
    private final int[] stepTitles = new int[]{R.string.step_1, R.string.step_2,
            R.string.step_3, R.string.step_4, R.string.step_5};

    private static final int NUM_PAGES = 5;
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
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        assert (inflater != null);

        View rootView =  inflater.inflate(R.layout.fragment_radar, container, false);

        setViewContents(rootView);
        initializeContents();

        clickListenerPrevStep();
        clickListenerNextStep();

        pageChangeListener();

        // getSupportActionBar() may return null. If null may crash the program.
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.radar);
        } else {
            Log.e(TAG, "getSupportActionBar() is null.");
        }

        return rootView;
    }

    /**
     * Creates the click listener for the button previous step. When the first page is shown
     * the button is set to invisible, otherwise is set to visible.
     */
    private void clickListenerPrevStep() {
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int previousPage = mPager.getCurrentItem() - 1;

                setCurrentPage(previousPage);

                correctVisibility(prevButton);
                correctVisibility(nextButton);
            }
        });
    }

    /**
     * Creates the click listener for the button next step. When the last page is shown
     * the button is set to invisible, otherwise is set to visible.
     */
    private void clickListenerNextStep() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int nextPage = mPager.getCurrentItem() + 1;

                setCurrentPage(nextPage);

                correctVisibility(prevButton);
                correctVisibility(nextButton);
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
                // Do nothing.
            }

            @Override
            public void onPageSelected(final int position) {
                /* Do nothing. All the page processing is done when the user
                 clicks the previous and next buttons.
                */
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
                // Do nothing.
            }
        });
    }

    /**
     * This method checks which page is displayed and change the visibility of the button
     * accordingly.
     *
     * @param button - Button to be evaluated the visibility
     */
    private void correctVisibility(final ImageView button) {
        assert (button != null);

        final int buttonId = button.getId();
        final int nextButtonId = nextButton.getId();
        final int prevButtonId = prevButton.getId();

        // Assert that the button is a known button.
        if (BuildConfig.DEBUG && !(buttonId == nextButtonId || buttonId == prevButtonId)) {
            Log.e(TAG, "Severe error, button not recognized in correctVisibility().");
            throw new AssertionError();
        } else {
            Log.d(TAG, "Button is known.");
        }

        if (button.getId() == prevButton.getId()) { // The object is the prevButton.
            if (mPager.getCurrentItem() == pages.FIRST_PAGE.ordinal()) { // Reached the last page.
                prevButton.setVisibility(View.INVISIBLE);
            } else {
                prevButton.setVisibility(View.VISIBLE);
            }

        } else if (button.getId() == nextButton.getId()) { // The object is the nextButton.
            if (mPager.getCurrentItem() == pages.FIFT_PAGE.ordinal()) { // Reached the last page.
                nextButton.setVisibility(View.INVISIBLE);
            } else {
                nextButton.setVisibility(View.VISIBLE);
            }

        } else {
            // Do nothing, the button is unknown.
        }

    }

    /**
     * Set and display the page chosen and writes the correct title for it.
     *
     * @param page - Number of page to be displayed
     */
    private void setCurrentPage(final int page) throws AssertionError {
        // Assert is unreliable, BuildConfig.DEBUG is more reliable.
        if (BuildConfig.DEBUG && !(page >= 0 && page <= NUM_PAGES)) {
            throw new AssertionError();
        } else {
            Log.d(TAG, "Page number in correct interval.");
        }

        mPager.setCurrentItem(page);

        String stepTitle = getString(stepTitles[mPager.getCurrentItem()]);
        Log.d(TAG, "Page Text " + stepTitle);
        stepIndicator.setText(Html.fromHtml(stepTitle));
    }

    /**
     * Creates and set the components in the layout of the radar fragment with its IDs.
     *
     * @param rootView - Object inflated with the radarFragment layout.
     */
    private void setViewContents(final View rootView) {
        assert (rootView != null);

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        nextButton = (ImageView) rootView.findViewById(R.id.next_step);
        prevButton = (ImageView) rootView.findViewById(R.id.prev_step);
        stepIndicator = (TextView) rootView.findViewById(R.id.steps_text);
    }

    /**
     * Initialize the components (mPager, stepIndicator and prevButton) with its initial values.
     */
    private void initializeContents() {
        PagerAdapter mPagerAdapter = new ScreenSlideCustomPagerAdapter(getActivity(),
                stepsContent, NUM_PAGES);
        mPager.setAdapter(mPagerAdapter);

        try {
            setCurrentPage(pages.FIRST_PAGE.ordinal());
        } catch (AssertionError error) {
            Log.e(TAG, "Error, page index out of bounds");
        }


        prevButton.setVisibility(View.INVISIBLE);
    }
}
