package in.h2oaqua.h2oaqua;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ranjeet on 04-01-2018.
 */

public class AddressAdapter extends ArrayAdapter<Address> {

    AddressAdapter(Context context, List<Address> addresses) {

        super(context, 0, addresses);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.address_list_item, parent, false);
        }

        //find address at given position.
        Address currentAddress = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.addressImage);
        imageView.setImageResource(currentAddress != null ? currentAddress.getImageResourseId() : 0);
        imageView.setBackgroundResource(currentAddress != null ? currentAddress.getImageBackgroundResourceId() : 0);
        // imageView.setVisibility(View.VISIBLE);

        TextView addressTextView = listItemView.findViewById(R.id.addressString);
        addressTextView.setText(currentAddress != null ? currentAddress.getAddress() : null);

        return listItemView;
    }
}
