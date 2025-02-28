package com.example.incometaxappcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Summary extends AppCompatActivity {


    private ImageView backIcon_Summary;
    private EditText totalIncomeField, deductionsField, taxableIncomeField;
    private EditText incomeField, basicDeductionField, medicalInsuranceField, educationLoanField, employeeContributionField, charityField, housingLoanField;
    private RadioButton oldRegimeRadioButton, newRegimeRadioButton; // Added new regime radio button
    private Button exitButton;
    private String basicDeduction,interestDeposit,medicalInsurance,npsContribution,educationLoan,housingLoan,charityDonation,selectedFY,selectedAge,incomeFromSalary,exceptAllowance,incomeFromInterest,interestHomeLoan,rentIncome,incomeHomeLoanLetOut,incomeFromDigitalAssets,otherIncome;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);


        // Initialize UI elements
        backIcon_Summary = findViewById(R.id.imageView2);
        exitButton = findViewById(R.id.button4);


        // Handle Back Icon click
        backIcon_Summary.setOnClickListener(v -> finish());

        // Get the selected regime from the previous activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String selectedRegime = bundle.getString("selectedRegime");

//             Display selected regime
            TextView regimeTextView = findViewById(R.id.regimeTextView);
            regimeTextView.setText("Selected Regime: " + selectedRegime);

            //from deductions
            basicDeduction = bundle.getString("basicDeduction");
            interestDeposit = bundle.getString("interestDeposit");
            medicalInsurance = bundle.getString("medicalInsurance");
            npsContribution = bundle.getString("npsContribution");
            educationLoan = bundle.getString("educationLoan");
            housingLoan = bundle.getString("housingLoan");
            charityDonation = bundle.getString("charityDonation");

            //from basicdetails
            selectedFY = bundle.getString("SELECTED_FY");
            selectedAge = bundle.getString("SELECTED_AGE");

            //from income
            incomeFromSalary = bundle.getString("incomeFromSalary");
            exceptAllowance = bundle.getString("exceptAllowance");
            incomeFromInterest = bundle.getString("incomeFromInterest");
            interestHomeLoan = bundle.getString("interestHomeLoan");
            rentIncome = bundle.getString("rentIncome");
            incomeHomeLoanLetOut = bundle.getString("incomeHomeLoanLetOut");
            incomeFromDigitalAssets = bundle.getString("incomeFromDigitalAssets");
            otherIncome = bundle.getString("otherIncome");



            // Perform calculations based on the selected regime
            if ("Old Regime".equals(selectedRegime)) {
                performOldRegimeCalculations();
            } else if ("New Regime".equals(selectedRegime)) { // Handle new regime calculations
                performNewRegimeCalculations();
            }
        }



        // Handle Exit Button click
        exitButton.setOnClickListener(v -> finishAffinity());
    }

    private void performOldRegimeCalculations() {
        // Get the total income from the income string retrieved from the Bundle
        double totalIncome = Double.parseDouble(incomeFromSalary); // Use the value from the Bundle
        totalIncomeField.setText(String.valueOf(totalIncome));

        // Get deductions from the Bundle and convert them to double
        double basicDeduction = Double.parseDouble(this.basicDeduction); // Use the value from the Bundle
        double medicalInsurance = Double.parseDouble(this.medicalInsurance); // Use the value from the Bundle
        double educationLoan = Double.parseDouble(this.educationLoan); // Use the value from the Bundle
        double employeeContribution = Double.parseDouble(npsContribution); // Assuming NPS contribution
        double charity = Double.parseDouble(this.charityDonation); // Use the value from the Bundle
        double housingLoan = Double.parseDouble(this.housingLoan); // Use the value from the Bundle

        // Calculate total deductions
        double totalDeductions = 50000 + basicDeduction + medicalInsurance + educationLoan + employeeContribution + charity + housingLoan;
        deductionsField.setText(String.valueOf(totalDeductions));

        // Calculate taxable income (total income - total deductions)
        double taxableIncome = totalIncome - totalDeductions;
        taxableIncomeField.setText(String.valueOf(taxableIncome));

        // Perform tax calculation for Old Regime
        double taxAmount = calculateTaxBasedOnOldRegime(taxableIncome);
        Toast.makeText(this, "Tax calculated (Old Regime): " + taxAmount, Toast.LENGTH_LONG).show();
    }

    private void performNewRegimeCalculations() {
        // Get the total income from the income string retrieved from the Bundle
        double totalIncome = Double.parseDouble(incomeFromSalary); // Use the value from the Bundle
        totalIncomeField.setText(String.valueOf(totalIncome));

        // New regime does not consider most deductions
        double totalDeductions = 0; // Set to 0 as per the New Regime rules
        deductionsField.setText(String.valueOf(totalDeductions));

        // Calculate taxable income (total income - total deductions)
        double taxableIncome = totalIncome - totalDeductions;
        taxableIncomeField.setText(String.valueOf(taxableIncome));

        // Perform tax calculation based on the New Regime
        double taxAmount = calculateTaxBasedOnNewRegime(taxableIncome);
        Toast.makeText(this, "Tax calculated (New Regime): " + taxAmount, Toast.LENGTH_LONG).show();
    }

    private double calculateTaxBasedOnNewRegime(double taxableIncome) {
        double taxAmount = 0;

        if (taxableIncome <= 300000) {
            taxAmount = 0; // No tax for income <= 3 lakh
        } else if (taxableIncome <= 600000) {
            taxAmount = (taxableIncome - 300000) * 0.05; // 5% tax on income between 3 lakh and 6 lakh
        } else if (taxableIncome <= 900000) {
            taxAmount = (300000 * 0.05) + (taxableIncome - 600000) * 0.1; // 10% on income between 6 lakh and 9 lakh
        } else if (taxableIncome <= 1200000) {
            taxAmount = (300000 * 0.05) + (300000 * 0.1) + (taxableIncome - 900000) * 0.15; // 15% on income between 9 lakh and 12 lakh
        } else if (taxableIncome <= 1500000) {
            taxAmount = (300000 * 0.05) + (300000 * 0.1) + (300000 * 0.15) + (taxableIncome - 1200000) * 0.2; // 20% on income between 12 lakh and 15 lakh
        } else {
            taxAmount = (300000 * 0.05) + (300000 * 0.1) + (300000 * 0.15) + (300000 * 0.2) + (taxableIncome - 1500000) * 0.3; // 30% on income above 15 lakh
        }

        // Add health and education cess if taxable income is greater than 700000
        if (taxableIncome > 700000) {
            taxAmount += taxableIncome * 0.04; // 4% of taxable income
        }

        return taxAmount;
    }

    private int getUserAge() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String ageStr = bundle.getString("SELECTED_AGE");
            return ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr);
        }
        return 0; // Default if age is not provided
    }

    private double calculateTaxBasedOnOldRegime(double taxableIncome) {
        int age = getUserAge(); // Implement this method to retrieve the user's age

        if (age < 60) {
            return calculateTaxForAgeBelow60(taxableIncome);
        } else if (age >= 60 && age <= 80) {
            return calculateTaxForAge60to80(taxableIncome);
        } else {
            return calculateTaxForAgeAbove80(taxableIncome);
        }
    }

    private double getDoubleFromField(EditText editText) {
        String valueStr = editText.getText().toString();
        return valueStr.isEmpty() ? 0 : Double.parseDouble(valueStr);
    }

    private double calculateTaxForAgeBelow60(double taxableIncome) {
        double taxAmount = 0;

        if (taxableIncome <= 250000) {
            taxAmount = 0; // No tax for income <= 2.5 lakh
        } else if (taxableIncome <= 500000) {
            taxAmount = (taxableIncome - 250000) * 0.05; // 5% tax on income between 2.5 lakh and 5 lakh
        } else if (taxableIncome <= 1000000) {
            // 5% on income between 2.5 lakh and 5 lakh, 20% on income above 5 lakh
            taxAmount = (500000 - 250000) * 0.05 + (taxableIncome - 500000) * 0.2;
        } else {
            // 5% on income between 2.5 lakh and 5 lakh, 20% on income between 5 lakh and 10 lakh, 30% on income above 10 lakh
            taxAmount = (500000 - 250000) * 0.05 + (1000000 - 500000) * 0.2 + (taxableIncome - 1000000) * 0.3;
        }

        if (taxableIncome > 700000) {
            taxAmount += taxableIncome * 0.04; // 4% of taxable income
        }

        return taxAmount;
    }

    private double calculateTaxForAge60to80(double taxableIncome) {
        double taxAmount = 0;

        if (taxableIncome <= 300000) {
            taxAmount = 0; // No tax for income <= 3 lakh
        } else if (taxableIncome <= 500000) {
            taxAmount = (taxableIncome - 300000) * 0.05; // 5% tax on income between 3 lakh and 5 lakh
        } else if (taxableIncome <= 1000000) {
            // 5% on income between 3 lakh and 5 lakh, 20% on income above 5 lakh
            taxAmount = (500000 - 300000) * 0.05 + (taxableIncome - 500000) * 0.2;
        } else {
            // 5% on income between 3 lakh and 5 lakh, 20% on income between 5 lakh and 10 lakh, 30% on income above 10 lakh
            taxAmount = (500000 - 300000) * 0.05 + (1000000 - 500000) * 0.2 + (taxableIncome - 1000000) * 0.3;
        }

        if (taxableIncome > 700000) {
            taxAmount += taxableIncome * 0.04; // 4% of taxable income
        }

        return taxAmount;
    }

    private double calculateTaxForAgeAbove80(double taxableIncome) {
        double taxAmount = 0;

        if (taxableIncome <= 500000) {
            taxAmount = 0; // No tax for income <= 5 lakh
        } else if (taxableIncome <= 1000000) {
            taxAmount = (taxableIncome - 500000) * 0.2; // 20% tax on income between 5 lakh and 10 lakh
        } else {
            // 20% on income between 5 lakh and 10 lakh, 30% on income above 10 lakh
            taxAmount = (1000000 - 500000) * 0.2 + (taxableIncome - 1000000) * 0.3;
        }

        if (taxableIncome > 700000) {
            taxAmount += taxableIncome * 0.04; // 4% of taxable income
        }


        return taxAmount;
    }
}
