package com.tsering.lazyloading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by root on 5/4/15.
 */
public class Detail_gridView extends BaseAdapter {
    Context context;
    ArrayList<SIngleRow.Single_detailGridview> list;
    Boolean lazyLoading;
    public Detail_gridView(Context context, ArrayList<Detail_info> data) {
        this.context = context;
        lazyLoading = false;
        list = new ArrayList<SIngleRow.Single_detailGridview>();
        for (int i = 0; i < data.size(); i++) {
            list.add(new SIngleRow.Single_detailGridview(data.get(i).name, context));
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder.DetailGridViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.adaptor_fragment_gridview, viewGroup, false);
            holder = new ViewHolder.DetailGridViewHolder(row);

            row.setTag(holder);
        } else {
            holder = (ViewHolder.DetailGridViewHolder) row.getTag();
        }
        SIngleRow.Single_detailGridview temp = list.get(i);
//        holder.img.setImageBitmap(temp.img);
//        new BitmapWorkerTask(holder.img).execute(temp.name);

            if (cancelPotentialWork(temp.name, holder.img)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(holder.img);
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(task);
                holder.img.setImageDrawable(asyncDrawable);
                task.execute(temp.name);

        }

        return row;
    }
    @Override
    public boolean isEnabled(int position) {
        if (list.get(position).name.equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
//        Detail_notify(DBManager.getInstance(context).ShowingDetailIMg(SmsDetail_Activity.category));
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageView;
        String bit_name = "";


        BitmapWorkerTask(ImageView view) {
            imageView = new WeakReference<ImageView>(view);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            bit_name = (String) strings[0];
            Bitmap img = null;

            if (!isCancelled()) {
                if (bit_name != null) {
                    File file = new File(context.getFilesDir().getAbsolutePath() + "/th_" + bit_name);
                    if (file.exists()) {
                        try {
                            FileInputStream in = new FileInputStream(file);
                            img = BitmapFactory.decodeStream(in);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (OutOfMemoryError error) {
                            error.printStackTrace();
                        }
                    }else {
                        Bitmap bm, newBit;
                        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int height = size.y;
                        int width = size.x;
                        int bit_width = (int) context.getResources().getDimension(R.dimen.detail_bit_width);
                        int bit_height = (int) context.getResources().getDimension(R.dimen.detail_bit_height);

                                        String url = "http://uat.ekbana.info/smsapp/user_uploads/sms_images/" + bit_name.replace("\\", "");
                                        RequestCreator bm1 = Picasso.with(context).load(url);
                                        try {
                                            bm = bm1.get();
                                            float ori_width = bm.getWidth();
                                            float ori_height = bm.getHeight();
                                            float ratio = ori_width / ori_height;
                                            int img_width;
                                            int img_height;
                                            if (ori_width > ori_height) {
                                                img_width = width;
                                                img_height = (int) (img_width / ratio);
                                            } else {
                                                img_height = height;
                                                img_width = (int) (img_height * ratio);
                                            }

                                            OutputStream outputStream;

                                            file = new File(context.getFilesDir().getAbsolutePath() + "/" + bit_name);
                                            File th_file = new File(context.getFilesDir().getAbsolutePath() + "/th_" + bit_name);
                                            try {
                                                outputStream = new FileOutputStream(file);
                                                newBit = Bitmap.createScaledBitmap(bm, img_width, img_height, false);
                                                newBit.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                                                outputStream = new FileOutputStream(th_file);
                                                newBit = Bitmap.createScaledBitmap(bm, bit_width, bit_height, false);
                                                newBit.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                                img=newBit;
                                                outputStream.flush();
                                                outputStream.close();
                                            } catch (Exception e) {
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                }
                }
            }
            return img;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageView != null && bitmap != null) {
                final ImageView image = imageView.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(image);
                if (this == bitmapWorkerTask && image != null) {
                    image.setImageBitmap(bitmap);
                }
            }

        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(BitmapWorkerTask bitmapWorkerTask) {
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.bit_name;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData.equals("") || bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}

