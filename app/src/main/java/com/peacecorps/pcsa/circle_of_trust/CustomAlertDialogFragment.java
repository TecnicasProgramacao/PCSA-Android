/**
 * File: CustomAlertDialogFragment.java
 * Purpose: A template for alert dialogs which display the user about confirmation of
 * message being sent to the comrades
 */

package com.peacecorps.pcsa.circle_of_trust;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.peacecorps.pcsa.R;

public class CustomAlertDialogFragment extends DialogFragment {

    /**
     * User about confirmation of message being sent to the comrades
     * @param title - Title of the dialog fragment
     * @param content - Content of the view
     * @return CustomAlertDialogFragment -
     */

    public static CustomAlertDialogFragment newInstance(final String title, final String content) {
        assert title != null;
        assert content != null;

        CustomAlertDialogFragment customAlertDialogFragment = new CustomAlertDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        customAlertDialogFragment.setArguments(args);

        return customAlertDialogFragment;
    }

    /**
     * Used to show an AlertDialog instead of a generic Dialog
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     * @return Return a new Dialog instance to be displayed by the Fragment.
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        assert savedInstanceState != null;

        String title = getArguments().getString("title");
        String content = getArguments().getString("content");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Title bar string
        builder.setTitle(title);
        builder.setPositiveButton(R.string.ok, null);

        builder.setMessage(content);
        AlertDialog errorDialog = builder.create();

        return errorDialog;
    }
}
