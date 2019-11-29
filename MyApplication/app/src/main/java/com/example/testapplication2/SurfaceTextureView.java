package com.example.testapplication2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

public class SurfaceTextureView extends TextureView{

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public SurfaceTextureView(Context context) {
        this(context,null);
    }

    public SurfaceTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SurfaceTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SurfaceTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setAspectRatio(int width,int height) throws IllegalAccessException {
        if (width < 0 || height < 0){
            throw new IllegalAccessException("尺寸值必须大于0");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mRatioWidth == 0 || mRatioHeight == 0){
            setMeasuredDimension(width,height);
        }else {
            if (width < height * mRatioWidth / mRatioHeight){
                setMeasuredDimension(width,width * mRatioHeight / mRatioWidth);
            }else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight ,height);
            }
        }
    }
}
