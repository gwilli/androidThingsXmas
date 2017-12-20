package io.atomicrobot.androidthingsxmas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

import io.atomicrobot.androidthingsxmas.util.GpioUtility;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * ledGpio = service.openGpio("BCM6");
 * ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * ledGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class RandomBlinkActivity extends Activity {

    private static final String TAG = RandomBlinkActivity.class.getSimpleName();

    private Gpio[] ledGpios;
    private BlinkRunnable[] blinkRunnables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_blink);

        Log.i(TAG, "Starting RandomBlinkActivity");
        init();
    }

    private void init() {

        String[] pinNames = GpioUtility.getGpioPinNames(2, 27);
        ledGpios = new Gpio[pinNames.length];
        blinkRunnables = new BlinkRunnable[pinNames.length];

        PeripheralManagerService service = new PeripheralManagerService();
        for (int i=0; i< pinNames.length; i++) {
            String pinName = pinNames[i];
            try {
                ledGpios[i] = service.openGpio(pinName);
                ledGpios[i].setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                ledGpios[i].setValue(false);

                Log.i(TAG, "Start blinking LED GPIO pin " + pinName);
                // Post a Runnable that continuously switch the state of the GPIO, blinking the
                // corresponding LED
                blinkRunnables[i] = new BlinkRunnable(ledGpios[i], true);

            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            }
        }
    }

    private void start() {
        for (BlinkRunnable blinkRunnable : blinkRunnables) {
            blinkRunnable.getHandler().post(blinkRunnable);
        }
    }

    private void stop() {

        // Remove pending blink Runnable from the handler.
        for (BlinkRunnable blinkRunnable : blinkRunnables) {
            blinkRunnable.getHandler().removeCallbacks(blinkRunnable);
        }

    }

    public void start(View view) {
        view.setEnabled(false);
        start();
        findViewById(R.id.buttonStop).setEnabled(true);
    }

    public void stop(View view) {
        view.setEnabled(false);
        stop();
        findViewById(R.id.buttonStart).setEnabled(true);
    }

    public void exit(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stop();

        // Close the Gpio pins.
        for (int i=0; i<ledGpios.length; i++) {
            Log.i(TAG, "Closing LED GPIO pin " + ledGpios[i].getName());
            try {
                ledGpios[i].close();
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            } finally {
                ledGpios[i] = null;
            }
        }
    }

}