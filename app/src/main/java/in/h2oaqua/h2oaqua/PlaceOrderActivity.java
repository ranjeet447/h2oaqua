package in.h2oaqua.h2oaqua;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.h2oaqua.h2oaqua.data.UserContract.UserEntry;
import in.h2oaqua.h2oaqua.data.UserDbHelper;

public class PlaceOrderActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout collapsedRateCard;
    LinearLayout expandedRateCard;
    Button mNextButton;
    Button mWaterCansPlusButton;
    Button mEmptyWaterCansPlusButton;
    Button mWaterCansMinusButton;
    Button mEmptyWaterCansMinusButton;
    int QuantityOfWaterCans = 1;
    int EmptyWaterCans = 1;
    EditText QuantityOfWaterCansEditText;
    EditText EmptyWaterCansEditText;

    ImageView refreshButton;
    Animation rotation;

    private UserDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        if (!isNetworkConnected()) {
            findViewById(R.id.network_error_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.place_order_layout).setVisibility(View.GONE);
            refreshButton = findViewById(R.id.refreshImageButton);
            rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotare);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshButton.startAnimation(rotation);
                    if (isNetworkConnected()) {
                        refreshButton.clearAnimation();
                        findViewById(R.id.network_error_layout).setVisibility(View.GONE);
                        findViewById(R.id.place_order_layout).setVisibility(View.VISIBLE);
                        //finish();
                        //startActivity(getIntent());
                    }
                }
            });
        }

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new UserDbHelper(this);
        displayUserInfo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        collapsedRateCard = findViewById(R.id.collapseRateCard);
        expandedRateCard = findViewById(R.id.expandedRateCard);

        mNextButton = findViewById(R.id.placeOrderNextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaceOrderActivity.this, confirmationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("NumberOfWaterCans", QuantityOfWaterCans);
                bundle.putInt("ReturnOfWaterCans", EmptyWaterCans);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        QuantityOfWaterCansEditText =  findViewById(R.id.waterCansText);
        QuantityOfWaterCansEditText.setText(String.valueOf(QuantityOfWaterCans));
        EmptyWaterCansEditText =  findViewById(R.id.emptyWaterCansText);
        EmptyWaterCansEditText.setText(String.valueOf(EmptyWaterCans));

        mWaterCansMinusButton = findViewById(R.id.minusWaterCans);
        mWaterCansMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QuantityOfWaterCans > 1 && EmptyWaterCans == QuantityOfWaterCans) {
                    QuantityOfWaterCans = QuantityOfWaterCans - 1;
                    QuantityOfWaterCansEditText.setText(String.valueOf(QuantityOfWaterCans));
                    EmptyWaterCans = EmptyWaterCans - 1;
                    EmptyWaterCansEditText.setText(String.valueOf(EmptyWaterCans));
                } else {
                    if (QuantityOfWaterCans > 1) {
                        QuantityOfWaterCans = QuantityOfWaterCans - 1;
                        QuantityOfWaterCansEditText.setText(String.valueOf(QuantityOfWaterCans));
                    }
                }
            }
        });

        mWaterCansPlusButton =  findViewById(R.id.plusWaterCans);
        mWaterCansPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuantityOfWaterCans = QuantityOfWaterCans + 1;
                QuantityOfWaterCansEditText.setText(String.valueOf(QuantityOfWaterCans));
            }
        });

        mEmptyWaterCansMinusButton = findViewById(R.id.minusEmptyWaterCans);
        mEmptyWaterCansMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EmptyWaterCans > 0) {
                    EmptyWaterCans = EmptyWaterCans - 1;
                    EmptyWaterCansEditText.setText(String.valueOf(EmptyWaterCans));
                }
            }
        });

        mEmptyWaterCansPlusButton = findViewById(R.id.plusEmptyWaterCans);
        mEmptyWaterCansPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (QuantityOfWaterCans > EmptyWaterCans) {
                    EmptyWaterCans = EmptyWaterCans + 1;
                    EmptyWaterCansEditText.setText(String.valueOf(EmptyWaterCans));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void closeDrawer(View v) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void showRateCard(View v) {
        collapsedRateCard.setVisibility(View.GONE);
        expandedRateCard.setVisibility(View.VISIBLE);
    }

    public void hideRateCard(View v) {
        collapsedRateCard.setVisibility(View.VISIBLE);
        expandedRateCard.setVisibility(View.GONE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.place_order, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            startActivity(new Intent(PlaceOrderActivity.this, userProfileActivity.class));
        }
        if (id == R.id.nav_orderHistory) {
            startActivity(new Intent(PlaceOrderActivity.this, orderHistoryActivity.class));
        }
        if (id == R.id.nav_contactUs) {
            startActivity(new Intent(PlaceOrderActivity.this, contactUsActivity.class));

        }
        if (id == R.id.signOut) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure you want to signout?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Signout",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserDbHelper mDbHelper = new UserDbHelper(getApplicationContext());
                            SQLiteDatabase db = mDbHelper.getReadableDatabase();
                            db.execSQL("delete from " + UserEntry.TABLE_NAME);
                            startActivity(new Intent(getApplicationContext(), signInActivity.class));
                            finish();
                        }
                    });
            builder1.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        if (id == R.id.nav_share) {
            Intent intent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, "Quench Your Thrist\n https://www.h2oaqua.in")
                    .setType("text/plain");
            startActivity(intent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null ? cm.getActiveNetworkInfo() : null) != null;
    }

    private void displayUserInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                UserEntry._ID,
                UserEntry.COLUMN_USER_NAME,
                UserEntry.COLUMN_USER_EMAIL,
                UserEntry.COLUMN_USER_IMAGE_URL,
        };
        Cursor cursor = db.query(UserEntry.TABLE_NAME, projection,
                null,
                null,
                null,
                null,
                null);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.profileImage);
        TextView nameTextView = navigationView.getHeaderView(0).findViewById(R.id.profileName);
        TextView emailTextView = navigationView.getHeaderView(0).findViewById(R.id.profileEmail);

        try {

            int idColumnIndex = cursor.getColumnIndex(UserEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            int emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
            int imageColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IMAGE_URL);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentemail = cursor.getString(emailColumnIndex);
                String currentimage = cursor.getString(imageColumnIndex);

                nameTextView.setText(currentName);
                emailTextView.setText(currentemail);
                Picasso.with(this).load(currentimage)
                        .placeholder(R.drawable.default_profile_image)
                        .into(imageView);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
