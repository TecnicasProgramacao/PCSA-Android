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

/*
 * Transportation fragment, a member of the Tabbed Layout
 *
 * @author rohan
 * @since 2016-07-08
 */
public class TransportationFragment extends Fragment {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    int[] contentToDisplay = new int[]{R.string.safety_plan_6_concerns,
            R.string.safety_plan_6_action};
    Button actionButton;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_safety_plan_common, container, false);

        initializePager(rootView);
        initializeButton(rootView);

        return rootView;
    }

    private void initializePager(final View rootView) {
        mPager = (ViewPager) rootView.findViewById(R.id.safety_plan_pager);
        mPagerAdapter = new ScreenSlideCustomPagerAdapter(getActivity(),
                contentToDisplay,
                SafetyPlanActivity.PAGES);
        mPager.setAdapter(mPagerAdapter);

        setPageChange();
    }

    private void setPageChange() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position,
                                       final float positionOffset,
                                       final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                if (position == 1) {
                    String seeConcerns = getString(R.string.see_concerns);
                    actionButton.setText(Html.fromHtml(seeConcerns));
                } else {
                    String seeAction = getString(R.string.see_action);
                    actionButton.setText(Html.fromHtml(seeAction));
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    private void initializeButton(final View rootView) {
        actionButton = (Button) rootView.findViewById(R.id.actionButton);
        setActionClickListener();
    }

    private void setActionClickListener() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String actionString = actionButton.getText().toString();
                String seeAction = getString(R.string.see_action);

                if (actionString.equals(seeAction)) {
                    String concerns = getString(R.string.see_concerns);
                    actionButton.setText(Html.fromHtml(concerns));
                    mPager.setCurrentItem(1);
                } else {
                    actionButton.setText(Html.fromHtml(seeAction));
                    mPager.setCurrentItem(0);
                }
            }
        });
    }

}
