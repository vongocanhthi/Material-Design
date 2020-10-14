package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnat.materialdesign.Model.User;
import com.vnat.materialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

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

        funClickSignUp();
        funClickLogin();

    }

    private void funClickLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private void funClickSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFullName() && validateUsername() && validateEmail() && validatePhoneNumber() && validatePassword()) {
                    funAddUser();
                    funIntentVerifyOtp();

//                    Toast.makeText(SignUpActivity.this, "Your account has been created successfully", Toast.LENGTH_LONG).show();

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            onBackPressed();
//                        }
//                    }, 3000);
                }

            }
        });
    }

    private void funAddUser() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String fullName = edtFullName.getText().toString();
        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String password = edtPassword.getText().toString();

        User user = new User(fullName, username, email, phoneNumber, password);

        reference.child(username).setValue(user);
    }

    private void funIntentVerifyOtp() {
        Intent intent = new Intent(SignUpActivity.this, VerifyPhoneNumberActivity.class);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(imgLogo, "imgLogo");
        pairs[1] = new Pair<View, String>(txtTitle, "txtTitle");
        pairs[2] = new Pair<View, String>(edtPhoneNumber, "verifyOtp");
        pairs[3] = new Pair<View, String>(btnSignUp, "btn1");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);

        String phoneNumber = edtPhoneNumber.getText().toString();
        intent.putExtra("phoneNumber", phoneNumber);

        startActivity(intent, options.toBundle());
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
