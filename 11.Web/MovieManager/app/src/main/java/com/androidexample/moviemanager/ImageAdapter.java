package com.androidexample.moviemanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    List<Image> images;
    Context context;
    int resourceId;
    LayoutInflater inflater;

    ImageLoader loader;
    int viewSize;

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    public ImageAdapter(List<Image> images, int resourceId, Context context) {
        this.images = images;
        this.context = context;
        this.resourceId = resourceId;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            //view = inflater.inflate(resourceId, null);
            view = new ImageView(context);
            view.setLayoutParams(new GridView.LayoutParams(viewSize, viewSize));
            ((ImageView)view).setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        Image image = images.get(position);
        String thumbnail = image.getThumbnail();
        view.setTag(thumbnail);
        loader.displayImage(image.getThumbnail(), (Activity)context,(ImageView)view);

        return view;
    }
}
