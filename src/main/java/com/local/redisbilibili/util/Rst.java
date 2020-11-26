package com.local.redisbilibili.util;

import java.io.Serializable;


public class Rst implements Serializable {

    private int code;

    private String msg;

    private Object data;

    public Rst() {
    }

    public static Rst.RstBuilder create() {
        return new Rst.RstBuilder();
    }

    public static class RstBuilder {
        private Rst rst = new Rst();
        private int code;
        private String msg;
        private Object data;

        public RstBuilder() {}

        public Rst build() {
            this.rst.code = this.code;
            this.rst.msg = this.msg;
            this.rst.data = this.data;
            return this.rst;
        }

        public Rst.RstBuilder setCode(int code) {
            this.code = code;
            return this;
        }

        public Rst.RstBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Rst.RstBuilder setData(Object data) {
            this.data = data;
            return this;
        }

    }
}
