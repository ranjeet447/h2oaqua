package in.h2oaqua.h2oaqua;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.h2oaqua.h2oaqua.data.UserContract.UserEntry;
import in.h2oaqua.h2oaqua.data.UserDbHelper;

public class userProfileActivity extends AppCompatActivity {
    public static Activity UPA;
    public static final String TAG = "userProfileActivity";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UPA = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        displayUserInfo();

        Button addNumberButton = findViewById(R.id.add_number_button);
        addNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userProfileActivity.this,phoneNumberActivity.class));
            }
        });

        final View mAddAddress = findViewById(R.id.addAddress);
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userProfileActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAG", TAG);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address(R.drawable.ic_delete_white_48dp, R.drawable.background_circle, "address1 dgkjfdhbkjfnbksnjvnv"));
        addresses.add(new Address(R.drawable.ic_delete_white_48dp, R.drawable.background_circle, "address2 dgkjfdhbkjfnbksnjvnv"));

        ListView addressListView = findViewById(R.id.list);
        AddressAdapter adapter = new AddressAdapter(this, addresses);
        addressListView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(addressListView);
    }

    private void displayUserInfo() {
        // Create and/or open a database to read from it
        UserDbHelper mDbHelper = new UserDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_USER_NAME,
                UserEntry.COLUMN_USER_EMAIL,
                UserEntry.COLUMN_USER_IMAGE_URL,
                 UserEntry.COLUMN_USER_PHONE
        };
        Cursor cursor = db.query(UserEntry.TABLE_NAME, projection,
                null,
                null,
                null,
                null,
                null);
        ImageView imageView = findViewById(R.id.profile_image);
        TextView nameTextView = findViewById(R.id.profile_name);
        TextView emailTextView = findViewById(R.id.profile_email);
        LinearLayout addNumberView = findViewById(R.id.add_number);

        try {
            int idColumnIndex = cursor.getColumnIndex(UserEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            int emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
            int imageColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IMAGE_URL);
            int phoneColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_PHONE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentemail = cursor.getString(emailColumnIndex);
                String currentimage = cursor.getString(imageColumnIndex);
                String currentphone = cursor.getString(phoneColumnIndex);

                nameTextView.setText(currentName);
                emailTextView.setText(currentemail);
                Picasso.with(this).load(currentimage)
                        .placeholder(R.drawable.default_profile_image)
                        .into(imageView);
//                if(currentphone!=null)
//                    addNumberView.setVisibility(View.GONE);

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
