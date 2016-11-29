/*
 * File: GlossaryAdapter.java
 * Package: policies_glossary
 *
 * Purpose: Custom adapter for Expandable List View in GlossaryFragment.java
 */

package com.peacecorps.pcsa.policies_glossary;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GlossaryAdapter extends BaseExpandableListAdapter {

    private Context glossaryAdapterContext;
    private List<String> glossaryAdapterListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<String, List<String>> glossaryAdapterListDataChild;

    public GlossaryAdapter(final Context context,
                           final List<String> listDataHeader,
                           final HashMap<String, List<String>> listChildData,
                           final ExpandableListView expandableListView) {

        this.glossaryAdapterContext = context;
        this.glossaryAdapterListDataHeader = listDataHeader;
        this.glossaryAdapterListDataChild = listChildData;
        ExpandableListView listView = expandableListView;
    }

    @Override
    public final Object getChild(final int groupPosition, final int childPosition) {
        return this.glossaryAdapterListDataChild.get(this.glossaryAdapterListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public final long getChildId(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public final View getChildView(final int groupPosition,
                                   final int childPosition,
                                   final boolean isLastChild,
                                   View convertView,
                                   final ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.glossaryAdapterContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_glossary_meaning, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.word_meaning);

        txtListChild.setText(Html.fromHtml(childText));
        return convertView;
    }

    @Override
    public final int getChildrenCount(final int groupPosition) {
        return this.glossaryAdapterListDataChild.get(this.glossaryAdapterListDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public final Object getGroup(final int groupPosition) {
        return this.glossaryAdapterListDataHeader.get(groupPosition);
    }

    @Override
    public final int getGroupCount() {
        return this.glossaryAdapterListDataHeader.size();
    }

    @Override
    public final long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public final View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.glossaryAdapterContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_glossary_item, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.word_title);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public final boolean hasStableIds() {
        return false;
    }

    @Override
    public final boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return true;
    }

    public final void filter(final String textEntered) {
        prepareListData(glossaryAdapterContext,
                        glossaryAdapterListDataHeader,
                        glossaryAdapterListDataChild);

        Iterator headers = glossaryAdapterListDataHeader.iterator();
        while (headers.hasNext()) {
            String next = (String) headers.next();

            // Transform all to upper case not to differentiate lower and upper case
            String textEnteredUpper = textEntered.toUpperCase();
            String nextUpper = next.toUpperCase();

            boolean startsWithEntered = nextUpper.startsWith(textEnteredUpper);
            boolean isShorter = next.length() < textEntered.length();

            // Remove items that does not match the user preferences
            if (isShorter || !startsWithEntered) {
                headers.remove();
            }
        }

        // Iterator to run through data child map (headers, descriptions)
        Iterator<Map.Entry<String, List<String>>> dataChildIterator =
                glossaryAdapterListDataChild.entrySet().iterator();

        while (dataChildIterator.hasNext()) {
            Map.Entry<String, List<String>> entry = dataChildIterator.next();
            if (!glossaryAdapterListDataHeader.contains(entry.getKey())) {
                dataChildIterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Initialize the content lists to be displayed in the screen
     * @param context Current context view
     * @param listDataHeader List to be populated with the headers
     * @param listDataChild HashMap to be initialize with headers and descriptions
     */
    public static void prepareListData(final Context context,
                                       List<String> listDataHeader,
                                       final HashMap<String, List<String>> listDataChild) {
        // Clear any residues in lists
        listDataChild.clear();
        listDataHeader.clear();

        final int HEADERSID[ ] = new int[] {// Constant for header id strings
                R.string.aggravated_sexual_assault,
                R.string.assailant,
                R.string.burglary,
                R.string.intervention,
                R.string.phenomenon,
                R.string.cyber,
                R.string.danger,
                R.string.mitigate,
                R.string.pii,
                R.string.rape,
                R.string.risk,
                R.string.rob,
                R.string.safe,
                R.string.security,
                R.string.sexual_assault,
                R.string.exploit,
                R.string.harass,
                R.string.misconduct,
                R.string.predator,
                R.string.threat,
                R.string.stalk,
                R.string.theft,
                R.string.vulnerability
        };

        listDataHeader = populateListWithStrings(context, HEADERSID);

        final int DESCRIPTIONSID[ ] = new int[]{// Constant for descriptions id strings
                R.string.asexual_assault,
                R.string.assailant_info,
                R.string.burglary_info,
                R.string.intervention_info,
                R.string.phenomenon_info,
                R.string.cyber_info,
                R.string.danger_info,
                R.string.mitigation_info,
                R.string.pii_info,
                R.string.rape_info,
                R.string.risk_info,
                R.string.robbery_info,
                R.string.safety_info,
                R.string.security_info,
                R.string.sexual_assault_info1,
                R.string.exploitation_info,
                R.string.harassment_info,
                R.string.misconduct_info,
                R.string.threat_info,
                R.string.predator_info,
                R.string.stalking_info,
                R.string.theft_info,
                R.string.vulnerability_info
        };


        final List<List<String>> descriptions = new ArrayList<>();
        // Create one List for each description item
        for (int id : DESCRIPTIONSID) {
            final String description = context.getString(id);

            final List<String> descriptionItem = new ArrayList<>();
            descriptionItem.add(description);

            descriptions.add(descriptionItem);
        }

        final int maxItems; // Number of ids in description and header lists

        if (HEADERSID.length == DESCRIPTIONSID.length){
            maxItems = DESCRIPTIONSID.length;

        } else {
            // If the header and description contents are not compatible in number choose the
            // header number as the main as there could not be a content with out title
            maxItems = HEADERSID.length;
        }

        // Iterate through items in both header and description lists
        for (int i = 0; i < maxItems; i++) {
            final String header = listDataHeader.get(i);
            final List<String> description = descriptions.get(i);

            listDataChild.put(header, description);
        }
    }

    private static List<String> populateListWithStrings(final Context context,
                                                 final int listIDs[ ]) {
        List<String> resultList = new ArrayList<>();

        for (int headerID : listIDs) {
            final String header = context.getString(headerID);
            resultList.add(header);
        }

        return resultList;

    }
}
