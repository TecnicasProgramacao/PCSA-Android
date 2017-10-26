/**
 * File: StepsFragment
 * Purpose: Steps in Reporting view fragment
 */

package com.peacecorps.pcsa.support_services;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

public class StepsFragment extends Fragment {

    public static final String TAG = StepsFragment.class.getSimpleName();
    private View rootView = null;
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

        rootView =  inflater.inflate(R.layout.fragment_reporting_steps,
                container,
                false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.after_assault);

        setingTextView();

        return rootView;
    }

    /**
     * Seting text view of the steps fragment view
     */
    private void setingTextView() {
        TextView reportingStep1 = (TextView) rootView.findViewById(R.id.reporting_step1);
        TextView reportingStep2 = (TextView) rootView.findViewById(R.id.reporting_step2);
        TextView reportingStep3 = (TextView) rootView.findViewById(R.id.reporting_step3);
        TextView reportingStep4 = (TextView) rootView.findViewById(R.id.reporting_step4);
        TextView reportingStep5 = (TextView) rootView.findViewById(R.id.reporting_step5);
        TextView reportingStep6 = (TextView) rootView.findViewById(R.id.reporting_step6);

        reportingStep1.setText(Html.fromHtml(getResources().getString(R.string.reporting_step1)));
        reportingStep2.setText(Html.fromHtml(getResources().getString(R.string.reporting_step2)));
        reportingStep3.setText(Html.fromHtml(getResources().getString(R.string.reporting_step3)));
        reportingStep4.setText(Html.fromHtml(getResources().getString(R.string.reporting_step4)));
        reportingStep5.setText(Html.fromHtml(getResources().getString(R.string.reporting_step5)));
        reportingStep6.setText(Html.fromHtml(getResources().getString(R.string.reporting_step6)));
    }
}
