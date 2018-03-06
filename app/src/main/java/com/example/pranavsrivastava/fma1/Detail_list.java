package com.example.pranavsrivastava.fma1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Pranav Srivastava on 05-Mar-18.
 */

public class Detail_list extends ArrayAdapter<Data> {
    public Activity context;
    public List<Data> arlist;

    public Detail_list(Activity context, List<Data> arlist)
    {
        super(context,R.layout.list_layout, arlist);
        this.context=context;
        this.arlist=arlist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li=context.getLayoutInflater();
        View listViewItem=li.inflate(R.layout.list_layout,null,true);
        TextView twf= (TextView) listViewItem.findViewById(R.id.food);
        TextView twa= (TextView) listViewItem.findViewById(R.id.address);
        Data data=arlist.get(position);
        twf.setText(data.getFood());
        twa.setText(data.getAddress());
        return listViewItem;
    }
}
