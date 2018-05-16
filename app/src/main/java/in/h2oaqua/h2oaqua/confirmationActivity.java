package in.h2oaqua.h2oaqua;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class confirmationActivity extends AppCompatActivity {
    public static final String TAG = "confirmationActivity";
    TextView mWaterCansTextView;
    TextView mEmptyWaterCansTextView;
    TextView mAmountTextView;
    Integer mNumberOfWaterCans;
    Integer mNumberOfEmptyWaterCans;
    Integer mAmount;
    View DatePicker;
    View TimePicker;
    TextView dateTextView;
    TextView timeTextView;
    String date;
    String time1;
    String time2;
    String time;
    String addressString = null;
    static int dateSelected = 0;
    static int timeSelected = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        DatePicker = findViewById(R.id.datePicker);
        TimePicker = findViewById(R.id.timePicker);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

        final View mAddAddress = findViewById(R.id.addAddress);
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(confirmationActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TAG", TAG);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setDate();
        setTime();

        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        TimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });


        //Get the Bundle
        Bundle bundle = getIntent().getExtras();
        //Extract data
        mNumberOfWaterCans = bundle != null ? bundle.getInt("NumberOfWaterCans") : 1;
        mNumberOfEmptyWaterCans = bundle != null ? bundle.getInt("ReturnOfWaterCans") : 1;

        mWaterCansTextView = findViewById(R.id.waterCanId);
        mWaterCansTextView.setText(String.valueOf(mNumberOfWaterCans));
        mEmptyWaterCansTextView = findViewById(R.id.emptyWaterCanId);
        mEmptyWaterCansTextView.setText(String.valueOf(mNumberOfEmptyWaterCans));
        mAmount = calculateAmount(mNumberOfWaterCans, mNumberOfEmptyWaterCans);
        mAmountTextView = findViewById(R.id.billingAmount);
        mAmountTextView.setText(String.valueOf(mAmount));


        ArrayList<Address> addresses = addressActivity.extraxtAddress();
        addresses.add(new Address(R.drawable.ic_place_white_48dp, R.drawable.background_circle_green, "address1 dgkjfdhbkjfnbksnjvnv"));
        addresses.add(new Address(R.drawable.ic_place_white_48dp, R.drawable.background_circle_green, "address2 dgkjfdhbkjfnbksnjvnv"));
        addresses.add(new Address(R.drawable.ic_place_white_48dp, R.drawable.background_circle_green, "address3 dgkjfdhbkjfnbksnjvnv"));
        addresses.add(new Address(R.drawable.ic_place_white_48dp, R.drawable.background_circle_green, "address4 dgkjfdhbkjfnbksnjvnv"));
        final ListView addressListView = findViewById(R.id.list);
        if (addressListView.getCount() != 0) {
            addressListView.setVisibility(View.VISIBLE);
        }
        AddressAdapter adapter = new AddressAdapter(this, addresses);
        addressListView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(addressListView);


        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                String selectedAddress = addressListView.getItemAtPosition(position).toString();
                TextView textView = view.findViewById(R.id.addressString);
                addressString = textView.getText().toString();
                Toast.makeText(getApplicationContext(), "Selected Address: " + addressString, Toast.LENGTH_SHORT).show();
            }
        });

        Button confirmOrderButton = findViewById(R.id.confirm_order_Button);
        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    if (addressString == null) {
                        findViewById(R.id.select_address_view).setBackgroundColor(Color.parseColor("#ff867c"));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.select_address_view).setBackgroundColor(Color.parseColor("#ffffff"));
                            }
                        }, 500);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(confirmationActivity.this).create();
                        alertDialog.setTitle("Confirmation");
                        alertDialog.setMessage("Number of Water Cans :" + mNumberOfWaterCans + "\n" +
                                "Return Water Cans :" + mNumberOfEmptyWaterCans + "\n" +
                                "Billing Amount :" + mAmount + "\n" +
                                "Date :" + date + "\n" +
                                "Time :" + time + "\n" +
                                "Address :" + addressString);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }else{
                    Toast.makeText(confirmationActivity.this, "No Internet...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        date = formatDate(currentDate);
        dateTextView.setText(date);
    }
    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        if (!(calendar.get(Calendar.HOUR_OF_DAY) > 11 && calendar.get(Calendar.HOUR_OF_DAY) < 23 &&
                date.equals(formatDate(calendar.getTime())))) {
            calendar.set(Calendar.HOUR_OF_DAY, 11);
            time1 = formatTime(calendar.getTime());
            calendar.add(Calendar.HOUR, 1);
            time2 = formatTime(calendar.getTime());
            time = time1 + " - " + time2;
            timeTextView.setText(time);
        } else {
            calendar.add(Calendar.HOUR, 1);
            time1 = formatTime(calendar.getTime());
            calendar.add(Calendar.HOUR, 1);
            time2 = formatTime(calendar.getTime());
            time = time1 + " - " + time2;
            timeTextView.setText(time);
        }
    }

    private void selectDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        Date currentDate = c.getTime();
        final String[] days = new String[8];
        days[0] = formatDate(currentDate);

        c.add(Calendar.DATE, 1);
        days[1] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[2] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[3] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[4] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[5] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[6] = formatDate(c.getTime());
        c.add(Calendar.DATE, 1);
        days[7] = formatDate(c.getTime());

        // setup the alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(confirmationActivity.this);
        // add a radio button list
        builder.setSingleChoiceItems(days, dateSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateSelected = which;
                dialog.dismiss();
                date = days[which];
                dateTextView.setText(date);
                setTime();
            }
        });
        builder.show();
    }

    private void selectTime() {
        Calendar c = Calendar.getInstance();
        int n;
        if (c.get(Calendar.HOUR_OF_DAY) > 11 && c.get(Calendar.HOUR_OF_DAY) < 23 && date.equals(formatDate(c.getTime()))) {
            n = 22 - c.get(Calendar.HOUR_OF_DAY);
            Date currentDate = c.getTime();
            time1 = formatTime(currentDate);
            c.add(Calendar.HOUR, 1);
            time2 = formatTime(c.getTime());
            time = time1 + " - " + time2;
            final String[] hours = new String[n];
            for (int i = 0; i < n; i++) {
                time1 = formatTime(c.getTime());
                c.add(Calendar.HOUR, 1);
                time2 = formatTime(c.getTime());
                time = time1 + " - " + time2;
                hours[i] = time;
            }
            // setup the alert builder
            final AlertDialog.Builder builder = new AlertDialog.Builder(confirmationActivity.this);
            // add a radio button list
            builder.setSingleChoiceItems(hours, timeSelected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeSelected = which;
                    dialog.dismiss();
                    time = hours[which];
                    timeTextView.setText(time);
                }
            });
            builder.show();
        } else {
            n = 12;
            c.set(Calendar.HOUR_OF_DAY, 11);
            final String[] hours = new String[n];
            for (int i = 0; i < n; i++) {
                time1 = formatTime(c.getTime());
                c.add(Calendar.HOUR, 1);
                time2 = formatTime(c.getTime());
                time = time1 + " - " + time2;
                hours[i] = time;
            }
            // setup the alert builder
            final AlertDialog.Builder builder = new AlertDialog.Builder(confirmationActivity.this);
            // add a radio button list
            builder.setSingleChoiceItems(hours, timeSelected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeSelected = which;
                    dialog.dismiss();
                    time = hours[which];
                    timeTextView.setText(time);
                }
            });
            builder.show();
        }
    }

    //Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
    private String formatDate(Date dateObject) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        return dateFormat.format(dateObject);
    }

    //Return the formatted date string (i.e. "4:30 PM") from a Date object.
    private String formatTime(Date dateObject) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("h a");
        return timeFormat.format(dateObject);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        ArrayList<Address> addresses = addressActivity.extraxtAddress();
        final ListView addressListView = findViewById(R.id.list);
        if (addressListView.getCount() != 0) {
            addressListView.setVisibility(View.VISIBLE);

            AddressAdapter adapter = new AddressAdapter(this, addresses);
            addressListView.setAdapter(adapter);
            Utility.setListViewHeightBasedOnChildren(addressListView);
        }

    }

    private int calculateAmount(int wc, int ewc) {
        int amount;
        if (wc == 1) {
            amount = (130 * wc) - (100 * ewc);
        } else amount = (125 * wc) - (100 * ewc);
        return amount;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm != null ? cm.getActiveNetworkInfo() : null) != null;
    }
}
