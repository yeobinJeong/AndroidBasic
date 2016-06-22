package com.androidexample.lab;

import android.widget.ImageView;

public class CardItem {

    private ImageView imageView;
    private int imageIndex;//image 배열에서의 위치 값
    private boolean opened;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

}
