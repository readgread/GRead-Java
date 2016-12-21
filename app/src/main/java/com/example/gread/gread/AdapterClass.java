package com.example.gread.gread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ISHAN_GUPTA on 11/22/2016.
 */
public class AdapterClass  extends ArrayAdapter<String> {
    Context context;
    private ArrayList<String> TextValue = new ArrayList<String>();

    public AdapterClass(Context context, ArrayList<String> TextValue) {
        super(context, R.layout.drawer_list_item, TextValue);
        this.context = context;
        this.TextValue= TextValue;

    }


    @Override
    public View getView(int position, View coverView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drawer_list_item,
                parent, false);

        TextView text1 = (TextView)rowView.findViewById(R.id.text1);
        text1.setText(TextValue.get(position));

        return rowView;

    }

}
