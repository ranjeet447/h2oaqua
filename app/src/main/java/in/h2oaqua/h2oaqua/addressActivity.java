package in.h2oaqua.h2oaqua;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class addressActivity extends AppCompatActivity {

    String city;
    String pinCode;
    String flatNo;
    String street;
    String locality;
    String knownName;
    String intentTag;

    String mFloorNO;
    String mFlatNo;
    String mStreet;
    String mLandmark;
    String mLocality;
    String mCity;
    String mPinCode;
    static String fullAddress;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        final EditText floorEditText = findViewById(R.id.floor_no_EditTextView);
        final EditText landmarkEditText = findViewById(R.id.landmark_EditTextView);
        final EditText flatNoEditText = findViewById(R.id.Flat_no_EditTextView);
        final EditText streetEditText = findViewById(R.id.street_EditTextView);
        final EditText localityEditText = findViewById(R.id.locality_EditTextView);
        final EditText cityEditText = findViewById(R.id.city_EditTextView);
        final EditText pincodeEditText = findViewById(R.id.pincode_EditTextView);

        //Get the Bundle
        Bundle bundle = getIntent().getExtras();
        //Extract data
        flatNo = bundle != null ? bundle.getString("HOUSENO") : null;
        street = bundle != null ? bundle.getString("STREET") : null;
        knownName = bundle != null ? bundle.getString("KNOWNNAME") : null;
        locality = bundle != null ? bundle.getString("LOCALITY") : null;
        city = bundle != null ? bundle.getString("CITY") : null;
        pinCode = bundle != null ? bundle.getString("POSTALCODE") : null;
        intentTag = bundle != null ? bundle.getString("INTENTTAG") : null;

        flatNoEditText.setText(flatNo);
        streetEditText.setText(street);
        if (locality == null) {
            localityEditText.setText(knownName);
        } else {
            localityEditText.setText(locality);
        }
        cityEditText.setText(city);
        pincodeEditText.setText(pinCode);

        Button saveAddressButton = findViewById(R.id.save_address_Button);
        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloorNO = floorEditText.getText().toString().trim();
                mFlatNo = flatNoEditText.getText().toString().trim();
                mStreet = streetEditText.getText().toString().trim();
                mLandmark = landmarkEditText.getText().toString().trim();
                mLocality = localityEditText.getText().toString().trim();
                mCity = cityEditText.getText().toString().trim();
                mPinCode = pincodeEditText.getText().toString().trim();

                if (flatNoEditText.getText().length()==0)
                    flatNoEditText.requestFocus();
                else if (streetEditText.getText().length()==0)
                    streetEditText.requestFocus();
                else if (localityEditText.getText().length()==0)
                    localityEditText.requestFocus();
                else if (cityEditText.getText().length()==0)
                    cityEditText.requestFocus();
                else if (pincodeEditText.getText().length()==0)
                    pincodeEditText.requestFocus();
                else {

                    MapsActivity.MA.finish();

                    fullAddress = mFlatNo + "," + mFloorNO + ","
                            + mStreet + "," + mLandmark + ","
                            + mLocality + "," + mCity + "," + mPinCode;

                    extraxtAddress();

                    if (intentTag.contentEquals("confirmationActivity")) {
                        addressActivity.super.onBackPressed();
                    }
                    if (intentTag.contentEquals("userProfileActivity")) {
                        userProfileActivity.UPA.finish();
                        startActivity(new Intent(addressActivity.this, userProfileActivity.class));
                    }
                    finish();
                }

            }
        });

    }

    public static ArrayList<Address> extraxtAddress() {
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address(R.drawable.ic_place_white_48dp, R.drawable.background_circle_green, fullAddress));
        return addresses;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
