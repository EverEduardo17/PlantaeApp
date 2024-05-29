package com.uv.app_plantae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Sensor extends AppCompatActivity {

    private TextView sensores;
    private ImageView sensor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensores = (TextView) findViewById(R.id.textView10);
        sensor1 = (ImageView) findViewById(R.id.image_sensor);

    }
}