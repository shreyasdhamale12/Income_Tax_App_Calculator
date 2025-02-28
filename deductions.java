package com.example.incometaxappcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class deductions extends AppCompatActivity {

    RadioGroup radioGroupRegime;
    RadioButton oldRegime, newRegime;
    EditText basicDeductions, interestFromDeposit, medicalInsurance, npsContribution, educationLoan, housingLoan, charityDonation;
    TextView textViewBasicDeductions, textViewInterestDeposit, textViewMedicalInsurance, textViewNPSContribution, textViewEducationLoan, textViewHousingLoan, textViewCharityDonation;
    Button nextButton;
    private ImageView backIcon_Deductions;

    // Variables to store the values passed from the previous activity
    String selectedFY, selectedAge, incomeFromSalary, exceptAllowance, incomeFromInterest, interestHomeLoan, rentIncome, incomeHomeLoanLetOut, incomeFromDigitalAssets, otherIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deductions);

        // Reference to all RadioButtons, EditTexts, and TextViews
        radioGroupRegime = findViewById(R.id.radioGroupRegime);
        oldRegime = findViewById(R.id.radioButton6);
        newRegime = findViewById(R.id.radioButton7);
        newRegime.setChecked(true);

        basicDeductions = findViewById(R.id.editTextNumber10);
        interestFromDeposit = findViewById(R.id.editTextNumber11);
        medicalInsurance = findViewById(R.id.editTextNumber12);
        npsContribution = findViewById(R.id.editTextNumber13);
        educationLoan = findViewById(R.id.editTextNumber14);
        housingLoan = findViewById(R.id.editTextNumber15);
        charityDonation = findViewById(R.id.editTextNumber16);

        textViewBasicDeductions = findViewById(R.id.textView17);
        textViewInterestDeposit = findViewById(R.id.textView18);
        textViewMedicalInsurance = findViewById(R.id.textView19);
        textViewNPSContribution = findViewById(R.id.textView20);
        textViewEducationLoan = findViewById(R.id.textView21);
        textViewHousingLoan = findViewById(R.id.textView22);
        textViewCharityDonation = findViewById(R.id.textView23);

        nextButton = findViewById(R.id.button3);
        backIcon_Deductions = findViewById(R.id.imageView2);



        // Retrieve values from the Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedFY = bundle.getString("SELECTED_FY");
            selectedAge = bundle.getString("SELECTED_AGE");
            incomeFromSalary = bundle.getString("incomeFromSalary");
            exceptAllowance = bundle.getString("exceptAllowance");
            incomeFromInterest = bundle.getString("incomeFromInterest");
            interestHomeLoan = bundle.getString("interestHomeLoan");
            rentIncome = bundle.getString("rentIncome");
            incomeHomeLoanLetOut = bundle.getString("incomeHomeLoanLetOut");
            incomeFromDigitalAssets = bundle.getString("incomeFromDigitalAssets");
            otherIncome = bundle.getString("otherIncome");
        }

        // Hide all fields except for basic deductions initially
        hideFieldsForNewRegime();

        // Set listener for radio button change
        radioGroupRegime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton7) {
                    // New Regime selected
                    hideFieldsForNewRegime();
                } else {
                    // Old Regime selected
                    showAllFields();
                }
            }
        });

        backIcon_Deductions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This finishes the current activity and returns to the previous one
            }
        });

        // Set click listener for the "Next" button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if either Old Regime or New Regime is selected
                if (radioGroupRegime.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(deductions.this, "Please select either Old Regime or New Regime.", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the selected regime
                    String selectedRegime = oldRegime.isChecked() ? "Old Regime" : "New Regime";

                    // Get other values (use default value if EditText is empty)
                    String basicDeduction = basicDeductions.getText().toString().isEmpty() ? "0" : basicDeductions.getText().toString();
                    String interestDeposit = interestFromDeposit.getText().toString().isEmpty() ? "0" : interestFromDeposit.getText().toString();
                    String medicalIns = medicalInsurance.getText().toString().isEmpty() ? "0" : medicalInsurance.getText().toString();
                    String npsContrib = npsContribution.getText().toString().isEmpty() ? "0" : npsContribution.getText().toString();
                    String eduLoan = educationLoan.getText().toString().isEmpty() ? "0" : educationLoan.getText().toString();
                    String housingLoanValue = housingLoan.getText().toString().isEmpty() ? "0" : housingLoan.getText().toString();
                    String charityValue = charityDonation.getText().toString().isEmpty() ? "0" : charityDonation.getText().toString();

                    // Create a bundle to store all values and pass it to the next activity
                    Bundle newBundle = new Bundle();
                    newBundle.putString("selectedRegime", selectedRegime);
                    newBundle.putString("basicDeduction", basicDeduction);
                    newBundle.putString("interestDeposit", interestDeposit);
                    newBundle.putString("medicalInsurance", medicalIns);
                    newBundle.putString("npsContribution", npsContrib);
                    newBundle.putString("educationLoan", eduLoan);
                    newBundle.putString("housingLoan", housingLoanValue);
                    newBundle.putString("charityDonation", charityValue);

                    // Pass income details
                    newBundle.putString("incomeFromSalary", incomeFromSalary);
                    newBundle.putString("exceptAllowance", exceptAllowance);
                    newBundle.putString("incomeFromInterest", incomeFromInterest);
                    newBundle.putString("interestHomeLoan", interestHomeLoan);
                    newBundle.putString("rentIncome", rentIncome);
                    newBundle.putString("incomeHomeLoanLetOut", incomeHomeLoanLetOut);
                    newBundle.putString("incomeFromDigitalAssets", incomeFromDigitalAssets);
                    newBundle.putString("otherIncome", otherIncome);

                    // Pass basic details
                    newBundle.putString("SELECTED_FY", selectedFY);
                    newBundle.putString("SELECTED_Age", selectedAge);

                    // Start the summary activity and pass the bundle
                    try {
                        Intent intent = new Intent(deductions.this, DemoActivity.class);
                        intent.putExtras(newBundle);
                        startActivity(intent);
                    }catch (Exception e){
                        System.out.println(e);
                    }

                }
            }
        });

        // Handle window insets (for devices with notches, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Hide all fields except Basic Deductions for New Regime
    private void hideFieldsForNewRegime() {
        textViewInterestDeposit.setVisibility(View.GONE);
        interestFromDeposit.setVisibility(View.GONE);
        textViewMedicalInsurance.setVisibility(View.GONE);
        medicalInsurance.setVisibility(View.GONE);
        textViewNPSContribution.setVisibility(View.GONE);
        npsContribution.setVisibility(View.GONE);
        textViewEducationLoan.setVisibility(View.GONE);
        educationLoan.setVisibility(View.GONE);
        textViewHousingLoan.setVisibility(View.GONE);
        housingLoan.setVisibility(View.GONE);
        textViewCharityDonation.setVisibility(View.GONE);
        charityDonation.setVisibility(View.GONE);
    }


        // Show all fields for Old Regime
    private void showAllFields() {
        textViewInterestDeposit.setVisibility(View.VISIBLE);
        interestFromDeposit.setVisibility(View.VISIBLE);
        textViewMedicalInsurance.setVisibility(View.VISIBLE);
        medicalInsurance.setVisibility(View.VISIBLE);
        textViewNPSContribution.setVisibility(View.VISIBLE);
        npsContribution.setVisibility(View.VISIBLE);
        textViewEducationLoan.setVisibility(View.VISIBLE);
        educationLoan.setVisibility(View.VISIBLE);
        textViewHousingLoan.setVisibility(View.VISIBLE);
        housingLoan.setVisibility(View.VISIBLE);
        textViewCharityDonation.setVisibility(View.VISIBLE);
        charityDonation.setVisibility(View.VISIBLE);
    }
}
