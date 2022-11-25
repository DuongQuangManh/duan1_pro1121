package com.example.duan1_baove.model;

import kotlin.jvm.internal.PropertyReference0Impl;

public class ThongKe {
    private int soluong;
    private String cuahang_id;

    public ThongKe(int soluong, String cuahang_id) {
        this.soluong = soluong;
        this.cuahang_id = cuahang_id;
    }

    public ThongKe() {
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getCuahang_id() {
        return cuahang_id;
    }

    public void setCuahang_id(String cuahang_id) {
        this.cuahang_id = cuahang_id;
    }
}
