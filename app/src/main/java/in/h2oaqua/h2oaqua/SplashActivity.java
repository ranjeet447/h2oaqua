package in.h2oaqua.h2oaqua;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import in.h2oaqua.h2oaqua.data.UserContract;
import in.h2oaqua.h2oaqua.data.UserDbHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserDbHelper mDbHelper = new UserDbHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                Cursor mCursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME, null);
                if (mCursor.getCount() == 0)
                    startActivity(new Intent(SplashActivity.this, signInActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, PlaceOrderActivity.class));
                finish();
                mCursor.close();
            }
        }, 1000);
    }
}
