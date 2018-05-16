package in.h2oaqua.h2oaqua;

/**
 * Created by ranjeet on 09-01-2018.
 */

public class OrderHistory {
    private int mNoOfWaterCans;
    private int mNoOfEmptyCans;
    private int mAmount;
    private String mDate;

    public OrderHistory(int noOfWaterCans, int noOfEmptyCans, int amount, String date) {
        mNoOfWaterCans = noOfWaterCans;
        mNoOfEmptyCans = noOfEmptyCans;
        mAmount = amount;
        mDate = date;
    }

    public int getNoOfWaterCans() {
        return mNoOfWaterCans;
    }

    public int getNoOfEmptyCans() {
        return mNoOfEmptyCans;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getDate() {
        return mDate;
    }

}
