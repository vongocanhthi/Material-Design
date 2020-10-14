package com.vnat.materialdesign.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.vnat.materialdesign.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    @BindView(R.id.pvVerifyOTP)
    PinView pvVerifyOTP;

    @BindView(R.id.btnVerify)
    Button btnVerify;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String phoneNumber = "";

    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        ButterKnife.bind(this);

        init();
        funGetPhoneNumber();
        funSendCodeToUser();
        funClickVerify();

    }

    private void funClickVerify() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pvVerifyOTP.getText().toString();
                if (code.isEmpty()){
                    pvVerifyOTP.setError("Wrong OTP...");
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }
            }
        });

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);
    }

    private void funSendCodeToUser() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks,
                mResendToken);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mResendToken = forceResendingToken;

            Toast.makeText(VerifyPhoneNumberActivity.this, "Send code success", Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        pvVerifyOTP.setText(code);

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Intent intent = new Intent(VerifyPhoneNumberActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
//                    Toast.makeText(VerifyPhoneNumberActivity.this, "Invalid verify code", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(VerifyPhoneNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void funGetPhoneNumber() {
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }
}