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

/*
 * Custom Adapter for Expandable List View in GlossaryFragment
 * @author rohan
 * @since 2016-07-30
 */
public class GlossaryAdapter extends BaseExpandableListAdapter {

    private Context glossaryAdapterContext;
    private List<String> glossaryAdapterListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> glossaryAdapterListDataChild;
    private ExpandableListView listView;

    public GlossaryAdapter(final Context context,
                           final List<String> listDataHeader,
                           final HashMap<String, List<String>> listChildData,
                           final ExpandableListView expandableListView) {
        this.glossaryAdapterContext = context;
        this.glossaryAdapterListDataHeader = listDataHeader;
        this.glossaryAdapterListDataChild = listChildData;
        this.listView = expandableListView;
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
    public final View getChildView(final int groupPosition, final int childPosition,
                                   final boolean isLastChild, View convertView, final ViewGroup parent) {

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

        Iterator listIt = glossaryAdapterListDataHeader.iterator();
        while (listIt.hasNext()) {
            String next = (String) listIt.next();
            if (next.length() < textEntered.length()
                    || !next.toUpperCase().startsWith(textEntered.toUpperCase())) {
                listIt.remove();
            }
        }

        for (Iterator<Map.Entry<String, List<String>>>
             it = glossaryAdapterListDataChild.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, List<String>> entry = it.next();
            if (!glossaryAdapterListDataHeader.contains(entry.getKey())) {
                it.remove();
            }
        }
        notifyDataSetChanged();
    }

    public static void prepareListData(final Context context,
                                       final List<String> listDataHeader,
                                       final HashMap<String, List<String>> listDataChild) {
        listDataChild.clear();
        listDataHeader.clear();

        listDataHeader.add(context.getString(R.string.aggravated_sexual_assault));
        listDataHeader.add(context.getString(R.string.assailant));
        listDataHeader.add(context.getString(R.string.burglary));
        listDataHeader.add(context.getString(R.string.intervention));
        listDataHeader.add(context.getString(R.string.phenomenon));
        listDataHeader.add(context.getString(R.string.cyber));
        listDataHeader.add(context.getString(R.string.danger));
        listDataHeader.add(context.getString(R.string.mitigate));
        listDataHeader.add(context.getString(R.string.pii));
        listDataHeader.add(context.getString(R.string.rape));
        listDataHeader.add(context.getString(R.string.risk));
        listDataHeader.add(context.getString(R.string.rob));
        listDataHeader.add(context.getString(R.string.safe));
        listDataHeader.add(context.getString(R.string.security));
        listDataHeader.add(context.getString(R.string.sexual_assault));
        listDataHeader.add(context.getString(R.string.exploit));
        listDataHeader.add(context.getString(R.string.harass));
        listDataHeader.add(context.getString(R.string.misconduct));
        listDataHeader.add(context.getString(R.string.predator));
        listDataHeader.add(context.getString(R.string.threat));
        listDataHeader.add(context.getString(R.string.stalk));
        listDataHeader.add(context.getString(R.string.theft));
        listDataHeader.add(context.getString(R.string.vulnerability));

        // Adding child data
        List<String> assault = new ArrayList<String>();
        assault.add(context.getString(R.string.asexual_assault));

        List<String> assailant = new ArrayList<String>();
        assailant.add(context.getString(R.string.assailant_info));

        List<String> burglary = new ArrayList<String>();
        burglary.add(context.getString(R.string.burglary_info));

        List<String> intervention = new ArrayList<String>();
        intervention.add(context.getString(R.string.intervention_info));

        List<String> phenomenon = new ArrayList<String>();
        phenomenon.add(context.getString(R.string.phenomenon_info));

        List<String> cyber = new ArrayList<String>();
        cyber.add(context.getString(R.string.cyber_info));

        List<String> danger = new ArrayList<String>();
        danger.add(context.getString(R.string.danger_info));

        List<String> mitigate = new ArrayList<String>();
        mitigate.add(context.getString(R.string.mitigation_info));

        List<String> pii = new ArrayList<String>();
        pii.add(context.getString(R.string.pii_info));

        List<String> rape = new ArrayList<String>();
        rape.add(context.getString(R.string.rape_info));

        List<String> risk = new ArrayList<String>();
        risk.add(context.getString(R.string.risk_info));

        List<String> rob = new ArrayList<String>();
        rob.add(context.getString(R.string.robbery_info));

        List<String> safe = new ArrayList<String>();
        safe.add(context.getString(R.string.safety_info));

        List<String> security = new ArrayList<String>();
        security.add(context.getString(R.string.security_info));

        List<String> sexualAssault = new ArrayList<String>();

        sexualAssault.add(context.getString(R.string.sexual_assault_info1));

        List<String> exploit = new ArrayList<String>();
        exploit.add(context.getString(R.string.exploitation_info));

        List<String> harass = new ArrayList<String>();
        harass.add(context.getString(R.string.harassment_info));

        List<String> misconduct = new ArrayList<String>();
        misconduct.add(context.getString(R.string.misconduct_info));

        List<String> threat = new ArrayList<String>();
        threat.add(context.getString(R.string.threat_info));

        List<String> predator = new ArrayList<String>();
        predator.add(context.getString(R.string.predator_info));

        List<String> stalk = new ArrayList<String>();
        stalk.add(context.getString(R.string.stalking_info));

        List<String> theft = new ArrayList<String>();
        theft.add(context.getString(R.string.theft_info));

        List<String> vulnerability = new ArrayList<String>();
        vulnerability.add(context.getString(R.string.vulnerability_info));

        final int availableOptions[] = new int[]{0, 1, 2, 3, 4, 5,
                                                 6, 7, 8, 9, 10, 11,
                                                 12, 13, 14, 15, 16, 17,
                                                 18, 19, 20, 21, 22};

        listDataChild.put(listDataHeader.get(availableOptions[0]), assault);
        listDataChild.put(listDataHeader.get(availableOptions[1]), assailant);
        listDataChild.put(listDataHeader.get(availableOptions[2]), burglary);
        listDataChild.put(listDataHeader.get(availableOptions[3]), intervention);
        listDataChild.put(listDataHeader.get(availableOptions[4]), phenomenon);
        listDataChild.put(listDataHeader.get(availableOptions[5]), cyber);
        listDataChild.put(listDataHeader.get(availableOptions[6]), danger);
        listDataChild.put(listDataHeader.get(availableOptions[7]), mitigate);
        listDataChild.put(listDataHeader.get(availableOptions[8]), pii);
        listDataChild.put(listDataHeader.get(availableOptions[9]), rape);
        listDataChild.put(listDataHeader.get(availableOptions[10]), risk);
        listDataChild.put(listDataHeader.get(availableOptions[11]), rob);
        listDataChild.put(listDataHeader.get(availableOptions[12]), safe);
        listDataChild.put(listDataHeader.get(availableOptions[13]), security);
        listDataChild.put(listDataHeader.get(availableOptions[14]), sexualAssault);
        listDataChild.put(listDataHeader.get(availableOptions[15]), exploit);
        listDataChild.put(listDataHeader.get(availableOptions[16]), harass);
        listDataChild.put(listDataHeader.get(availableOptions[17]), misconduct);
        listDataChild.put(listDataHeader.get(availableOptions[18]), predator);
        listDataChild.put(listDataHeader.get(availableOptions[19]), threat);
        listDataChild.put(listDataHeader.get(availableOptions[20]), stalk);
        listDataChild.put(listDataHeader.get(availableOptions[21]), theft);
        listDataChild.put(listDataHeader.get(availableOptions[22]), vulnerability);

    }
}
