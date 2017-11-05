package com.example.shubhraj.convertor;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private float[] conversionValues = {100f, 0.001f, 39.3701f};
    private String[] conversionLabels = {"Centimeters", "Kilometers", "Inch"};
    private String[] shortConversionLabels = {"cm", "km", "in"};

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.settings_label);

        createRadioButtons();
    }

    private float getActualUnits()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file),
                MODE_PRIVATE);
        return sharedPreferences.getFloat(getString(R.string.pref_unit_label),100f);
    }

    private void createRadioButtons() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        for(int i=0; i<conversionLabels.length; i++)
        {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(getString(R.string.meters_to_label,conversionLabels[i]));
            final float value = conversionValues[i];
            final String shortLabel = shortConversionLabels[i];

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setActualUnits(value, shortLabel);
                }
            });
            mRadioGroup.addView(radioButton);

            if (value == getActualUnits())
            {
                radioButton.setChecked(true);
            }
        }
    }

    private void setActualUnits(float value, String shortLabel) {
        SharedPreferences mPreferences = getSharedPreferences(getString(R.string.shared_pref_file)
                ,MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putFloat(getString(R.string.pref_unit_label), value);
        editor.putString(getString(R.string.pref_label), shortLabel);
        editor.apply();
    }
}
