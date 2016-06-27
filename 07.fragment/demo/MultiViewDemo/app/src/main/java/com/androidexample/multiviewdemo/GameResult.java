package com.androidexample.multiviewdemo;

import java.io.Serializable;

public class GameResult implements Serializable {

    private int success;
    private int failure;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }
}
