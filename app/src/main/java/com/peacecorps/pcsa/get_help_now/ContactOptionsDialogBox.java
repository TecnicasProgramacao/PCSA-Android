/**
 * File: ContactOptionsDialogBox.java
 * Purpose: Create a new box containing dialog options
 */

package com.peacecorps.pcsa.get_help_now;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;

public class ContactOptionsDialogBox extends ListDialogBox {

    /**
     * Creates a new instance that will be used to create a box of dialog
     * @param title - Title of the dialog fragment
     * @param context - Context of the view
     * @param clickListener - An AdapterView is a view whose children are
     * determined by an Adapter and he has their aggregate listener
     * @return ContactOptionsDialogBox - Box option dialog created
     */

    public static ContactOptionsDialogBox newInstance(final String title,
                                                      final Context context,
                                                      final AdapterView.OnItemClickListener
                                                              clickListener) {
        assert title != null;
        assert context != null;
        assert  clickListener != null;

        final String BUNDLE_TITLE_STRING = "title";

        ContactOptionsDialogBox customAlertDialogContext = new ContactOptionsDialogBox();

        Bundle args = new Bundle();
        args.putString(BUNDLE_TITLE_STRING, title);

        customAlertDialogContext.setArguments(args);
        customAlertDialogContext.clickListener = clickListener;
        customAlertDialogContext.context = context;

        return customAlertDialogContext;
    }

    /**
     * This adapter is to initialise views of the customised dialog box.
     * @return ListAdapter - Adapter that will be initialise views of the customised dialob box
     */
    @Override
    protected ListAdapter getListAdapter() {
        return new CustomAdapter(context);
    }

    /**
     * Providing functionality to the list items (Call and Message)
     * @return AdapterView.OnItemClickListener - An AdapterView is a view whose children are
     * determined by an Adapter and he has their aggregate listener
     */
    @Override
    protected AdapterView.OnItemClickListener getItemClickListener() {
        return (AdapterView.OnItemClickListener) clickListener;
    }
}
