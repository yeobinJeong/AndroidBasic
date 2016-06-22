package com.androidexample.lab;

import java.io.Serializable;

/**
 * Created by OH.CHI.HOOOOON on 2016-04-06.
 */
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
