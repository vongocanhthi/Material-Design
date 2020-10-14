package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ButterKnife.bind(this);

        funGetDataUser();
    }

    private void funGetDataUser() {
        Bundle bundle = getIntent().getExtras();
        String fullNameFromDB = bundle.getString("fullNameFromDB");
        String usernameFromDB = bundle.getString("usernameFromDB");
        String emailFromDB = bundle.getString("emailFromDB");
        String phoneNumberFromDB = bundle.getString("phoneNumberFromDB");

        txtFullName.setText(fullNameFromDB);
        txtUsername.setText(usernameFromDB);
        edtFullName.setText(fullNameFromDB);
        edtEmail.setText(emailFromDB);
        edtPhoneNumber.setText(phoneNumberFromDB);
    }

}