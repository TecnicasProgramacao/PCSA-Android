/*
 * File: CustomAdapter.java
 * Package: get_help_now
 *
 * Purpose: Initialize views of the customised dialog box
 */

package com.peacecorps.pcsa.get_help_now;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.peacecorps.pcsa.R;

public class CustomAdapter extends BaseAdapter {

    public static int[] caption = {R.string.dialog_call, R.string.dialog_message};
    public static int[] icons = {R.drawable.ic_call, R.drawable.ic_message};

    private Context context;
    private static LayoutInflater inflater;

    public CustomAdapter(final Object object) {
        context = (Context) object;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gets the total size of the caption array
     * @return Total size of the caption array
     */
    @Override
    public final int getCount() {
        return caption.length;
    }

    /**
     * Grabs the item in the defined position in the list
     * @param position Position of the item in the list
     * @return Object - The object in the position
     */
    @Override
    public final Object getItem(final int position) {
        return position;
    }

    /**
     *
     * @param position Position of the item in the list
     * @return long - The id of the object in the position
     */
    @Override
    public final long getItemId(final int position) {
        return position;
    }

    /**
     *
     * @param position Desired position fir the dialogs
     * @param convertView
     * @param parent Group view that holds the adapter
     * @return View - The list with the defined dialogs
     */
    @Override
    public final View getView(final int position,
                              final View convertView,
                              final ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.reporting_dialog_listitem, null);

        TextView dialogText = (TextView) rowView.findViewById(R.id.dialog_txt);
        dialogText.setText(caption[position]);

        ImageView dialogImage = (ImageView) rowView.findViewById(R.id.dialog_img);
        dialogImage.setImageResource(icons[position]);

        return rowView;
    }
}
