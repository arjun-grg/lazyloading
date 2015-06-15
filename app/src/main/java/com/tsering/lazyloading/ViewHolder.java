package com.tsering.lazyloading;

import android.view.View;
import android.widget.ImageView;
/**
 * Created by root on 3/25/15.
 */
public class ViewHolder {
    public static class DetailGridViewHolder {
        ImageView img;

        DetailGridViewHolder(View view) {
            this.img = (ImageView) view.findViewById(R.id.h_img);
        }
    }
}
