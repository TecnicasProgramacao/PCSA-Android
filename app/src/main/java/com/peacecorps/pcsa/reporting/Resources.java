/**
 * File: Resources.java
 * Purpose: Resources for users to learn more about Reporting
 */

package com.peacecorps.pcsa.reporting;

import android.app.Activity;
import android.os.Bundle;

import com.peacecorps.pcsa.R;

public class Resources extends Activity {

    /**
     * Called when the activity is starting.
     * @param savedInstanceState -It contains data provided by the activity previously initialized
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        assert savedInstanceState != null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_resources);

    }
}
