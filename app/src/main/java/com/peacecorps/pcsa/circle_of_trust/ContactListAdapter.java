/*
 * File: ContactListAdapter.java
 * Package: circle_of_trust
 *
 * Purpose: List Adaoter for showing multiple contact selection dialog.
 */

package com.peacecorps.pcsa.circle_of_trust;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

public class ContactListAdapter extends ArrayAdapter {

    private static final String TAG = ContactListAdapter.class.getSimpleName();
    private static LayoutInflater inflater = null;

    public ContactListAdapter(final Context context, final Object[] objects) {
        super(context, R.layout.dialog_list, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Retrive the contact list with selected contacts
     *
     * @param position position selected in the list
     * @param convertView
     * @param parent
     * @return Returns the list view with the contacts
     */
    @Override
    public final View getView(final int position, final View convertView, final ViewGroup parent) {
        // Initialize the TextView and the rows in the contact list
        View rowView = inflater.inflate(R.layout.contacts_dialog_listitem, null);
        TextView contactInfo = (TextView) rowView.findViewById(R.id.dialog_txt);

        // Shows the selected contact info in the TextView
        Object item = getItem(position);
        if (item != null) {
            contactInfo.setText(item.toString());
        } else {
            Log.e(TAG, "Item selected in contact list is null");
            contactInfo.setText("");
        }

        return rowView;
    }
}
