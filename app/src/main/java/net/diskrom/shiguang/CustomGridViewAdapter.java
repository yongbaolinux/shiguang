package net.diskrom.shiguang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

/**
 * Created by yongbaolinux on 2017/1/6.
 *  自定义gridview adapter
 */

public class CustomGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List list;
    private LayoutInflater layoutInflater;

    public CustomGridViewAdapter(Context context, List list){
        mContext = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);      //布局渲染器
    }

    public View getView(int position, View convertView, ViewGroup viewGroup){
        View view = null;
        if(layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.grid_view_item,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //把图片地址转换为 bitmap 并设置 imageview
            LogUtils.v(list.get(position).toString());
            Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).toString());
            imageView.setImageBitmap(bitmap);
        }
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
}
