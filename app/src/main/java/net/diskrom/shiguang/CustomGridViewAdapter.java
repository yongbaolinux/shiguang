package net.diskrom.shiguang;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by jsb-hdp-0 on 2017/1/6.
 */

public class CustomGridViewAdapter extends BaseAdapter {
    public View getView(int position, View view, ViewGroup viewGroup){
        ImageView imageView = new ImageView(this); // 声明ImageView的对象
        return imageView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
