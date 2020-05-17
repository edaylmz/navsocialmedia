package com.example.nav;

public class Kullanici {
    private String kullaniciAdi;
    private String id;

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Kullanici(String kullaniciAdi, String id) {
        this.kullaniciAdi = kullaniciAdi;
        this.id = id;
    }

    public Kullanici() {
    }
}
