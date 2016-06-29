package org.androidtown.networking.rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FeederAdapter extends BaseAdapter {

    private Context context;
    private List<FeederItem> items;
    private int resourceId;

    public FeederAdapter(Context context, int resourceId, List<FeederItem> items) {
        this.context = context;
        this.resourceId =  resourceId;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resourceId, null);
        }

        TextView tv = (TextView)view.findViewById(android.R.id.text1);
        tv.setText(items.get(position).getTitle());

        return view;
    }
}
