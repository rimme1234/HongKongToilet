package hk.edu.ouhk.ronnie.hongkongtoilet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ronnie on 18/2/2017.
 */

public class ToiletListAdapter extends ArrayAdapter<Toilet> {
    int resource;
    String response;
    Context context;

    public ToiletListAdapter(Context context, int resource,
                             List<Toilet> items) {
        super(context, resource, items);
        // TODO Auto-generated constructor stub
        this.resource=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout toiletView;

        Toilet toilet=getItem(position);

        if(convertView==null)
        {
            toiletView = new LinearLayout(getContext());
            String inflater=Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi=(LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, toiletView,true);

        }
        else{
            toiletView=(LinearLayout)convertView;
        }//TODO
        TextView alertText1 = (TextView)toiletView.findViewById(R.id.toiletTitle);
        alertText1.setText(toilet.name);
        TextView alertText2 = (TextView)toiletView.findViewById(R.id.toiletAddress);
        alertText2.setText(toilet.address);
        TextView alertText3 = (TextView)toiletView.findViewById(R.id.toiletDistance);
        alertText3.setText(toilet.distance);

        return toiletView;

    }
}
