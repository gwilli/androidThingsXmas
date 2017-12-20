package io.atomicrobot.androidthingsxmas;

import android.app.Activity;
import android.content.Intent;
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
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Starting MainActivity");

    }

    public void startRandomBlinking(View view) {
        startActivity(new Intent(this, RandomBlinkActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove pending blink Runnable from the handler.

        // Close the Gpio pins.
    }

}
