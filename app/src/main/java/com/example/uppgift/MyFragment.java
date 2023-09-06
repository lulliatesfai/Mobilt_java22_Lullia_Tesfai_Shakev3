package com.example.uppgift;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyFragment extends Fragment {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private TextView accelerometerTextView;
    private TextView gyroscopeTextView;
    private Button myButton;
    private View mainLayout;
    private int[] colors = {R.color.holo_red_light, R.color.holo_green_light, R.color.holo_blue_light};
    private int currentColorIndex = 0; // Index to track the current color

    private float accelerometerThreshold = 10.0f;
    private float currentRotation = 0f;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        // Hämtar referens till sensormanager och sensorer
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Hämta en referens till TextViews i fragmentets layout
        accelerometerTextView = view.findViewById(R.id.accelerometerTextView);
        gyroscopeTextView = view.findViewById(R.id.gyroscopeTextView);
        myButton = view.findViewById(R.id.myButton);

        // Lägg till lyssnare för att få sensordata
        sensorManager.registerListener(
                accelerometerListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
        );
        sensorManager.registerListener(
                gyroscopeListener,
                gyroscope,
                SensorManager.SENSOR_DELAY_NORMAL
        );

        mainLayout = view.findViewById(R.id.LinearLayout);

        myButton = view.findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event here
                Toast.makeText(getContext(), "Button clicked!", Toast.LENGTH_SHORT).show();

                // Fetch the actual color value associated with the resource ID
                int colorResId = colors[currentColorIndex];
                int colorValue = getResources().getColor(colorResId);

                // Set the background color of mainLayout
                mainLayout.setBackgroundColor(colorValue);

                currentColorIndex = (currentColorIndex + 1) % colors.length;
            }
        });

        ImageView myImageView = view.findViewById(R.id.myImageView);
        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rotera bilden med en viss vinkel (90 grader) när användaren klickar på den
                currentRotation += 90f;
                myImageView.setRotation(currentRotation);
            }
        });

        return view;
    }

    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Hantera accelerometerdata här
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Visa datan i en TextView
            accelerometerTextView.setText("Accelerometer: X=" + x + ", Y=" + y + ", Z=" + z);

            // Kontrollera om tröskelvärden nås och visa en Toast eller logga
            if (Math.sqrt(x * x + y * y + z * z) > accelerometerThreshold) {
                Toast.makeText(getContext(), "Accelerometer threshold reached", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // Händler noggrannhetsändringar om det behövs
        }
    };

    private final SensorEventListener gyroscopeListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // Hantera gyroskopdata här
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Visa gyroskopdata i en annan TextView
            gyroscopeTextView.setText("Gyroskop: X=" + x + ", Y=" + y + ", Z=" + z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // Hanterar noggrannhetsändringar om det behövs
        }
    };
}