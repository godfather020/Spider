package com.example.spider.adapter;

import com.example.spider.model.Banner;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    List<Banner> img=new ArrayList<>();

    public MainSliderAdapter() {

    }
    public MainSliderAdapter(List<Banner> img) {
        this.img = img;
    }

    @Override
    public int getItemCount() {
        return img.size();
    }


    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {



        viewHolder.bindImageSlide(img.get(position).getPhoto().toString());

       /* switch (position) {
            case 0:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
                break;
            case 1:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
                break;
            case 2:
                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
                break;
        }*/
    }
}
