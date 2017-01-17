package net.diskrom.shiguang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;


public class BrowseImageActivity extends BaseActivity {
    private Bitmap srcImageBitmap;  //维护一个存储原图Bitmap的变量
    private static final int LOAD_SOURCE_IMAGE_FINISHED = 1;  //消息类型 原图加载完成
    private ImageView browseImage;
    private LinearLayout quse;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == LOAD_SOURCE_IMAGE_FINISHED){
                browseImage.setImageBitmap(srcImageBitmap);
                //图片加载完成后绑定图片处理动作
                quse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BrowseImageActivity.this,"hello",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    };
    private MyApp myApplication;                         //维护一个application实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myApplication = (MyApp) getApplication();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_image);
        init();
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        //加载已经预存的 bitmap
        //Bitmap bitmap = MainActivity.imagesBitmapMap.get(path);
        //Bitmap bitmap = myApplication.getImageFromMemoryCache(path);
        //browseImage.setImageBitmap(bitmap);
        //loadSrcImage(path);
        Glide.with(BrowseImageActivity.this).load(path).into(new GlideDrawableImageViewTarget(browseImage) {
            @Override
            public void onResourceReady(final GlideDrawable drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                //在这里添加一些图片加载完成的操作
                quse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //1、从drawable 转化为 bitmap

                        //2、直接从ImageView控件中获取bitmap
                        Bitmap bitmapOrigin = ((GlideBitmapDrawable)browseImage.getDrawable()).getBitmap();
                        int height = bitmapOrigin.getHeight();
                        int width = bitmapOrigin.getWidth();
                        //定义灰度图像
                        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        Canvas canvas = new Canvas(bmpGrayscale);
                        Paint paint = new Paint();
                        ColorMatrix cm = new ColorMatrix();
                        cm.setSaturation(0);
                        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
                        paint.setColorFilter(f);
                        canvas.drawBitmap(bitmapOrigin, 0, 0, paint);
                        browseImage.setImageBitmap(bmpGrayscale);
                    }
                });
            }
        });

        /*Glide.with(BrowseImageActivity.this).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                // setImageBitmap(bitmap) on CircleImageView
                browseImage.setImageBitmap(bitmap);
            }
        });*/
    }

    private void init(){
        browseImage = (ImageView) findViewById(R.id.browseImage);
        quse = (LinearLayout) findViewById(R.id.quse);
    }

    //开启新的线程加载原图
    private void loadSrcImage(final String srcImagePath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载原图
                srcImageBitmap = BitmapFactory.decodeFile(srcImagePath);
                Message msg = new Message();
                msg.what = LOAD_SOURCE_IMAGE_FINISHED;
                handler.sendMessage(msg);
            }
        }).start();
    }


}
