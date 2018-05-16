package in.h2oaqua.h2oaqua;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class phoneNumberActivity extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_SEND_SMS = 123;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final EditText phoneNumberEditText = findViewById(R.id.phoneNumbereEditText);
        Button submitNumberButton = findViewById(R.id.submitPhoneNumberButton);
        submitNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNumberEditText.getText().toString();
                int generatedOTP = (int) (Math.random() * (10000-1000) + 1000);
                getSmsPermission();
                if(number != null && number.matches("[0-9]{10}")) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, "OTP " + generatedOTP, null, null);
                    startActivity(new Intent(phoneNumberActivity.this, otpActivity.class));
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //sendSms(mobile);
                } else {
                    Toast.makeText(phoneNumberActivity.this, "SEND_SMS Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void getSmsPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
