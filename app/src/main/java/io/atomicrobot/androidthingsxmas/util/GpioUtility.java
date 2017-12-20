package io.atomicrobot.androidthingsxmas.util;

import android.util.Log;

import io.atomicrobot.androidthingsxmas.MainActivity;

/**
 * Created by greg on 12/20/17.
 */

public class GpioUtility {

    private static final String TAG = GpioUtility.class.getSimpleName();

    public static String[] getGpioPinNames(int start, int end) {
        Log.i(TAG, String.format("getGpioPinNames from %d to %d inclusive", start, end));
        int length = end-start+1;
        String[] pinNames = new String[length];
        for (int i=0; i<length; i++) {
            pinNames[i] = getGpioPinName(i+start);
        }
        return pinNames;
    }

    public static String getGpioPinName(int pinNumber) {
        return "BCM" + pinNumber;
    }
}
