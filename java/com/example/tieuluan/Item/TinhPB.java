package com.example.tieuluan.Item;

public class TinhPB {
    String tenDiaDanh;
    String diaChiDiaDanh;
    int image;

    public TinhPB(String tenDiaDanh, String diaChiDiaDanh, int image) {
        this.tenDiaDanh = tenDiaDanh;
        this.image = image;
        this.diaChiDiaDanh = diaChiDiaDanh;
    }

    public String getDiaChiDiaDanh() {
        return diaChiDiaDanh;
    }

    public void setDiaChiDiaDanh(String diaChiDiaDanh) {
        this.diaChiDiaDanh = diaChiDiaDanh;
    }

    public String getTenDiaDanh() {
        return tenDiaDanh;
    }

    public void setTenDiaDanh(String tenDiaDanh) {
        this.tenDiaDanh = tenDiaDanh;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
