package io.atomicrobot.androidthingsxmas;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;

import java.io.IOException;

/**
 * Created by greg on 12/20/17.
 */
class BlinkRunnable implements Runnable {

    private static final String TAG = BlinkRunnable.class.getSimpleName();
    private static final int INTERVAL_BETWEEN_BLINKS_MS = 1000;

    private Gpio ledGpio;
    private boolean ledState;
    private Handler handler;
    private boolean randomInterval;

    public BlinkRunnable(Gpio ledGpio) {
        this(ledGpio, false);
    }

    public BlinkRunnable(Gpio ledGpio, boolean randomInterval) {
        this.ledGpio = ledGpio;
        this.randomInterval = randomInterval;
        handler = new Handler();
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void run() {
        // Exit Runnable if the GPIO is already closed
        if (ledGpio == null) {
            return;
        }

        try {
            // Toggle the GPIO state
            ledState = !ledState;
            ledGpio.setValue(ledState);
            Log.d(TAG, ledGpio.getName() + " state set to " + ledState);

            if (randomInterval) {
                handler.postDelayed(this, Math.round(Math.random() * INTERVAL_BETWEEN_BLINKS_MS));
            } else {
                // Reschedule the same runnable in {#INTERVAL_BETWEEN_BLINKS_MS} milliseconds
                handler.postDelayed(this, INTERVAL_BETWEEN_BLINKS_MS);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }
}
