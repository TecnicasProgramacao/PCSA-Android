package com.peacecorps.pcsa.safety_tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.peacecorps.pcsa.R;
import com.peacecorps.pcsa.SingleTextViewFragment;

/*
 * Bystander Intervention main fragment
 *
 * @author rohan
 * @since 2016-07-08
 */
public class BystanderInterventionFragment extends Fragment {

    public static final String TAG = BystanderInterventionFragment.class.getSimpleName();

    private Button potentialVictim;
    private Button potentialOffender;
    private Button tacticsForBoth;

    private TextView safetyText;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_bystander, container, false);

        setUpButtons(rootView);
        setSafetyText(rootView);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.bystander_intervention);

        return rootView;
    }

    private void setUpButtons(final View rootView){
        // Setup potential offender button
        potentialOffender = (Button) rootView.findViewById(R.id.potentialOffender);
        potentialOffenderClickListener();

        // Setup potential victim button
        potentialVictim = (Button) rootView.findViewById(R.id.potentialVictim);
        potentialVictimClickListener();

        // Setup tactics button
        tacticsForBoth = (Button) rootView.findViewById(R.id.tacticsForBoth);
        tacticsClickListener();
    }

    private void setSafetyText(final View rootView){
        safetyText = (TextView) rootView.findViewById(R.id.bystander_text);
        safetyText.setText(Html.fromHtml(getActivity().getString(R.string.safety_text_bystander)));

        final int textSize = 26; // Size in pixels
        safetyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        safetyText.setGravity(Gravity.CENTER);
    }

    private void potentialOffenderClickListener(){
        potentialOffender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String potentialOffender = getString(R.string.bystander_potential_offender);
                final String verbalOffender = getString(R.string.bystander_verbal_offender);

                SingleTextViewFragment.showSingleTextLayout(getActivity(),
                        TAG,
                        potentialOffender,
                        verbalOffender);
            }
        });
    }

    private void potentialVictimClickListener(){
        potentialVictim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String potentialVictim = getString(R.string.bystander_potential_victim);
                final String verbalVictim = getString(R.string.bystander_verbal_victim);

                SingleTextViewFragment.showSingleTextLayout(getActivity(),
                        TAG,
                        potentialVictim,
                        verbalVictim);
            }
        });
    }

    private void tacticsClickListener(){
        tacticsForBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String taticsBoth = getString(R.string.bystander_tactics_both);
                final String nonVerbal = getString(R.string.bystander_non_verbal);

                SingleTextViewFragment.showSingleTextLayout(getActivity(),
                        TAG,
                        taticsBoth,
                        nonVerbal);
            }
        });
    }
}
