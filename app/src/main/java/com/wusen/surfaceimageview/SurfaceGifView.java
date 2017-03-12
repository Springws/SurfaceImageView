package com.wusen.surfaceimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by wusen on 2017/3/12.
 */
public class SurfaceGifView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String SurfaceTag = "surface";
    private MovieThread movieThread;
    private SurfaceHolder holder;
    //gif图片路径
    private String path;
    private Movie movie;
    //缩放倍率
    private float mScaleX = 1;
    private float mScaleY = 1;
    //空间测量高度和宽度
    private int mMeasureHeight;
    private int mMeasureWidth;
    //gif播放线程
    private MovieThread mThread;

    //构造函数
    public SurfaceGifView(Context context) {
        super(context);
        initParam();
    }

    public SurfaceGifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParam();
    }

    public SurfaceGifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParam();
    }

    /**
     * 初始化参数
     */
    private void initParam() {
        holder = getHolder();
        holder.addCallback(this);
        movieThread = new MovieThread(holder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(SurfaceTag, "surfaceCreated: ");

        //线程
        // if(movie != null) {
//            mThread = new MovieThread(holder);
            movieThread.start();
     //   }
    }

    /**
     * 计算视图宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(SurfaceTag, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(measureWidthMode == MeasureSpec.EXACTLY) {
            //mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        }

        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(measureHeightMode == MeasureSpec.EXACTLY) {
            //mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(SurfaceTag, "surfaceDestroyed: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Log.i(SurfaceTag, "surfaceChanged: ");
    }

    private class MovieThread extends Thread {
        private SurfaceHolder mHolder;

        public MovieThread(SurfaceHolder holder) {
            this.mHolder = holder;
        }

        @Override
        public void run() {
            Log.i(SurfaceTag, "run: ");
            Canvas canvas = null;
            mScaleX = (float) mMeasureWidth / movie.width();
            mScaleY = (float) mMeasureHeight / movie.height();
            while (true) {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    synchronized (holder) {
                        canvas.save();
                        canvas.scale(mScaleX, mScaleY);    //x为水平方向的放大倍数，y为竖直方向的放大倍数。
                        //绘制此gif的某一帧，并刷新本身
                        movie.draw(canvas, 0, 0);
                        //逐帧绘制图片(图片数量5)
                        // 1 2 3 4 5 6 7 8 9 10
                        // 1 2 3 4 0 1 2 3 4 0  循环
                        Log.i(SurfaceTag, "run: "+ "time" + System.currentTimeMillis() % movie.duration());
                        movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
                        canvas.restore();
                        //结束锁定画图，并提交改变,画画完成(解锁)
                    }

                }
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setmScaleX(float mScaleX) {
        this.mScaleX = mScaleX;
    }

    public void setmScaleY(float mScaleY) {
        this.mScaleY = mScaleY;
    }

    public void setmMeasureHeight(int mMeasureHeight) {
        this.mMeasureHeight = mMeasureHeight;
    }

    public void setmMeasureWidth(int mMeasureWidth) {
        this.mMeasureWidth = mMeasureWidth;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
