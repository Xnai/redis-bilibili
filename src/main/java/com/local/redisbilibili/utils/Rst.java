package com.local.redisbilibili.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Rst implements Serializable {

    private int code;

    private String msg;

    private Object data;

    private Rst() {}

    public static Rst create() {
        return new Rst();
    }

    public Rst setCode(int code) {
        this.code = code;
        return this;
    }

    public Rst setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Rst setData(Object data) {
        this.data = data;
        return this;
    }
}
