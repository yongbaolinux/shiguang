package net.diskrom.shiguang;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.os.Handler;
import android.media.ThumbnailUtils;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends BaseActivity {
    private long mClickTime = 0;                                //点击时间
    private static final int countPerRow = 3;                   //每一行排列的图片数量
    private static final int pixPadding = 10;                   //每张图片的间距

    private static final int GET_EXTERNAL_IMAGES_FINISHED = 1;  //消息类型 读取完所有SD卡上的图片
    private Button loadImage;
    private GridView gridView;
    private List<String> imagesPath = new ArrayList<String>();        //存放查询出的图片路径
    private List<Bitmap> imagesBitmap = new ArrayList<Bitmap>();      //存放查询出的图片资源
    CustomGridViewAdapter customGridViewAdapter;                        //

    //private HashMap<String,String> gridViewItemData = new HashMap<>();  //用一个hashmap存放每个gridItem 数据
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            if(msg.what == GET_EXTERNAL_IMAGES_FINISHED){
                //获得消息通知后更新  customGridViewAdapter
                /*for(int i = 0;i < imagesPath.size(); i++){

                }*/
                //gridView.setAdapter(customGridViewAdapter);
                customGridViewAdapter.notifyDataSetChanged();
            }
        }
    };

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        loadImage = (Button) findViewById(R.id.loadImage);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //启动意图
                Intent intent = new Intent(MainActivity.this,BrowseImageActivity.class);
                startActivity(intent);
            }

        });
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType("image*//*");
                //intent.setPackage("com.miui.gallery");
                //intent.setPackage("com.android.gallery3d");
                //startActivity(intent);

                //设置adapter
                gridView.setAdapter(customGridViewAdapter);
            }
        });
        getImages();
        customGridViewAdapter = new CustomGridViewAdapter(MainActivity.this,imagesBitmap);
    }

    //获取图片地址的
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
                String order = MediaStore.Images.Media.DATE_MODIFIED + " desc limit 100 offset 0 ";       //查询结果排序 按修改日期 逆序
                Cursor cursor = contentResolver.query(imageUri,columns,where,null,order);
                if(cursor == null){
                    LogUtils.v("cursor初始化失败");
                    return;
                }
                assert(cursor!=null);
                //遍历cursor
                while(cursor.moveToNext()){
                    //获取图片的路径
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    imagesPath.add(path);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path,options);

                    //横向排列三张图片(
                    screenProperty sp = getScreen();
                    int toWidth = (sp.width - (countPerRow + 1) * pixPadding) / countPerRow;      //希望压缩的宽度值
                    int toHeiht = toWidth;
                    //int toHeight = options.outHeight * toWidth / options.outWidth;
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;    //ARGB_4444的图像质量太差
                    options.inSampleSize = options.outWidth / toWidth;      //压缩比例
                    Bitmap bitmap = BitmapFactory.decodeFile(path,options);
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, toWidth, toHeiht);
                    imagesBitmap.add(bitmap);
                    //定义待发送的消息体
                    Message msg = new Message();
                    msg.what = GET_EXTERNAL_IMAGES_FINISHED;
                    handler.sendMessage(msg);       //解析完一张图片就通知主线程更新 customGridViewAdapter
                }

            }
        }).start();
    }

    /**
     * 连续按两次回退键退出应用
     * @param keyCode   按键代码
     * @param keyEvent
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        if(keyCode == KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if((currentTime - mClickTime) > 2000){
                mClickTime = currentTime;
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                System.exit(0);
            }
        }
        return true;
    }
}

