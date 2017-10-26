package com.peacecorps.pcsa.circle_of_trust.slides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peacecorps.pcsa.R;

// Third Slide of circle of trust introduction
public class ThirdSlide extends Fragment {
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro3, container, false);
        return view;
    }
}
