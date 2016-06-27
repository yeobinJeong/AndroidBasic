package com.androidexample.notitle;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<String> data = new ArrayList<>();

        for( int i=0; i<50; i++){
            data.add("my data" + i);
        }
        ListView lv = (ListView)findViewById(R.id.list);
        /*MyAdapter ad = new MyAdapter(data);*/
        MyAdapter ad = new MyAdapter(this, R.layout.list_item, data);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);
        adapter.add("www");
        lv.setAdapter(ad);


    }

    class MyAdapter extends BaseAdapter{

        private List<String> data;
        private int layoutId;
        private Context context;//Activity
        private LayoutInflater inflater;


        public MyAdapter(){}
        public MyAdapter(Context context, int layoutId, List<String> data){
            this.context = context;
            this.layoutId = layoutId;
            this.data = data;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {

            View view = convertview;

            if(view == null){
                view = inflater.inflate(layoutId,null);
            }

            TextView tx1 = (TextView)view.findViewById(R.id.textView);
            TextView tx2 = (TextView)view.findViewById(R.id.textView2);

            tx1.setText("title " + data.get(position));
            tx2.setText("sub title " + data.get(position));

            ImageView iv = (ImageView)view.findViewById(R.id.imageView);
            iv.setImageResource(R.mipmap.ic_launcher);


            return view;

        }
    }
}
