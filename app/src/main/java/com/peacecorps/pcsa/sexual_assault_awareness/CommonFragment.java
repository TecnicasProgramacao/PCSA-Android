/**
 * File: CommonFragment.java
 * Purpose: This fragment displays the main questions sexual assault common questions
 */

package com.peacecorps.pcsa.sexual_assault_awareness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacecorps.pcsa.R;
import com.peacecorps.pcsa.support_services.FAQArrayAdapter;

public class CommonFragment extends ListFragment {

    public static final String TAG = CommonFragment.class.getSimpleName();
    private String[] values = null;
    private String[] titles = null;

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

        View rootView =  inflater.inflate(R.layout.fragment_coping, container, false);
        TextView subtitle = (TextView) rootView.findViewById(R.id.subtitle);
        subtitle.setText(getString(R.string.common_questions));

        settingValues();

        settingTitles();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.sexual_assault_awareness);

        FAQArrayAdapter faqArrayAdapter = new FAQArrayAdapter(getActivity(), titles, values);
        setListAdapter(faqArrayAdapter);

        return rootView;
    }

    /**
     * Set titles in the resources of the fragment
     */
    private void settingTitles() {
        titles = new String[]{
                getResources().getString(R.string.common_qs_title1),
                getResources().getString(R.string.common_qs_title2),
                getResources().getString(R.string.common_qs_title3),
                getResources().getString(R.string.common_qs_title4),
                getResources().getString(R.string.common_qs_title5)};
    }

    /**
     * Set values in the resources of the fragment
     */
    private void settingValues() {
        values = new String[] {
                getResources().getString(R.string.common_as1),
                getResources().getString(R.string.common_as2),
                getResources().getString(R.string.common_as3),
                getResources().getString(R.string.common_as4),
                getResources().getString(R.string.common_as5)};
    }
}

