package net.diskrom.shiguang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;

public class BrowseImageActivity extends AppCompatActivity {

    private ImageView browseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_image);
        init();
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        //加载已经预存的 bitmap
        //Bitmap bitmap = MainActivity.imagesBitmapMap.get(path);
        //加载原图
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        browseImage.setImageBitmap(bitmap);
    }

    private void init(){
        browseImage = (ImageView) findViewById(R.id.browseImage);
    }
}
