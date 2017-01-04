package net.diskrom.shiguang;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private AlertDialog commonDialog;       //维护一个全局的风格统一的dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 应用统一风格对话框
     */
    public void showDialog(String string){
        //维护一个公共对话框
        commonDialog = new AlertDialog.Builder(BaseActivity.this).setCancelable(false).create();
        Window commonDialogWindow = commonDialog.getWindow();
        commonDialog.show();
        commonDialogWindow.setContentView(R.layout.common_dialog);
        TextView commonDialogContent = (TextView)commonDialogWindow.findViewById(R.id.commonDialogContent);
        commonDialogContent.setText(string);
        TextView sure = (TextView) commonDialogWindow.findViewById(R.id._dialog_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
    }

    /**
     * activity退出
     */
    public void stop(){

    }

    /**
     * float 类型数据处理
     * @param string 待处理的字符串
     */
    public float floatVal(String string){
        if(string.replace(".","").length() == 0) {
            //全部为小数点直接返回0
            return 0;
        } else {
            if(string.indexOf('.') != string.lastIndexOf('.')){
                //有两个及两个以前小数点 舍弃第二个小数点及后面的字符
                String temp = string.substring(0,string.indexOf('.',string.indexOf('.')+1));
                if(temp.equals(".")){
                    return 0;
                } else {
                    return Float.parseFloat(temp);
                }
            } else {
                return Float.parseFloat(string);
            }
        }
    }
}
