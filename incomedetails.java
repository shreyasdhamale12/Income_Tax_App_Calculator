package com.example.incometaxappcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class incomedetails extends AppCompatActivity {

    EditText incomeFromSalary, exceptAllowance, incomeFromInterest, interestHomeLoan, rentIncome, incomeHomeLoanLetOut, incomeFromDigitalAssets, otherIncome;
    Button nextButton;
    private ImageView backIcon_IncomeD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomedetails);

        TextView textView = findViewById(R.id.textView7);

        // Original text
        String text = "1. Income from Salary *";

        // Create a SpannableString
        SpannableString spannableString = new SpannableString(text);

        // Apply red color to the "*" character
        int start = text.indexOf("*");  // Get the index of the "*"
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the SpannableString to the TextView
        textView.setText(spannableString);

        // Reference to all EditText fields
        incomeFromSalary = findViewById(R.id.editTextNumber_income);
        exceptAllowance = findViewById(R.id.editTextNumber_ExceptAllowance);
        incomeFromInterest = findViewById(R.id.editTextNumber_IncomeFromInterest);
        interestHomeLoan = findViewById(R.id.editTextNumber_InterestHomeLoan);
        rentIncome = findViewById(R.id.editTextNumber_RentIncome);
        incomeHomeLoanLetOut = findViewById(R.id.editTextNumber_IncomeHome_LoanLetOut);
        incomeFromDigitalAssets = findViewById(R.id.editTextNumber_IncomeFromDigitalAssests);
        otherIncome = findViewById(R.id.editTextNumber_OtherIncome);
        nextButton = findViewById(R.id.button_Next_IncomeDetails);
        backIcon_IncomeD = findViewById(R.id.imageView2);

        backIcon_IncomeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This finishes the current activity and returns to the previous one
            }
        });




        // Set click listener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if "Income from Salary" field is not empty
                if (incomeFromSalary.getText().toString().isEmpty()) {
                    // Show a toast message if the field is empty
                    Toast.makeText(incomedetails.this, "Please enter your Income from Salary.", Toast.LENGTH_SHORT).show();
                } else {

                    // Create a new Bundle to store all data
                    Bundle bundle = new Bundle();
                    String selectedFY = bundle.getString("SELECTED_FY");
                    String selectedAge = bundle.getString("SELECTED_AGE");


                    // BasicDetails
                    bundle.putString("SELECTED_FY", selectedFY);
                    bundle.putString("SELECTED_AGE", selectedAge);

                    // // IncomeDetails
                    bundle.putString("incomeFromSalary", incomeFromSalary.getText().toString());
                    bundle.putString("exceptAllowance", exceptAllowance.getText().toString());
                    bundle.putString("incomeFromInterest", incomeFromInterest.getText().toString());
                    bundle.putString("interestHomeLoan", interestHomeLoan.getText().toString());
                    bundle.putString("rentIncome", rentIncome.getText().toString());
                    bundle.putString("incomeHomeLoanLetOut", incomeHomeLoanLetOut.getText().toString());
                    bundle.putString("incomeFromDigitalAssets", incomeFromDigitalAssets.getText().toString());
                    bundle.putString("otherIncome", otherIncome.getText().toString());

                    // Pass the bundle to the next activity (deductions.java)
                    Intent intent = new Intent(incomedetails.this, deductions.class);
                    intent.putExtras(bundle);  // Attach the bundle to the intent
                    startActivity(intent);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
