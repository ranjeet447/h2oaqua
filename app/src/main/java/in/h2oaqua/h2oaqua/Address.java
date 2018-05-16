package in.h2oaqua.h2oaqua;

/**
 * Created by ranjeet on 04-01-2018.
 */

public class Address {
    private String mAddress;
    private int mImageResourceId;
    private int mImageBackgroundResourceId;

    Address(int imageResourceId, int imageBackgroundResourceId, String address) {
        mImageResourceId = imageResourceId;
        mAddress = address;
        mImageBackgroundResourceId = imageBackgroundResourceId;
    }

    int getImageResourseId() {
        return mImageResourceId;
    }

    public String getAddress() {
        return mAddress;
    }

    int getImageBackgroundResourceId() {
        return mImageBackgroundResourceId;
    }
}
