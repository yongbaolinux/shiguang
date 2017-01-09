package net.diskrom.shiguang;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.os.Handler;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int GET_EXTERNAL_IMAGES_FINISHED = 1;  //消息类型 读取完所有SD卡上的图片

    //handler
    private Handler handler = new Handler() {

        public void handleMessage(Message msg){
            if(msg.what == GET_EXTERNAL_IMAGES_FINISHED){
                //拿到消息数据后开始组装供 gridview 使用的数据对象
                for(int i = 0;i < imagesPath.size(); i++){

                }
                LogUtils.v(imagesPath);
            }
        }
    };
    private Button loadImage;
    private GridView gridView;
    private List<String> imagesPath = new ArrayList<String>();        //存放所有图片地址

    private HashMap<String,String> gridViewItemData = new HashMap<>();  //用一个hashmap存放每个gridItem 数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        loadImage = (Button) findViewById(R.id.loadImage);
        gridView = (GridView) findViewById(R.id.gridView);
        //构造
        //gridView.setAdapter();
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
                    imagesPath.add(path);
                }
                //LogUtils.v(imagesPath);
                Message msg = new Message();
                msg.what = GET_EXTERNAL_IMAGES_FINISHED;
                //msg.obj = imagesPath;
                handler.sendMessage(msg);       //把图片地址信息 以message的方式发送给主线程
            }
        }).start();
    }
}

