package in.h2oaqua.h2oaqua.data;

import android.provider.BaseColumns;

/**
 * Created by ranjeet on 20-01-2018.
 */

public final class UserContract {

    private UserContract() {
    }

    public static final class UserEntry implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_IMAGE_URL = "image";
        public static final String COLUMN_USER_PHONE = "phone";


    }

}
