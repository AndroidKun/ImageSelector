package com.example.g40_70m.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.androidkun.imageselectorlibrary.activity.SelectImageActivity;
import com.androidkun.imageselectorlibrary.utils.SelectMothedPopupWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SelectMothedPopupWindow.OnMothedSelected {

    private SelectMothedPopupWindow selectMothedPopupWindow;
    private int selectNum = 3;//可选择图片的最大数量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPopuWindow();
    }

    private void initPopuWindow() {
         selectMothedPopupWindow = new SelectMothedPopupWindow(this);
    }

    public void selectImage(View view){
        selectMothedPopupWindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .3f;
        getWindow().setAttributes(lp);

        selectMothedPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    public void mothedSelected(int mothed) {
        if (mothed == 1) {//相机
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1002);
        } else if (mothed == 2) {//相册
            startActivityForResult(new Intent(this, SelectImageActivity.class).putExtra("SELECT_NUM", selectNum), 1001);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {//相册回结果
            if (resultCode == 1001) {
                List<String> images = (List<String>) data.getSerializableExtra("SELECTED_IMAGE");
                String image = "";
                for (String path : images) {
                    image += path + "\n";
                }
                Log.w("TAG",image);
            }
        }else if(requestCode == 1002) {//相机返回结果
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//                ((ImageView) findViewById(R.id.imageview)).setImageBitmap(bitmap);
                String path = saveBitmapToLoacte(bitmap);
                Log.w("TAG",path);
            }
        }
    }

    /**
     * 保存bitmap到本地
     * @param bitmap
     */
    private String saveBitmapToLoacte(Bitmap bitmap) {
        String path = null;
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("TestFile",
                    "SD card is not avaiable/writeable right now.");
            return path;
        }
        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        FileOutputStream b = null;
        File file = new File("/sdcard/Image/");
        if(!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = "/sdcard/Image/"+name;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
                path = fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }
}
