package com.tsering.lazyloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Detail_info> list;
    DBManager manager;
    MyTask task;
    String url="http://uat.ekbana.info/smsapp/api_category_sms.php?id=20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView= (GridView) findViewById(R.id.gridview);
        manager=DBManager.getInstance(this);
        task=new MyTask();
        if (NetCheck()){
            new AQuery(this).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    super.callback(url, object, status);
                    if (object != null) {
                        task.execute(object.optJSONArray("sms"));
                    } else {
                        task.onPostExecute(null);
                    }
                }
            });
        }else {
            list = manager.ShowingDetailIMg();
            Detail_gridView adaptor=new Detail_gridView(this,list);
            gridView.setAdapter(adaptor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class MyTask extends AsyncTask<JSONArray,Void,ArrayList> {
        @Override
        protected ArrayList<Detail_info> doInBackground(JSONArray... params) {
            JSONArray json = params[0];
            ArrayList<Detail_info> detailInfos = new ArrayList<Detail_info>();
            ArrayList<Detail_info> detailInfo = new ArrayList<Detail_info>();
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    JSONObject sms = json.optJSONObject(i);
                    Detail_info detail = new Detail_info();
                    detail.id = sms.optString("id");
                        detail.cat_name = "arjun";
                        detail.isFav = 0;
                        detail.date = sms.optInt("date");
                        String url = "http://uat.ekbana.info/smsapp/" + sms.optString("image").replace("\\", "");
                        String[] s = url.split("/");
                        String name = s[s.length - 1];
                        detail.name = name;
                        detail.content = "null";
                    if (!manager.CheckInDb(detail.id)){
                        manager.InsertDetail(detail);
                    }
                        detailInfos.add(detail);
                }
            }
            return detailInfos;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            Detail_gridView adaptor=new Detail_gridView(MainActivity.this,arrayList);
            gridView.setAdapter(adaptor);
        }
    }

    public  Boolean NetCheck() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
