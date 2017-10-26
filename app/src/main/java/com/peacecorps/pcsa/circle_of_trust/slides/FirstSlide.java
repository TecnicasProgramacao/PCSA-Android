/**
 * File: FirstSlide.java
 * Purpose: First Slide of circle of trust introduction.
 */

package com.peacecorps.pcsa.circle_of_trust.slides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peacecorps.pcsa.R;

public class FirstSlide extends Fragment {

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

        View view = inflater.inflate(R.layout.intro, container, false);

        return view;
    }
}
