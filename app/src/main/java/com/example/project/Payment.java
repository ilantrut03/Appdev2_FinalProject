package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    private Button btn;
    private EditText fullName, cardNumber, CVV, date;
    private RadioButton visa, master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn = findViewById(R.id.payment);
        fullName = findViewById(R.id.EditTextFullName);
        cardNumber = findViewById(R.id.EditTextCardNumber);
        CVV = findViewById(R.id.EditTextCVV);
        date = findViewById(R.id.EditTextDate);
        visa = findViewById(R.id.radioButtonVisa);
        master = findViewById(R.id.radioButtonMaster);

        btn.setOnClickListener(view -> {
            if(!fullName.getText().toString().isEmpty() &&
                    !cardNumber.getText().toString().isEmpty() &&
                    !CVV.getText().toString().isEmpty() && !date.getText().toString().isEmpty()) {
                Intent intent = new Intent(this, Timer.class);
                startActivity(intent);
            }

            else {
                Toast.makeText(Payment.this, "Please fill in the empty requirements", Toast.LENGTH_SHORT).show();
            }

        });

    }

//    public void checkButton(View v) {
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);
//
//        if(!radioButton.isChecked()) {
//            Toast.makeText(Payment.this, "Please check button", Toast.LENGTH_SHORT).show();
//        }
//    }
}