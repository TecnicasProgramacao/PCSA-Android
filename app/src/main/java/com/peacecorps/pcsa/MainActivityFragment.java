/**
 * File: MainActivityFragment.java
 * Purpose: A placeholder fragment containing a simple view.
 */

package com.peacecorps.pcsa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.peacecorps.pcsa.circle_of_trust.CircleIntro;
import com.peacecorps.pcsa.circle_of_trust.CircleOfTrustFragment;
import com.peacecorps.pcsa.get_help_now.ContactPostStaff;
import com.peacecorps.pcsa.policies_glossary.PoliciesFragment;
import com.peacecorps.pcsa.safety_tools.SafetyToolsFragment;
import com.peacecorps.pcsa.sexual_assault_awareness.MainFragment;
import com.peacecorps.pcsa.support_services.SupportServicesFragment;

public class MainActivityFragment extends Fragment {

    public static final String TAG = "MainActivityFragment";
    private boolean introFinished = false;
    private View rootView = null;

    private Button circleButton = null;
    private Button getHelpNowButton = null;
    private Button safetyToolsButton = null;
    private Button supportServicesButton = null;
    private Button assaultAwarenessButton = null;
    private Button policiesButton = null;

    public MainActivityFragment() {
        //Empty constructor is required
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
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        settingButtons();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.first_aide);

        settingOnClickListeners();

        return rootView;
    }

    /**
     * Receive the result from a previous call to
     * @param requestCode - The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode - The integer result code returned by the child activity
     *                   through its setResult().
     * @param data - An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 2:
                introFinished = true;
                //Swapping CircleOfTrustFragment into the container
                CircleOfTrustFragment circleOfTrustFragment = new CircleOfTrustFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        circleOfTrustFragment,
                        CircleOfTrustFragment.TAG,
                        true);
            default:
                //Nothing to do
        }
    }

    /**
     * Setting on click listeners of buttons of the MainActivityFragment class
     */

    private void settingOnClickListeners() {
        safetyToolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping Safety Tools HomeFragment Screen into the fragment container
                Fragment safetyToolsFragment = new SafetyToolsFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        safetyToolsFragment,
                        SafetyToolsFragment.TAG,
                        true);
            }
        });

        getHelpNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //Swapping ContactPostStaff into the fragment container dynamically
                Fragment contactPostStaffFragment = new ContactPostStaff();
                MainActivity.swapFragmentIn(getActivity(),
                        contactPostStaffFragment,
                        ContactPostStaff.TAG,
                        true);
            }
        });

        supportServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping Support Services HomeFragment into the fragment container dynamically
                Fragment supportServicesFragment = new SupportServicesFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        supportServicesFragment,
                        SupportServicesFragment.TAG,
                        true);
            }
        });

        assaultAwarenessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping Sexual Assault MainScreen into the fragment container dynamically
                Fragment assaultAwarenessFragment = new MainFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        assaultAwarenessFragment,
                        MainFragment.TAG,
                        true);
            }
        });

        policiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Swapping PoliciesFragment into the fragment container dynamically
                Fragment policiesFragment = new PoliciesFragment();
                MainActivity.swapFragmentIn(getActivity(),
                        policiesFragment,
                        PoliciesFragment.TAG,
                        true);
            }
        });

        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!introFinished) {
                    startActivityForResult(new Intent(getActivity(), CircleIntro.class), 2);
                } else {
                    //Swapping CircleOfTrustFragment into the container
                    CircleOfTrustFragment circleOfTrustFragment = new CircleOfTrustFragment();
                    MainActivity.swapFragmentIn(getActivity(),
                            circleOfTrustFragment,
                            CircleOfTrustFragment.TAG,
                            true);
                }
            }
        });
    }


    /**
     * Setting buttons of the MainActivityFragment class
     */
    private void settingButtons() {
        circleButton = (Button) rootView.findViewById(R.id.circleButton);
        getHelpNowButton = (Button) rootView.findViewById(R.id.getButton);
        safetyToolsButton = (Button) rootView.findViewById(R.id.safetyToolsButton);
        supportServicesButton = (Button) rootView.findViewById(R.id.supportServicesButton);
        assaultAwarenessButton = (Button) rootView.findViewById(R.id.assaultAwarenessButton);
        policiesButton = (Button) rootView.findViewById(R.id.policiesButton);
    }
}
