/**
 * File: PoliciesFragment.java
 * Purpose: Where the screens that show more information about the project glossary and policies
 */

package com.peacecorps.pcsa.policies_glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.peacecorps.pcsa.MainActivity;
import com.peacecorps.pcsa.R;
import com.peacecorps.pcsa.SingleTextViewFragment;

public class PoliciesFragment extends Fragment {

    public static final String TAG = PoliciesFragment.class.getSimpleName();
    private Button policyButton = null, glossaryButton = null, furtherButton = null;
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

        rootView =  inflater.inflate(R.layout.fragment_policies, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.policies_glossary);

        setButtons();
        setOnClickListeners();

        return rootView;
    }

    /**
     * Sets the onClick listeners of the Buttons of the PoliciesFragment view
     */
    private void setOnClickListeners() {
        policyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SingleTextViewFragment.showSingleTextLayout(getActivity(),
                        getString(R.string.policies_title),
                        getString(R.string.subtitle_policies),
                        getString(R.string.policies_all));
            }
        });

        glossaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping GlossaryFragment into the container
                GlossaryFragment glossaryFragment = new GlossaryFragment();
                MainActivity.swapFragmentIn(getActivity(), glossaryFragment,
                        GlossaryFragment.SIMPLENAME, true);
            }
        });

        furtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping GlossaryFragment into the container
                FurtherResourcesFragment furtherResourcesFragment = new FurtherResourcesFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        furtherResourcesFragment,
                        FurtherResourcesFragment.TAG,
                        true);
            }
        });
    }

    /**
     * Sets the Buttons of the PoliciesFragment view
     */
    private void setButtons() {
        policyButton = (Button) rootView.findViewById(R.id.policiesButton);
        glossaryButton = (Button) rootView.findViewById(R.id.glossaryButton);
        furtherButton = (Button) rootView.findViewById(R.id.furtherButton);
    }

}

