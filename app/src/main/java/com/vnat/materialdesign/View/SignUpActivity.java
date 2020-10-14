package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnat.materialdesign.Model.User;
import com.vnat.materialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.edtFullName)
    EditText edtFullName;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        funSignUp();
        funLogin();

    }

    private void funLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void funSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFullName() && validateUsername() && validateEmail() && validatePhoneNumber() && validatePassword()) {

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    String fullName = edtFullName.getText().toString();
                    String username = edtUsername.getText().toString();
                    String email = edtEmail.getText().toString();
                    String phoneNumber = edtPhoneNumber.getText().toString();
                    String password = edtPassword.getText().toString();

                    User user = new User(fullName, username, email, phoneNumber, password);

                    reference.child(username).setValue(user);

                    Toast.makeText(SignUpActivity.this, "Account registration is successful", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validateFullName() {
        String fullName = edtFullName.getText().toString();

        if (fullName.isEmpty()) {
            edtFullName.setError("Full name can not be empty");
            return false;
        }

        return true;
    }

    private boolean validateUsername() {
        String username = edtUsername.getText().toString();

        if (username.isEmpty()) {
            edtUsername.setError("Username can not be empty");
            return false;
        } else {
            if (username.length() < 6) {
                edtUsername.setError("Username length >= 6");
                return false;
            }
        }

        return true;
    }

    private boolean validateEmail() {
        String email = edtEmail.getText().toString();

        if (email.isEmpty()) {
            edtEmail.setError("Email can not be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Invalid email address");
            return false;
        }

        return true;
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = edtPhoneNumber.getText().toString();

        if (phoneNumber.isEmpty()) {
            edtPhoneNumber.setError("Phone number can not be empty");
            return false;
        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            edtPhoneNumber.setError("Invalid phone number");
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        String password = edtPassword.getText().toString();

        if (password.isEmpty()) {
            edtPassword.setError("Password can not be empty");
            return false;
        } else {
            if (password.length() < 6) {
                edtPassword.setError("Password length >= 6");
                return false;
            }
        }

        return true;
    }

}
