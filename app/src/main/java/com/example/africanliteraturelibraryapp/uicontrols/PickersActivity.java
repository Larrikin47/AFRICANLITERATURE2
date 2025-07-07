package com.example.africanliteraturelibraryapp.uicontrols;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;

import java.util.Calendar;

public class PickersActivity extends AppCompatActivity {

    private TextView selectedDateTextView;
    private TextView selectedTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickers);

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);

        Button datePickerButton = findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        Button timePickerButton = findViewById(R.id.timePickerButton);
        timePickerButton.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    selectedDateTextView.setText("Selected Date: " + date);
                    Toast.makeText(PickersActivity.this, "Date: " + date, Toast.LENGTH_SHORT).show();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    String time = hourOfDay + ":" + minute1;
                    selectedTimeTextView.setText("Selected Time: " + time);
                    Toast.makeText(PickersActivity.this, "Time: " + time, Toast.LENGTH_SHORT).show();
                }, hour, minute, false); // false for 12-hour format, true for 24-hour
        timePickerDialog.show();
    }
}
