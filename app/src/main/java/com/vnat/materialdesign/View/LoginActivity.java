package com.vnat.materialdesign.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        event();
    }

    private void event() {
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