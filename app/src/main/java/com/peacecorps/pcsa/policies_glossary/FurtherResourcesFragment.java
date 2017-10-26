/*
 * File: FurtherResourcesFragment.java
 * Package: policies_glossary
 *
 * Purpose: Class destined to list resources for user on further reading
 */

package com.peacecorps.pcsa.policies_glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

public class FurtherResourcesFragment extends Fragment{

    public static final String TAG = FurtherResourcesFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_resources, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.policies_glossary);

        TextView resources = (TextView) rootView.findViewById(R.id.resources_content);

        return rootView;
    }
}
