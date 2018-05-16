package in.h2oaqua.h2oaqua.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.h2oaqua.h2oaqua.data.UserContract.UserEntry;

/**
 * Created by user on 20-01-2018.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = UserDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "userData.db";


    // Database version. If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //This is called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_IMAGE_URL + " TEXT NOT NULL, "
                + UserEntry.COLUMN_USER_PHONE + " TEXT );";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
