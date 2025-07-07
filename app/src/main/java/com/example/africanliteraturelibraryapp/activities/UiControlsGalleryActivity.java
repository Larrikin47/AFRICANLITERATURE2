package com.example.africanliteraturelibraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.example.africanliteraturelibraryapp.uicontrols.AutoCompleteActivity;
import com.example.africanliteraturelibraryapp.uicontrols.CheckBoxesActivity;
import com.example.africanliteraturelibraryapp.uicontrols.MapViewActivity;
import com.example.africanliteraturelibraryapp.uicontrols.PickersActivity;
import com.example.africanliteraturelibraryapp.uicontrols.RadioButtonsActivity;
import com.example.africanliteraturelibraryapp.uicontrols.SpinnerActivity;
import com.example.africanliteraturelibraryapp.uicontrols.ToggleButtonActivity;

public class UiControlsGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_controls_gallery);

        findViewById(R.id.pickersButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, PickersActivity.class)));

        findViewById(R.id.mapViewButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, MapViewActivity.class)));

        findViewById(R.id.autoCompleteButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, AutoCompleteActivity.class)));

        findViewById(R.id.spinnerButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, SpinnerActivity.class)));

        findViewById(R.id.radioButtonsButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, RadioButtonsActivity.class)));

        findViewById(R.id.checkBoxesButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, CheckBoxesActivity.class)));

        findViewById(R.id.toggleButtonButton).setOnClickListener(v ->
                startActivity(new Intent(UiControlsGalleryActivity.this, ToggleButtonActivity.class)));
    }
}
