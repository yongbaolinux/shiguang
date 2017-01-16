package net.diskrom.shiguang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

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
        loadSrcImage(path);
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
