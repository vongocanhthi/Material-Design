package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnat.materialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.txtFullName)
    TextView txtFullName;
    @BindView(R.id.txtUsername)
    TextView txtUsername;

    @BindView(R.id.edtFullName)
    EditText edtFullName;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    private String fullNameFromDB, usernameFromDB, emailFromDB, phoneNumberFromDB;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(this);

        funGetDataUser();
        funClickUpdate();
    }

    private void funClickUpdate() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = FirebaseDatabase.getInstance().getReference("users");

                if (isFullNameChanged() || isEmailChanged() || isPhoneNumberChanged()){
                    Toast.makeText(UserProfileActivity.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserProfileActivity.this, "Data to same and can not be updated", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isFullNameChanged() {
        String fullName = edtFullName.getText().toString();
        if (!fullNameFromDB.equals(fullName)) {
            reference.child(usernameFromDB).child("fullName").setValue(fullName);
            fullNameFromDB = fullName;
            txtFullName.setText(fullName);
            return true;
        }
        return false;
    }

    private boolean isEmailChanged() {
        String email = edtEmail.getText().toString();
        if (!emailFromDB.equals(email)) {
            reference.child(usernameFromDB).child("email").setValue(email);
            emailFromDB = email;
            return true;
        }
        return false;
    }

    private boolean isPhoneNumberChanged() {
        String phoneNumber = edtPhoneNumber.getText().toString();
        if (!phoneNumberFromDB.equals(phoneNumber)) {
            reference.child(usernameFromDB).child("phoneNumber").setValue(phoneNumber);
            phoneNumberFromDB = phoneNumber;
            return true;
        }
        return false;
    }

    private void funGetDataUser() {
        Bundle bundle = getIntent().getExtras();
        fullNameFromDB = bundle.getString("fullNameFromDB");
        usernameFromDB = bundle.getString("usernameFromDB");
        emailFromDB = bundle.getString("emailFromDB");
        phoneNumberFromDB = bundle.getString("phoneNumberFromDB");

        txtFullName.setText(fullNameFromDB);
        txtUsername.setText(usernameFromDB);
        edtFullName.setText(fullNameFromDB);
        edtEmail.setText(emailFromDB);
        edtPhoneNumber.setText(phoneNumberFromDB);
    }

}