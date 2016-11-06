package teamthat.com.onemusic.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import teamthat.com.onemusic.R;

public class SignupActivity extends AppCompatActivity {

    EditText edtName, edtAddress, edtEmail, edtPassword, edtReEnterPassword;
    Button btnSignup;
    TextView tvLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControl();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void addControl() {
        edtName = (EditText) findViewById(R.id.input_name);
        edtAddress = (EditText) findViewById(R.id.input_address);
        edtEmail = (EditText) findViewById(R.id.input_email);
        edtPassword = (EditText) findViewById(R.id.input_password);
        edtReEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        tvLogin = (TextView) findViewById(R.id.link_login);
    }

    public void signUp() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Đăng kí thất bại !", Toast.LENGTH_LONG).show();
        btnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String reEnterPassword = edtReEnterPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            edtName.setError("Tên ít nhất có 3 kí tự");
            valid = false;
        } else {
            edtName.setError(null);
        }

        if (address.isEmpty()) {
            edtAddress.setError("Nhập điạ chỉ");
            valid = false;
        } else {
            edtAddress.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Nhập địa chỉ email hợp lệ");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            edtPassword.setError("Mật khẩu ít nhất có 4 kí tự");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || !(reEnterPassword.equals(password))) {
            edtReEnterPassword.setError("Mật khẩu không phù hợp");
            valid = false;
        } else {
            edtReEnterPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        super.onBackPressed();
    }
}
