package com.peacecorps.pcsa.policies_glossary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.peacecorps.pcsa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Glossary
 * @author rohan
 * @since 2016-07-29
 */
public class GlossaryFragment extends Fragment {

    public static final String SIMPLENAME = GlossaryFragment.class.getSimpleName();
    List<String> listDataHeader = new ArrayList<>();
    EditText searchBox;
    HashMap<String, List<String>> listDataChild = new HashMap<>();
    GlossaryAdapter listAdapter;
    int currentExpanded = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_glossary,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.policies_glossary);
        final ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.list_words);
        GlossaryAdapter.prepareListData(getActivity(),listDataHeader,listDataChild);
        listAdapter = new GlossaryAdapter(getActivity(), listDataHeader, listDataChild,expListView);
        expListView.setAdapter(listAdapter);
        searchBox = (EditText)rootView.findViewById(R.id.inputSearch);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int counter, int afterChange) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int beforeChange, int counter) {
                Log.e("INPUT", charSequence.toString());
                listAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editableText) {
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if( currentExpanded >= 0 && currentExpanded != groupPosition)
                    expListView.collapseGroup(currentExpanded);
                currentExpanded = groupPosition;
            }
        });
        return rootView;
    }
}
