package com.vnat.materialdesign.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vnat.materialdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.imgLogo)
    ImageView imgLogo;

    @BindView(R.id.txtSlogan)
    TextView txtSlogan;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnForgetPassword)
    Button btnForgetPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    private static final String TAG = "zzz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        funClickSignUp();

        funClickLogin();
    }

    private void funClickLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername() && validatePassword()) {
                    final String username = edtUsername.getText().toString();
                    final String password = edtPassword.getText().toString();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);

                                if (password.equals(passwordFromDB)) {
                                    String fullNameFromDB = snapshot.child(username).child("fullName").getValue(String.class);
                                    String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                                    String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                                    String phoneNumberFromDB = snapshot.child(username).child("phoneNumber").getValue(String.class);

                                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putString("fullNameFromDB", fullNameFromDB);
                                    bundle.putString("usernameFromDB", usernameFromDB);
                                    bundle.putString("emailFromDB", emailFromDB);
                                    bundle.putString("phoneNumberFromDB", phoneNumberFromDB);

                                    intent.putExtras(bundle);

                                    startActivity(intent);

                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

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

    private void funClickSignUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imgLogo, "imgLogo");
                pairs[1] = new Pair<View, String>(txtSlogan, "txtSlogan");
                pairs[2] = new Pair<View, String>(txtTitle, "txtTitle");
                pairs[3] = new Pair<View, String>(edtUsername, "edtUsername");
                pairs[4] = new Pair<View, String>(edtPassword, "edtPassword");
                pairs[5] = new Pair<View, String>(btnLogin, "btn1");
                pairs[6] = new Pair<View, String>(btnSignUp, "btn2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }
}