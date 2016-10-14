/**
 * File: CircleOfTrustFragment.java
 * Purpose: Fragment responsible for Circle of Trust view
 */

package com.peacecorps.pcsa.circle_of_trust;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.peacecorps.pcsa.Constants;
import com.peacecorps.pcsa.Constants.SmsConstants;
import com.peacecorps.pcsa.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CircleOfTrustFragment extends Fragment {
    public static final String TAG = CircleOfTrustFragment.class.getSimpleName();
    private static final String NAME_KEY = "ComradeName";
    //Code of message is not sent
    private static final int REQUEST_CODE_TRUSTEES = 1001;
    private static final int INT_ZERO = 0;

    public static final String SENT = "300";

    // Length of vibration in milliseconds
    private static final long VIBRATION_TIME = 300;
    private static final long VIBRATION_PAUSE = 200;

    // Add info about vibration pattern in intro activity
    private long[] patternSuccess = {
            // Start immediately
            0,
            VIBRATION_TIME
    };

    private long[] patternFailure = {
            // Start immediately
            0,
            // Each element then alternates between vibrate, sleep, vibrate, sleep...
            VIBRATION_TIME, VIBRATION_PAUSE, VIBRATION_TIME,
    };

    private ImageView[] comradesViews = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    private String[] phoneNumbers;
    private LocationHelper locationHelper = null;
    private Vibrator vibrator = null;

    private static boolean firstTime = false;
    private static int messageWasSent = INT_ZERO;
    private static List<Boolean> sent = new ArrayList<>();
    private ArrayList<PendingIntent> sentIntents = new ArrayList<>();
    private String numbers[];
    public static BroadcastReceiver sentReceiver;
    public static Map allNamesOfCircleOfTrust = new HashMap();

    private TextView firstComradeName, secondComradeName, thirdComradeName,
            fourthComradeName, fifthComradeName, sixthComradeName;
    private TextView[] allTextViews;

    private View rootView = null;

    private ImageButton requestButton = null;
    private ImageButton editButton = null;

    public CircleOfTrustFragment() {
        //Empty constructor is required
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     * @param savedInstanceState - If non-null, this fragment is being re-constructed from a
     *                           previous saved state as given here
     * @return View - View of the fragment
     */

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        assert inflater != null;
        assert container != null;
        assert savedInstanceState != null;

        rootView = inflater.inflate(R.layout.fragment_circle_of_trust, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.circle_title);

        settingTextView(inflater, container);

        verifySmsIsSend();

        // Get instance of Vibrator from current Context
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        settingImageButtons();

        setOnClicksListeners();

        setComradesViews();

        loadContactPhotos();
        locationHelper = new LocationHelper(getActivity());

        return rootView;
    }

    /**
     * Sends a message to the comrades' phone numbers
     * @param optionSelected - Selected option
     */
    public void sendMessage(final String optionSelected) {
        assert optionSelected != null;

        SmsManager sms = SmsManager.getDefault();
        String message = "";

        switch (optionSelected) {
            case SmsConstants.COME_GET_ME:
                Location location = locationHelper.retrieveLocation(false);

                if (location == null) {
                    message = getString(R.string.come_get_me_message);
                } else {
                    message = getString(R.string.come_get_me_message_with_location);
                    message = message.replace(Constants.TAG_LOCATION, location.getLatitude()
                            + "," + location.getLongitude());
                    String locationUrl = Constants.LOCATION_URL.replace("LAT",
                            String.valueOf(location.getLatitude())).replace("LON",
                            String.valueOf(location.getLongitude()));
                    message = message.replace(Constants.TAG_LOCATION_URL, locationUrl);
                }
                break;
            case SmsConstants.CALL_NEED_INTERRUPTION:
                message = getString(R.string.interruption_message);
                break;
            case SmsConstants.NEED_TO_TALK:
                message = getString(R.string.need_to_talk_message);
                break;
            default:
                //Nothing to do
        }

        sharedPreferences = this.getActivity().getSharedPreferences(Trustees.MY_PREFERENCES,
                Context.MODE_PRIVATE);

        if (phoneNumbers == null) {
            loadPhoneNumbers();
        } else {
            //Nothing to do
        }

        // The numbers variable holds the Comrades numbers
        numbers = phoneNumbers;

        int counter = 0;

        //Fix sending messages if the length is more than single sms limit
        ArrayList<String> parts = sms.divideMessage(message);
        int numParts = parts.size();
        for (int i = 0; i < numParts; i++) {
            sentIntents.add(PendingIntent.getBroadcast(getActivity(), 0, new Intent(
                    SENT), 0));
        }
        int numRegisteredComrades = 0;
        for (String number : numbers) {
            if (!number.isEmpty()) {
                numRegisteredComrades++;
            } else {
                //Nothing to do
            }
        }
        messageWasSent = numParts * numRegisteredComrades;
        firstTime = true;
        for (String number : numbers) {
            if (!number.isEmpty()) {
                try {
                    sms.sendMultipartTextMessage(number, null, parts, sentIntents, null);
                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            R.string.message_failed + (counter + 1),
                            Toast.LENGTH_LONG).show();
                }
                counter++;
            } else {
                //Nothing to do
            }
        }
        if (counter != 0) {
            String contentToPost;

            //For 1 comrade
            if (counter == 1) {
                contentToPost = getString(R.string.confirmation_message1)
                        + " " + counter + " " + getString(R.string.confirmation_message3) + " "
                        + getString(R.string.receive_log);
            } else {
                contentToPost = getString(R.string.confirmation_message1) + " "
                        + counter + " " + getString(R.string.confirmation_message2) + " "
                        + getString(R.string.receive_log);
                CustomAlertDialogFragment customAlertDialogFragment =
                        CustomAlertDialogFragment.newInstance(getString(R.string.msg_sent), contentToPost);
                customAlertDialogFragment.show(getActivity().getSupportFragmentManager(),
                        getString(R.string.dialog_tag));
            }
        } else {
            CustomAlertDialogFragment customAlertDialogFragment =
                    CustomAlertDialogFragment.newInstance(getString(R.string.no_comrade_title),
                            getString(R.string.no_comrade_msg));
            customAlertDialogFragment.show(getActivity().getSupportFragmentManager(),
                    getString(R.string.dialog_tag));
        }
    }

    /**
     * Verify if SMS is sent
     */
    private void verifySmsIsSend() {
        //To verify if SMS is sent
        sentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (firstTime) {
                    firstTime = false;
                    sent.clear();
                }

                boolean anyError = false;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        //Nothing to do
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        //Nothing to do
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        //Nothing to do
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        anyError = true;
                        break;
                    default:
                        //Nothing to do
                }

                sent.add(anyError);
                messageWasSent--;

                if (messageWasSent == 0) {
                    String logMessage = "";
                    for (int i = 0; i < sent.size(); ++i) {
                        if (!numbers[i].isEmpty()) {
                            if (!sent.get(i)) {
                                logMessage += numbers[i] + " : " + getString(R.string.sms_send_pass);
                            } else {
                                logMessage += numbers[i] + " : " + getString(R.string.sms_send_fail);
                            }
                            logMessage += "\n";
                        }
                    }

                    CustomAlertDialogFragment customAlertDialogFragment =
                            CustomAlertDialogFragment.newInstance(getString(R.string.log_title),
                                    logMessage);
                    customAlertDialogFragment.show(getActivity().getSupportFragmentManager(),
                            getString(R.string.dialog_tag));
                    sent.clear();
                }
            }
        };
    }

    /**
     * Checks whether the device is connected to a mobile network or not
     * @param appContext - Context of the fragment
     * @return boolean - Return true if the device is connected
     */
    public static boolean checkMobileNetworkAvailable(final Context appContext) {

        assert appContext != null;

        TelephonyManager telephonyManager =
                (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);

        boolean deviceIsConnected = false;
        if (telephonyManager.getNetworkOperator() != null
                && telephonyManager.getNetworkOperator().equals("")) {
            deviceIsConnected = false;
        } else {
            deviceIsConnected = true;
        }

        return deviceIsConnected;
    }

    /**
     * Loads contact photos from the device saved contacts for comrades' numbers
     */
    private void loadContactPhotos() {

        if (phoneNumbers == null) {
            loadPhoneNumbers();
        }

        //reset comrades to defaults
        for (ImageView view:comradesViews) {
            view.setImageResource(R.drawable.ic_comrade);
        }

        for (int i = 0; i < phoneNumbers.length; i++) {
            String number = phoneNumbers[i];
            if (number != null && number.length() > 0) {
                ContactPhotoLoader contactPhotoLoader = new ContactPhotoLoader();
                contactPhotoLoader.setContext(this.getActivity());
                ImageView button = null;
                if (comradesViews.length > i) {
                    button = comradesViews[i];
                }

                if (button != null) {
                    contactPhotoLoader.setOutputView(button);
                    contactPhotoLoader.execute(number);
                }
            }
        }
    }

    /**
     * Sets the ImageButtons of the CircleOfTrustFragment view
     */

    private void settingImageButtons() {
        requestButton = (ImageButton) rootView.findViewById(R.id.requestButton);
        editButton = (ImageButton) rootView.findViewById(R.id.editButton);
    }

    /**
     * Sets the TextViews of the CircleOfTrustFragment view
     * @param inflater - Object used to inflate any views in the fragment
     * @param container - If non-null, is the parent view that the fragment should be attached to
     */
    private void settingTextView(final LayoutInflater inflater,
                                 final ViewGroup container) {
        assert rootView != null;
        assert inflater != null;
        assert container != null;

        firstComradeName = (TextView) rootView.findViewById(R.id.com1ButtonName);
        secondComradeName = (TextView) rootView.findViewById(R.id.com2ButtonName);
        thirdComradeName = (TextView) rootView.findViewById(R.id.com3ButtonName);
        fourthComradeName = (TextView) rootView.findViewById(R.id.com4ButtonName);
        fifthComradeName = (TextView) rootView.findViewById(R.id.com5ButtonName);
        sixthComradeName = (TextView) rootView.findViewById(R.id.com6ButtonName);

        final String MY_PREFERENCES = "MyPreference";

        sharedPreferences = getActivity().getSharedPreferences(MY_PREFERENCES,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        allTextViews = new TextView[]{
                firstComradeName,
                secondComradeName,
                thirdComradeName,
                fourthComradeName,
                fifthComradeName,
                sixthComradeName};

        for (int i = 0; i < allTextViews.length; ++i) {
            allTextViews[i].setText(sharedPreferences.getString(NAME_KEY + i,
                    getString(R.string.unregistered)));
        }

    }

    /**
     * Set comrades views of the CircleOfTrustFragment view
     */

    private void setComradesViews() {
        comradesViews = new ImageView[]{(ImageView) rootView.findViewById(R.id.com1Button),
                (ImageView) rootView.findViewById(R.id.com2Button),
                (ImageView) rootView.findViewById(R.id.com3Button),
                (ImageView) rootView.findViewById(R.id.com4Button),
                (ImageView) rootView.findViewById(R.id.com5Button),
                (ImageView) rootView.findViewById(R.id.com6Button)};
    }

    /**
     * Sets on clicks of buttons of the CircleOfTrustFragment view
     */
    private void setOnClicksListeners() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityForResult(new Intent(getActivity(), Trustees.class),
                        REQUEST_CODE_TRUSTEES);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (checkMobileNetworkAvailable(getActivity())) {
                    if (vibrator.hasVibrator()) {
                        // Only perform success pattern one time (-1 means "do not repeat")
                        vibrator.vibrate(patternSuccess, -1);
                    }

                    MessageDialogBox messageDialogBox =
                            MessageDialogBox.newInstance(CircleOfTrustFragment.this, getActivity());
                    messageDialogBox.show(getActivity().getSupportFragmentManager(),
                            getString(R.string.message_options));

                } else {
                    if (vibrator.hasVibrator()) {
                        // Only perform failure pattern one time (-1 means "do not repeat")
                        vibrator.vibrate(patternFailure, -1);
                    }
                    Toast.makeText(getActivity(), R.string.network_unavailable,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Receive the result from a previous call to
     * @param requestCode - The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode - The integer result code returned by the child activity
     *                   through its setResult().
     * @param data - An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        assert data != null;

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TRUSTEES) {
            refreshPhotos();
            Iterator it = allNamesOfCircleOfTrust.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                allTextViews[(Integer) pair.getKey() - 1].setText(pair.getValue().toString());
                editor.putString(NAME_KEY + ((Integer) pair.getKey() - 1),
                        pair.getValue().toString());
            }

            for (int i = 0; i < Trustees.NUMBER_OF_COMRADES; i++) {
                if (!allNamesOfCircleOfTrust.containsKey(i + 1) && !(phoneNumbers[i].isEmpty())) {
                    allTextViews[i].setText(phoneNumbers[i]);
                    editor.putString(NAME_KEY + i, phoneNumbers[i]);
                }
                if (phoneNumbers[i].isEmpty()) {
                    allTextViews[i].setText(getString(R.string.unregistered));
                    editor.putString(NAME_KEY + i, getString(R.string.unregistered));
                }
            }
            editor.commit();
        }
    }

    /**
     * Retrieve phone numbers saved in Trustees
     * @return true if the number retrieval is success
     */
    private boolean loadPhoneNumbers() {
        sharedPreferences = this.getActivity().getSharedPreferences(Trustees.MY_PREFERENCES,
                Context.MODE_PRIVATE);
        try {

            phoneNumbers = new String[Trustees.NUMBER_OF_COMRADES];
            for (int i = 0; i < Trustees.NUMBER_OF_COMRADES; i++) {
                phoneNumbers[i] = sharedPreferences.getString(Trustees.COMRADE_KEY.get(i), "");
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "Unable to load comrades numbers from shared preferences", e);
        }

        return false;
    }

    /**
     * Invalidate current phone numbers and load again with contact photos
     */
    private void refreshPhotos() {
        phoneNumbers = null;
        loadContactPhotos();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     */

    @Override
    public void onResume() {
        super.onResume();
        locationHelper.startAcquiringLocation();
    }

    /**
     * Called when the Fragment is no longer resumed.
     */

    @Override
    public void onPause() {
        super.onPause();
        locationHelper.stopAcquiringLocation();
    }
}
