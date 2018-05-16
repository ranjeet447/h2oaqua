package in.h2oaqua.h2oaqua;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class otpActivity extends AppCompatActivity {

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        if(getSupportActionBar() != null)
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final EditText otpEditText = findViewById(R.id.otpEditText);
        Button submitOtpButton = findViewById(R.id.submitOtpButton);
        submitOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String otp = otpEditText.getText().toString();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
