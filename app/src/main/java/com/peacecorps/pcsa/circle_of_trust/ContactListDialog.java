package com.peacecorps.pcsa.circle_of_trust;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.peacecorps.pcsa.get_help_now.ListDialogBox;

// Dialog for showing multiple contact selection dialog
public class ContactListDialog extends ListDialogBox {

    private Object[] elements;
    private AdapterView.OnItemClickListener listener;

    public static ContactListDialog newInstance(final Context context, final String title, final Object[] elements) {
        Bundle args = new Bundle();
        args.putString("title", title);
        ContactListDialog fragment = new ContactListDialog();
        fragment.context = context;
        fragment.setArguments(args);
        fragment.elements = elements;
        return fragment;
    }

    @Override
    protected final ListAdapter getListAdapter() {
        return new ContactListAdapter(context, elements);
    }

    @Override
    protected final AdapterView.OnItemClickListener getItemClickListener() {
        return listener;
    }

    public final void setListener(final AdapterView.OnItemClickListener listener) {
        this.listener = listener;
    }
}
