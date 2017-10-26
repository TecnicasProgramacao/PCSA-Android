/**
 * File: FAQFragment.java
 * Purpose: Frequently Asked Questions (FAQ) page implemented as a list
 */

package com.peacecorps.pcsa.support_services;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

public class FAQFragment extends ListFragment {

    public static final String TAG = FAQFragment.class.getSimpleName();
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
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {

        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        View rootView =  inflater.inflate(R.layout.fragment_coping, container, false);
        TextView subtitle = (TextView) rootView.findViewById(R.id.subtitle);
        subtitle.setText(getString(R.string.title_activity_faq));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.reporting_types);

        FAQArrayAdapter faqArrayAdapter = new FAQArrayAdapter(getActivity(), titles, values);
        setListAdapter(faqArrayAdapter);

        return rootView;
    }

    /**
     * Set titles in the resources of the fragment
     */
    private void settingTitles() {
        titles = new String[]{
                getResources().getString(R.string.reporting_faq1_header),
                getResources().getString(R.string.reporting_faq2_header),
                getResources().getString(R.string.reporting_faq3_header),
                getResources().getString(R.string.reporting_faq4_header),
                getResources().getString(R.string.reporting_faq5_header),
                getResources().getString(R.string.reporting_faq6_header)
        };
    }

    /**
     * Set values in the resources of the fragment
     */
    private void settingValues() {
        values = new String[] {
                getResources().getString(R.string.reporting_faq1),
                getResources().getString(R.string.reporting_faq2),
                getResources().getString(R.string.reporting_faq3),
                getResources().getString(R.string.reporting_faq4),
                getResources().getString(R.string.reporting_faq5),
                getResources().getString(R.string.reporting_faq6)
        };
    }
}
