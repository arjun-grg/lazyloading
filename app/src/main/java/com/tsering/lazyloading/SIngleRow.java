package com.tsering.lazyloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by root on 3/25/15.
 */
public class SIngleRow {
    static class Single_detail{
        String name,id;
        Boolean fav;
        Single_detail(String name,Boolean fav,String id){
            this.name=name;
            this.fav=fav;
            this.id=id;
        }
    }

    static class Single_detailGridview {
        String name;

        Single_detailGridview(String name, Context context) {
            this.name = name;
        }
    }
//

}
