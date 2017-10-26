package com.peacecorps.pcsa.get_help_now;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.peacecorps.pcsa.R;

/**
 * Shows details of the Other Staff members to contact in case of crime
 * Allows user to call/send SMS to Other Staff members
 *
 * @author Buddhiprabha Erabadda
 * @since 07-08-2015
 */
public class OtherStaffContent extends Fragment implements AdapterView.OnItemClickListener {

    public static final String CONTACT_NUMBER = "contactNumber";
    public static final String CONTACT_NAME = "contactName";
    public static final String CONTACT_DESC = "contatDesc";
    public static final String TAG = OtherStaffContent.class.getSimpleName();
    static String contactNumber;

    @Nullable
    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reporting_other_staff_content,
                container, false);

        TextView contactName = (TextView) rootView.findViewById(R.id.reporting_contact_other_content);
        TextView contactDescription = (TextView) rootView.findViewById(R.id.reporting_contact_description);
        Button contactNow = (Button) rootView.findViewById(R.id.contact_now);

        final Bundle details = getArguments();
        contactNumber = details.getString(CONTACT_NUMBER);

        contactName.setText(details.getString(CONTACT_NAME));
        contactDescription.setText(details.getString(CONTACT_DESC));
        contactNow.setText("Contact Now");
        contactNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String contact = getString(R.string.contact);
                String contactName = details.getString(CONTACT_NAME);
                String viaString = getString(R.string.via);
                String dialogString = contact + " " + contactName + " " + viaString;

                ContactOptionsDialogBox contactOptionsDialogBox =
                        ContactOptionsDialogBox.newInstance(dialogString,
                                getActivity(),
                                OtherStaffContent.this);

                contactOptionsDialogBox.show(getActivity().getSupportFragmentManager(),
                        getString(R.string.dialog_tag));
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.reporting_get_help);
        return rootView;
    }

    /**
     * Interface definition for a callback to be invoked when
     * an item in this AdapterView has been clicked.
     *
     * @param parent The AdapterView where the click happened.
     * @param view  The view within the AdapterView that was clicked
     *               (this will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    @Override
    public final void onItemClick(final AdapterView<?> parent,
                                  final View view,
                                  final int position,
                                  final long id) {

        //For Voice Call
        if (position == 1) {
            Intent callingIntent = new Intent(Intent.ACTION_CALL);
            callingIntent.setData(Uri.parse("tel:" + contactNumber));

            startActivity(callingIntent);
        } else if (position == 2) { //For Message
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + contactNumber));

            startActivity(smsIntent);
        }
    }
}
