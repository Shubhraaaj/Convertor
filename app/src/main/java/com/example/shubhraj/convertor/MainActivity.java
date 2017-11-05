package com.example.shubhraj.convertor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText numberInput;
    private TextView scaleTypeText, resultText;
    private Button convertButton;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        convertButton = (Button) findViewById(R.id.convert_button);
        scaleTypeText = (TextView) findViewById(R.id.scale_type);
        resultText = (TextView) findViewById(R.id.result_text);
        numberInput = (EditText) findViewById(R.id.number_input);

        setDefaultText();

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextValue = numberInput.getText().toString();
                if (editTextValue.length()>0)
                    convert(Float.parseFloat(editTextValue));
            }
        });

    }

    private void setDefaultText() {
        scaleTypeText.setText(getString(R.string.scale_text_label, getActualUnitsLabel()));
        numberInput.setText("");
        resultText.setText(getString(R.string.result,"0"));
    }

    private String getActualUnitsLabel()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file),
                MODE_PRIVATE);
        return sharedPreferences.getString(getString(R.string.pref_label),"cm");
    }

    private void convert(float v) {
        resultText.setText(getString(R.string.result, String.valueOf(v * getActualUnits())));
    }

    private float getActualUnits() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_pref_file)
                , MODE_PRIVATE);
        return sharedPreferences.getFloat(getString(R.string.pref_unit_label),100f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        scaleTypeText.setText(getString(R.string.scale_text_label, getActualUnitsLabel()));
        String editTextValue = numberInput.getText().toString();
        if (editTextValue.length()>0)
            convert(Float.parseFloat(editTextValue));
    }
}
