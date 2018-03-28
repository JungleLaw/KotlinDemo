package com.law.kotlindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Jungle on 2018/3/28.
 */

public class ImageHandleActivity extends AppCompatActivity {
    ImageView mImageView;
    Button mBtnHandle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_handle);
        mImageView = findViewById(R.id.img);
        mBtnHandle = findViewById(R.id.btn_handle);
        mBtnHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap sourBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                long begin = System.currentTimeMillis();
                Bitmap destBmp = drawCenterLable(ImageHandleActivity.this, sourBitmap, "某某公司专用");
                long end = System.currentTimeMillis();
                Log.d("brycegao", "打水印用时：" + (end - begin) + "毫秒");
                mImageView.setImageBitmap(destBmp);
                sourBitmap.recycle();
                sourBitmap = null;
            }
        });
    }


    /**
     * @param context
     * @param bmp     添加全屏斜着45度的文字
     * @param text
     * @return
     */
    public static Bitmap drawCenterLable(Context context, Bitmap bmp, String text) {
        float scale = context.getResources().getDisplayMetrics().density;
        //创建一样大小的图片
        Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(newBmp);
        canvas.drawBitmap(bmp, 0, 0, null);  //绘制原始图片
        canvas.save();
//        canvas.rotate(45); //顺时针转45度
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.argb(90, 255, 255, 255)); //白色半透明
        paint.setColor(Color.argb(255, 0, 0, 0)); //白色半透明
        paint.setTextSize(30);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect rectText = new Rect();  //得到text占用宽高， 单位：像素
        paint.getTextBounds(text, 0, text.length(), rectText);
//        double beginX = (bmp.getHeight() / 2 - rectText.width() / 2) * 1.4;  //45度角度值是1.414
//        double beginY = (bmp.getWidth() / 2 - rectText.width() / 2) * 1.4;
//        canvas.drawText(text, (int) beginX, (int) beginY, paint);
//        float mTextWidth = paint.measureText(text);
//        canvas.drawText(text, (int) bmp.getWidth() - mTextWidth, (int) bmp.getHeight() - rectText.height(), paint);
        canvas.drawText(text, (int) bmp.getWidth() - rectText.width() - 10, (int) bmp.getHeight() - rectText.bottom - 10, paint);
        canvas.restore();
        return newBmp;
    }
}
