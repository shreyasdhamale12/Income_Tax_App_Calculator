package com.example.incometaxappcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class basicdetails extends AppCompatActivity {

    private RadioGroup radioGroupFY;
    private RadioGroup radioGroupAge;
    private Button buttonSubmit;
    private ImageView backIcon_basicD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basicdetails);

        radioGroupFY = findViewById(R.id.radioGroupFY);
        radioGroupAge = findViewById(R.id.radioGroupAge);
        buttonSubmit = findViewById(R.id.button_Submit_basicDetails);
        backIcon_basicD = findViewById(R.id.imageView2);  // ImageView for the back button

        // Set an OnClickListener on the ImageView to go back to the previous activity
        backIcon_basicD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This finishes the current activity and returns to the previous one
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedFYId = radioGroupFY.getCheckedRadioButtonId();
                int selectedAgeId = radioGroupAge.getCheckedRadioButtonId();

                if (selectedFYId != -1 && selectedAgeId != -1) {
                    RadioButton selectedFYButton = findViewById(selectedFYId);
                    RadioButton selectedAgeButton = findViewById(selectedAgeId);

                    String selectedFY = selectedFYButton.getText().toString();
                    String selectedAge = selectedAgeButton.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("SELECTED_FY", selectedFY);
                    bundle.putString("SELECTED_AGE", selectedAge);

                    // Create an Intent to pass the Bundle to the next activity
                    Intent intent = new Intent(basicdetails.this, incomedetails.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    StringBuilder message = new StringBuilder("Please select: ");
                    if (selectedFYId == -1) {
                        message.append("Financial Year");
                    }
                    if (selectedAgeId == -1) {
                        message.append(selectedFYId == -1 ? " and Age" : "Age");
                    }
                    Toast.makeText(basicdetails.this, message.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
