package net.diskrom.shiguang;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;

public class MainActivity extends BaseActivity {

    private Button loadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        loadImage = (Button) findViewById(R.id.loadImage);
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                //intent.setPackage("com.miui.gallery");
                //intent.setPackage("com.android.gallery3d");
                //startActivity(intent);
                getImages();
            }
        });
    }

    private void getImages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                //LogUtils.v(imageUri);
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                //根据图片类型查询
                String [] columns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};   //要查询的列
                String where = null;        //没有查询条件 即全部查询出来
                String order = MediaStore.Images.Media.DATE_MODIFIED + " desc";       //查询结果排序 按修改日期 逆序
                Cursor cursor = contentResolver.query(imageUri,columns,where,null,order);
                assert(cursor!=null);
                //遍历cursor
                while(cursor.moveToNext()){
                    //获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    LogUtils.v(path);
                }
            }
        }).start();
    }
}
