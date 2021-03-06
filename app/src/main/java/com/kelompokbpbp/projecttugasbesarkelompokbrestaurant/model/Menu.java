package com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kelompokbpbp.projecttugasbesarkelompokbrestaurant.R;

import static android.media.audiofx.AutomaticGainControl.isAvailable;

@Entity
public class Menu {
    @PrimaryKey
    private String id;
    private String nama;
    private String harga;
    private String jenis;
    private String fotoMenu;

    public Menu(String nama, String harga, String jenis, String fotoMenu) {
        this.nama = nama;
        this.harga = harga;
        this.jenis = jenis;
        this.fotoMenu = fotoMenu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getFotoMenu() {
        return fotoMenu;
    }

    @BindingAdapter({"android:loadImg"})
    public static void setFotoMenu(ImageView imageView, String fotoMenu) {
        Glide.with(imageView.getContext())
                .load(fotoMenu)
                .into(imageView);
    }

}
