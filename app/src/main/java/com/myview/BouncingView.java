package com.myview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author LI .
 * Time 2018/9/17
 * Description is BouncingView
 */
public class BouncingView extends View {
    jiekouhuidiao jiekouhuidiao;
    private Path mPath = new Path();
    private Paint mPaint;
    private int mArcHeigth;
    private int mMaxarcHeigth;
   Status mStatus =  Status.NONE;

    private enum Status{
       NONE,
       STATUS_SMOOTH_UP,
        STATUS_DOWN;
   }

   @SuppressLint("ResourceType")
   private void init(){
       mPaint = new Paint();
       mPaint.setAntiAlias(true);//抗锯齿
       mPaint.setStyle(Paint.Style.FILL);//填充
       mPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
//       mMaxarcHeigth = getResources().getDimensionPixelSize(100);
       mMaxarcHeigth = 150;
   }
    public BouncingView(Context context) {
        this(context,null);
    }

    public BouncingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int currentPointY = 0 ;
        switch (mStatus){
            case NONE:
                currentPointY = 0;
                break;
            case STATUS_SMOOTH_UP:
                currentPointY = (int)(getHeight()*(1-(float)mArcHeigth/mMaxarcHeigth)+mMaxarcHeigth);
                break;
            case STATUS_DOWN:
                currentPointY = mMaxarcHeigth;
                break;
        }
        //绘制贝塞尔曲线
        //mPath里面储存一个封闭的曲线，并且随着动画的执行，让上边的曲线不断增加高度
        mPath.moveTo(0,currentPointY);
        mPath.quadTo( getWidth()/2,currentPointY-mArcHeigth,getWidth(),currentPointY);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    public void show(jiekouhuidiao jiekouhuidiao) {
        mStatus = Status.STATUS_SMOOTH_UP;
        //开启动画
        //当弧度的高度不断增长，和上移的增长率是一样的
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,mMaxarcHeigth);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //
                mArcHeigth = (int) animation.getAnimatedValue();
                invalidate();
                if(mArcHeigth == mMaxarcHeigth){
                    //弧度回弹一下
                    bounce();
                }
            }
        });
        valueAnimator.start();
        jiekouhuidiao.istrue(true);
        //让曲线不断增长
    }
    private void bounce() {
        mStatus = mStatus.STATUS_DOWN;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxarcHeigth,0);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeigth = (int)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

//    public void removew() {
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(getHeight(),0-getHeight());
//        valueAnimator.setDuration(1000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mArcHeigth = (int)animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        valueAnimator.start();
//    }

}
